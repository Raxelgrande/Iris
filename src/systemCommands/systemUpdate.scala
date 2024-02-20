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

def pacUpdate() = List("sudo", "pacman", "-Syu", "--noconfirm").run
    
def aptUpdate() = 
  List("sudo", "apt", "update", "-y").run
  List("sudo", "apt", "upgrade", "-y").run

def dnfUpdate() = List("sudo", "dnf", "upgrade", "-y").run

def zypperUpdate() = List("sudo", "zypper", "dup", "-y").run

def nixUpdate() = List("sudo", "nixos-rebuild", "switch", "--upgrade").run

def unknownSystem() = //remember to change the text based on what is supposed for the person to do next
  pressToContinue(
    "Iris could not recognise your system or the package manager it uses"
    + "\nPlease update your system before installing the required packages"
  )
