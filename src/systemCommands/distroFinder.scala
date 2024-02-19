package iris.distroFinder
import scala.sys.process._
import scala.io.Source

def getPackageManager(): String =
  def findDistro(distro: String, l: Seq[String], i: Int = 0): Int = // l = list. i = iterator
    if i >= l.length then
      -1 //convenient way to tell its an "unknown" distro
    else if l(i) == distro then
      i
    else findDistro(distro, l, i+1)

  val distros = Vector("Debian GNU/Linux", "Linux Mint", "Pop!_OS", "Ubuntu", "Arch Linux", "EndeavourOS", "NixOS", "Fedora Linux", "openSUSE Tumbleweed")
  val pacmans = Vector("apt", "apt", "apt", "apt", "pacman", "pacman", "nix", "dnf", "zypper")
  val getos = 
    Source.fromFile("/etc/os-release")
    .getLines().toVector
    .filter(x => x.contains("NAME="))(0) //get the line that has your distro info as a string
    .replaceAll("NAME=", "")

  val i = findDistro(getos, distros)
  //distros and pacmans have the same size, and the positions are equivalent and related (if distros(3) is ubuntu then pacmans(3) is apt
  //with 2 vectors and 1 recursive function, we can know what package manager to use without using a huge if else or match statement, all we need to do is to add a new distro to distros and its equivalent package manager to pacmans, both in the same position
  if i != -1 then pacmans(i) //returns the package manager of the user's distro finally
  else "" //error handling, this means "unknown" distro
  