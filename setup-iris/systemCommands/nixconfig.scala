package setup.dependencyInstall

import setup.sysUpdate._, setup.distroFinder.{getHome, getPackageManager}

import scala.io.Source
import scala.sys.process._
import java.io.File
import java.io.FileOutputStream

def nix_build() = List("nixos-rebuild", "switch").!<

def nix_addPkg(pkg: String) = nix_addPkgs(Vector(pkg))

def nix_addPkgs(pkgs: Seq[String]) =
  def getPkgsString(str: String = "", i: Int = 0): String =
    if i >= pkgs.length then
      str
    else if i == pkgs.length - 1 then
      getPkgsString(str + s"${pkgs(i)} ", i+1)
    else
      getPkgsString(str + s"${pkgs(i)}", i+1)

  def mkConf(conf: Vector[String], confstr: String = "", i: Int = 0): String =
    if i >= conf.length then
      confstr
    else if conf(i).contains("environment.systemPackages") then
      mkConf(conf, confstr + addPkgs(conf(i)) + "\n", i+1)
    else
      mkConf(conf, confstr + conf(i) + "\n", i+1)

  def addPkgs(line: String, newline: String = "", c: Int = 0): String =
    if c > line.length then newline //prevents crashing
    else if c == line.length then
      newline + s"\n${getPkgsString()}"
    else if line(c) == '#' || line(c) == ']' then
      newline + s"\n${getPkgsString()}${line(c)}"
    else addPkgs(line, newline + line(c), c+1)

  val nixconf = nix_readConf()
  val start = nix_findPackages(nixconf)
  if start != -1 then
    val newconf = mkConf(nixconf)
    nix_writeConf(newconf)

def nix_setupQtPlatform() =
  def replaceEnv(conf: Seq[String], newconf: String = "", i: Int = 0): String =
    if i >= conf.length then newconf
    else if conf(i).contains("environment.variables.QT_QPA_PLATFORMTHEME") then
      replaceEnv(conf, newconf + "environment.variables.QT_QPA_PLATFORMTHEME = \"qt5ct\";\n", i+1)
    else
      replaceEnv(conf, newconf + s"${conf(i)}\n", i+1)

  def getClosureLine(conf: Seq[String], i: Int = 0): Int =
    if i <= 0 then 0
    else if conf(i).contains("}") then i
    else getClosureLine(conf, i-1)

  def addEnv(conf: Seq[String], closureLine: Int, newconf: String = "", i: Int = 0): String =
    if i >= conf.length then newconf
    else if i == closureLine then
      addEnv(conf, closureLine, newconf + s"environment.variables.QT_QPA_PLATFORMTHEME = \"qt5ct\";\n${conf(i)}\n", i+1)
    else
      addEnv(conf, closureLine, newconf + s"${conf(i)}\n", i+1)

  val conf = nix_readConf()
  val envline = nix_findQtEnv(conf)
  val newconf =
    if envline != -1 then replaceEnv(conf)
    else addEnv(conf, getClosureLine(conf))
  nix_writeConf(newconf)

private def nix_readConf(): Vector[String] =
  Source.fromFile("/etc/nixos/configuration.nix")
    .getLines()
    .toVector

private def nix_writeConf(newconf: String) =
  File("/etc/nixos/configuration.nix").renameTo(File("/etc/nixos/configuration.nix.bak"))
  FileOutputStream("/etc/nixos/configuration.nix").write(newconf.getBytes())

def nix_findPackages(conf: Seq[String]): Int = nix_findLine(conf, "environment.systemPackages")

def nix_findQtEnv(conf: Seq[String]): Int = nix_findLine(conf, "environment.variables.QT_QPA_PLATFORMTHEME")

def nix_findLine(conf: Seq[String], line: String, i: Int = 0): Int =
  if i >= conf.length then
    -1
  else if conf(i).contains(line) then
    i
  else
    nix_findLine(conf, line, i+1)

def nix_hasPkg(conf: Vector[String], pkg: String): Boolean =
  val i = nix_findPackages(conf)
  if i == -1 then false
  else
    val pkgs = nix_getPkgs(conf, i)
    if pkgs.contains(pkg) then true else false

def nix_getPkgs(conf: Vector[String], i: Int, pkgs: Vector[String] = Vector()): Vector[String] =
  def endOfList(line: String, c: Int = 0): Boolean =
    if c >= line.length || line(c) == '#' then false
    else if line(c) == ']' then true
    else endOfList(line, c+1)

  def getPkg(line: String, pkg: String = "", i: Int = 0): String =
    if i >= line.length || line(i) == '#' || line(i) == ']' then pkg
    else if line(i) == '[' then
      getPkg(line, "", i+1)
    else if line(i) != ' ' && line(i) != '=' then
      getPkg(line, pkg + line(i), i+1)
    else getPkg(line, pkg, i+1)

  if i >= conf.length then pkgs
  else if endOfList(conf(i)) then pkgs :+ getPkg(conf(i))
  else nix_getPkgs(conf, i+1, pkgs :+ getPkg(conf(i)))
