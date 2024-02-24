package iris.sysUpdate

import iris.distroFinder.getPackageManager
import iris.tui.pressToContinue
import scala.sys.process._


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

