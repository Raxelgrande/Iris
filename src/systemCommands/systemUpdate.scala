package iris.sysUpdate
import iris.distroFinder
 import iris.distroFinder.getPackageManager
 import scala.sys.process._

 def sysUpdate() = 
    val getDistro = getPackageManager().mkString
    if getDistro.contains("pacman") then 
    pacUpdate()
    else if getDistro.contains("apt") then
   aptUpdate() 
    else if getDistro.contains("dnf") then
      dnfUpdate()
    else if getDistro.contains("zypper") then
      zypperUpdate()
    else if getDistro.contains("nix") then
      nixUpdate()
   

    
 def pacUpdate() = List("sudo", "pacman", "-Syu", "--noconfirm").run
    
def aptUpdate() = 
   List("sudo", "apt", "update", "-y").run
   List("sudo", "apt", "upgrade", "-y").run

 
def dnfUpdate() = List("sudo", "dnf", "upgrade", "-y").run

def zypperUpdate() = List("sudo", "zypper", "dup", "-y").run

def nixUpdate() = List("sudo", "nixos-rebuild", "switch", "--upgrade").run

    
