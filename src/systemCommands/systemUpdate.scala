package iris.sysUpdate

import iris.distroFinder.getPackageManager
import scala.sys.process._
import iris.tui.pressToContinue

def sysUpdate() = 
  getPackageManager() match
    case "pacman" => pacUpdate()
    case "apt" => aptUpdate()
    case "dnf" => dnfUpdate()
    case "zypper" => zypperUpdate()
    case "nix" => nixUpdate()
    case _ => unknownSystem()
   
//maybe its better to run without sudo, and detect first if iris is being run as root

def pacUpdate() = List("sudo", "pacman", "-Syu", "--noconfirm").!<
    
def aptUpdate() = 
  List("sudo", "apt", "update", "-y").!<
  List("sudo", "apt", "upgrade", "-y").!<

def dnfUpdate() = List("sudo", "dnf", "upgrade", "-y").!<

def zypperUpdate() = List("sudo", "zypper", "dup", "-y").!<

def nixUpdate() = List("sudo", "nixos-rebuild", "switch", "--upgrade").!<

def unknownSystem() = //remember to change the text based on what is supposed for the person to do next
  pressToContinue(
    "Iris could not recognise your system or the package manager it uses"
    + "\nPlease update your system before installing the required packages"
  )

def kvantumUbuntu() =
  List("sudo", "add-apt-repository", "ppa:papirus/papirus", "-y").!<
  List("sudo", "apt", "install", "qt5ct", "qt6ct", "qt5-style-kvantum", "qt6-style-kvantum", "-y").!<
  aptUpdate()


def kvantumDebian() =
  println("Currently not supported")
  //"""sudo sh -c "echo 'deb http://ppa.launchpad.net/papirus/papirus/ubuntu jammy main' > /etc/apt/sources.list.d/papirus-ppa.list""".!<
  //"""sudo wget -qO /etc/apt/trusted.gpg.d/papirus-ppa.asc 'https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x9461999446FAF0DF770BFC9AE58A9D36647CAE7F'""".!<
  aptUpdate()


