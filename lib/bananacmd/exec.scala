package bananacmd

import scala.sys.process.*

def getHome() = System.getProperty("user.home")

def isProgramRoot(): Boolean = System.getProperty("user.home") == "/root"

def command(cmd: Seq[String], interactive: Boolean = true): Int =
  if interactive then
    cmd.!
  else
    cmd.!<

def command_read(cmd: Seq[String], interactive: Boolean = true): String =
  if interactive then
    cmd.!!
  else
    cmd.!!<

def command_readV(cmd: Seq[String], interactive: Boolean = true): Vector[String] =
  def parse(str: String, tmp: String = "", strlist: Vector[String] = Vector(), i: Int = 0): Vector[String] =
    if i >= str.length then
      if tmp.length > 0 then strlist :+ tmp else strlist
    else if str(i) == '\n' then
      parse(str, "", strlist :+ tmp, i+1)
    else
      parse(str, tmp + str(i), strlist, i+1)

  val stdout = command_read(cmd, interactive)
  parse(stdout)

def command_try(cmd: Seq[String], interactive: Boolean = true): Int =
  try
    command(cmd, interactive)
  catch
    case e: Exception => -1


def command_root(cmd: Seq[String], rootcmd: String = "pkexec", interactive: Boolean = true): Int =
  if isProgramRoot() then
    command(cmd, interactive)
  else
    command(Vector(rootcmd) ++ cmd, interactive)
