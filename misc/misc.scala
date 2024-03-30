package misc.misc 
import scala.sys.process._

@main def main() =
  def getHome() = System.getProperty("user.home")

  def flatpakGtkOverride() = List("sudo", "flatpak", "override", "--filesystem="+getHome()+"/.themes").!<
  def flatpakIconOverride() = List("sudo", "flatpak", "override", "--filesystem="+getHome()+"/.icons").!<

  flatpakIconOverride()
  flatpakIconOverride()
