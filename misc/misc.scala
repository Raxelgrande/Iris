package misc.misc 
import scala.sys.process._
import bananatui.*


@main def main() =
  def getHome() = System.getProperty("user.home")

  def flatpakGtkOverride() = List("sudo", "flatpak", "override", "--filesystem="+getHome()+"/.themes").!<
  def flatpakDotConfigOverride() = List("sudo", "flatpak", "override", "--filesystem="+getHome()+"/.config").!<
  def flatpakIconOverride() = List("sudo", "flatpak", "override", "--filesystem="+getHome()+"/.icons").!<
  val finishFlatpakOverrides = askPrompt("Due to limitations of Iris and the JVM's libraries, we had to make this third program to finish Iris' setup" +
    "\nIf you want to allow Iris to theme flatpaks' Icon, GTK themes and .config directory for compatibility reasons, say yes.")
  if finishFlatpakOverrides == true then 
    flatpakIconOverride()
    flatpakGtkOverride()
    flatpakDotConfigOverride()
  else 
    System.exit(0)
