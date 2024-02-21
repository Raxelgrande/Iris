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
   
//maybe its better to run without sudo, and detect first if iris is being run as root

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
  println("Currently not supported")
  writePapirusPPA()
  """wget -qO /etc/apt/trusted.gpg.d/papirus-ppa.asc 'https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x9461999446FAF0DF770BFC9AE58A9D36647CAE7F'""".!<
  aptUpdate()
  List("apt", "install", "qt5ct", "qt6ct", "qt5-style-kvantum", "qt6-style-kvantum", "-y").!<


def writePapirusPPA() =
  val repo = "deb http://ppa.launchpad.net/papirus/papirus/ubuntu jammy main".getBytes()
  //gives you an Array[Byte], needed for writing
  val repolist = FileOutputStream("/etc/apt/sources.list.d/papirus-ppa.list", true)
  repolist.write(repo)
  repolist.close()
  //true is important, it sets append to true so you dont overwrite the file

