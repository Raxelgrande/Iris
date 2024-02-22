package iris.sysUpdate

import iris.distroFinder.getPackageManager
import scala.sys.process._
import iris.tui.pressToContinue
import java.io.FileOutputStream

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
      case _ => println("unknown system") //remember to add something here
    kvantumFlatpak() //now that flatpak is (probably) installed, run again

def pacUpdate() = List("pacman", "-Syu", "--noconfirm").!<
    
def aptUpdate() = 
  List("apt", "update", "-y").!<
  List("apt", "upgrade", "-y").!<

def dnfUpdate() = List("dnf", "upgrade", "-y").!<

def zypperUpdate() = List("zypper", "dup", "-y").!<

def nixUpdate() = List("nixos-rebuild", "switch", "--upgrade").!<

def unknownSystem() = //remember to change the text based on what is supposed for the person to do next
  pressToContinue(
    "Iris could not recognise your system or the package manager it uses"
    + "\nPlease update your system before installing the required packages"
  )

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

def nixFlatpak() = //i will work on this soon. - banana
  println("TODO")




