package iris.sysUpdate

import iris.distroFinder.getPackageManager
import iris.tui.pressToContinue

import scala.sys.process._
import java.io.File
import java.io.FileOutputStream
import scala.io.Source

def sysUpdate() = 
  getPackageManager() match
    case "pacman" => pacUpdate()
    case "apt" => aptUpdate()
    case "dnf" => dnfUpdate()
    case "zypper" => zypperUpdate()
    case "nix" => nixUpdate()
    case _ => unknownSystem()

def installKvantumFlatpak() =
  if !kvantumFlatpak() then //trying to run flatpak first, in case its already installed
    getPackageManager() match
      case "pacman" => pacmanFlatpak()
      case "apt" => aptFlatpak()
      case "dnf" => dnfFlatpak()
      case "zypper" => zypperFlatpak()
      case "nix" => nixFlatpak()
      case _ => unknownSystem("Flatpak")

def pacUpdate() = List("pacman", "-Syu", "--noconfirm").!<
    
def aptUpdate() = 
  List("apt", "update", "-y").!<
  List("apt", "upgrade", "-y").!<

def dnfUpdate() = List("dnf", "upgrade", "-y").!<

def zypperUpdate() = List("zypper", "dup", "-y").!<

def nixUpdate() = List("nixos-rebuild", "switch", "--upgrade").!<

def unknownSystem(pkg_name: String = "") =
  val txt =
    if pkg_name != "" then
      "Iris could not recognise your system or the package manager it uses"
      + s"\nPlease update your system before installing $pkg_name"
    else
      "Iris could not recognise your system or the package manager it uses"
      + "\nPlease update your system before installing the required packages"
  pressToContinue(txt)

def kvantumUbuntu() =
  List("add-apt-repository", "ppa:papirus/papirus", "-y").!<
  List("apt", "install", "qt5ct", "qt6ct", "qt5-style-kvantum", "qt6-style-kvantum", "-y").!<
  aptUpdate()


def kvantumDebian() =
  writePapirusPPA()
  List("wget", "-qO", "/etc/apt/trusted.gpg.d/papirus-ppa.asc", "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x9461999446FAF0DF770BFC9AE58A9D36647CAE7F").!<
  aptUpdate()
  List("apt", "install", "qt5ct", "qt6ct", "qt5-style-kvantum", "qt6-style-kvantum", "-y").!<


def writePapirusPPA() =
  val repo = "deb http://ppa.launchpad.net/papirus/papirus/ubuntu jammy main".getBytes()
  val repolist = FileOutputStream("/etc/apt/sources.list.d/papirus-ppa.list", true)
  repolist.write(repo)
  repolist.close()

//if flatpak isnt installed, the program will crash
//try catch prevents this and also lets us know if flatpak is installed in the system
//if flatpak is installed, we dont need to go through the native package manager to get it
def kvantumFlatpak(): Boolean =
  try
    List("flatpak", "remote-add", "--if-not-exists", "flathub", "https://dl.flathub.org/repo/flathub.flatpakrepo").!<
    List("flatpak", "install", "runtime/org.kde.KStyle.Kvantum/x86_64/6.6", "-y").!<
    true
    //note for later: even if flatpak is installed, the command can fail
    //if that happens, this command execution will return an int that is not equal to 0
  catch case e: Exception => false

def pacmanFlatpak() = 
  List ("pacman", "-S", "flatpak", "--noconfirm").!<
  kvantumFlatpak()

def aptFlatpak() =
  List("apt", "install", "flatpak", "-y").!<
  kvantumFlatpak()

def dnfFlatpak() =
  List("dnf", "install", "flatpak", "-y").!<
  kvantumFlatpak()

def zypperFlatpak() = 
  List("zypper", "install", "flatpak", "-y").!<
  kvantumFlatpak()

def nixFlatpak() = //needs testing, finishing and improvements
  def addPkgHorizontally(line: String, newl: String = "", i: Int = 0): String =
    if i >= line.length then newl
    else if line(i) == '[' then
      addPkgHorizontally(line, newl + line(i) + " flatpak", i+1)
    else
      addPkgHorizontally(line, newl + line(i), i+1)

  def addPkg(conf: Seq[String], newconf: String = "", i: Int = 0): String =
    if i >= conf.length then newconf
    else if conf(i).contains("environment.systemPackages = ") && conf(i)(0) != '#' then
      if !conf(i).contains("]") then
        addPkg(conf, newconf + conf(i) + "\n" + "  flatpak\n", i+1)
      else
        addPkg(conf, addPkgHorizontally(conf(i)) + "\n", i+1)
    else
      addPkg(conf, newconf + conf(i) + "\n", i+1)

  val confpath = "/etc/nixos/configuration.nix"
  val nixconf = Source.fromFile(confpath)
    .getLines()
    .toVector
  val newconf = addPkg(nixconf).getBytes()
  File(confpath).renameTo(File(s"$confpath.bak"))
  FileOutputStream(confpath).write(newconf)