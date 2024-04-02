package setup.sysUpdate

import setup.distroFinder.getPackageManager
import setup.dependencyInstall._
import bananatui.*
import scala.sys.process._
import setup.distroFinder.aptWho


val package_manager = getPackageManager()

def sysUpdate() =
  package_manager match
    case "pacman" => pacUpdate()
    case "apt" => aptUpdate()
    case "dnf" => dnfUpdate()
    case "zypper" => zypperUpdate()
    case _ => unknownSystem()

def sysDependencies() =
  package_manager match
    case "pacman" => pacDependency()
    case "apt" => aptWho()
    case "dnf" => dnfDependency()
    case "zypper" => zypDependency()
    case _ => unknownSystem("Please install the dependencies required for Iris to work, check them at:" +
      "\nhttps://github.com/Raxelgrande/Iris")
  

def installKvantumFlatpak() =
  if kvantumFlatpak() == false then //trying to run flatpak first, in case its already installed
    package_manager match
      case "pacman" => pacmanFlatpak()
      case "apt" => aptFlatpak()
      case "dnf" => dnfFlatpak()
      case "zypper" => zypperFlatpak()
      case _ => unknownSystem("Flatpak")


def pacUpdate() = 
  clear()
  List("pacman", "-Syu", "--noconfirm").!<
    
def aptUpdate() = 
  clear()
  List("apt", "update", "-y").!<
  List("apt", "upgrade", "-y").!<

def dnfUpdate() = 
  clear()
  List("dnf", "upgrade", "-y").!<

def zypperUpdate() = 
  clear()
  List("zypper", "dup", "-y").!<


def unknownSystem(pkg_name: String = "") =
  val txt =
    if pkg_name != "" then
      "Iris could not recognise your system or the package manager it uses"
      + s"\nPlease update your system before installing $pkg_name"
    else
      "Iris could not recognise your system or the package manager it uses"
      + "\nPlease update your system before installing the required packages"
  pressToContinue(txt)

