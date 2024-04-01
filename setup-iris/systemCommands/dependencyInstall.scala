package setup.dependencyInstall
import setup.sysUpdate._
import setup.distroFinder.{getHome, getPackageManager}
import scala.io.Source
import scala.sys.process._
import java.io.File
import java.io.FileOutputStream
import bananatui.*


def pacDependency() = List("pacman", "-Sy", "qt5ct", "qt6ct", "kvantum", "kvantum-qt5", "--noconfirm").!< 

def dnfDependency() = List("dnf", "install", "qt5ct", "qt6ct", "kvantum", "kvantum-qt6", "-y").!<

def zypDependency() = List("zypper", "install", "qt5ct", "qt6ct", "kvantum-manager", "kvantum-qt5", "kvantum-qt6").!< 

def kvantumUbuntuOld() =
  List("apt", "install", "qt5ct", "qt5-style-kvantum", "-y").!<
  aptUpdate()

def kvantumUbuntu() =
  List("add-apt-repository", "ppa:papirus/papirus", "-y").!<
  List("apt", "install", "qt5ct", "qt6ct", "qt5-style-kvantum", "qt6-style-kvantum").!<
  aptUpdate()

def kvantumDebian() =
  writePapirusPPA()
  List("wget", "-qO", "/etc/apt/trusted.gpg.d/papirus-ppa.asc", "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x9461999446FAF0DF770BFC9AE58A9D36647CAE7F").!<
  aptUpdate()
  List("apt", "install", "qt5ct", "qt6ct", "qt5-style-kvantum", "qt6-style-kvantum", "-y").!<


def writePapirusPPA() =
  val repo = "\ndeb http://ppa.launchpad.net/papirus/papirus/ubuntu jammy main".getBytes()
  val repolist = FileOutputStream("/etc/apt/sources.list.d/papirus-ppa.list", true)
  repolist.write(repo)
  repolist.close()

def FOSetcEnvironment = FileOutputStream("/etc/environment", true)
def linesEtcEnvironment = Source.fromFile("/etc/environment").getLines()

def writeQtPlatform() =
  val qtPlatform = "QT_QPA_PLATFORMTHEME=qt5ct".getBytes()
  if linesEtcEnvironment.contains("QT_QPA_PLATFORMTHEME=qt5ct") then 
    pressToContinue("The Qt override we use, (QT_QPA_PLATFORMTHEME=qt5ct), is already configured")
  else
    FOSetcEnvironment.write(qtPlatform)
    FOSetcEnvironment.close()
  

def writeQtForceX11() = //for advanced settings, in cases were wayland QT has issues
  val qtX11 = "QT_QPA_PLATFORM=xcb".getBytes()
  FOSetcEnvironment.write(qtX11)
  FOSetcEnvironment.close()

def flathubInstall() = List("flatpak", "remote-add", "--if-not-exists", "flathub", "https://dl.flathub.org/repo/flathub.flatpakrepo").!<

//Don't use, getHome changes location on root
def flatpakGtkOverride() = List("flatpak", "override", "--filesystem="+getHome()+"/.themes").!<
def flatpakIconOverride() = List("flatpak", "override", "--filesystem="+getHome()+"/.icons").!<

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

def kvantumOverride() = List("flatpak", "override", "--filesystem=xdg-config/Kvantum:ro").!<

def pacmanFlatpak() = 
  List("pacman", "-S", "flatpak", "--noconfirm").!<
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

