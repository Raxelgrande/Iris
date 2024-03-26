import bananatui.*
import bananacmd.isProgramRoot
import setup.distroFinder._
import setup.sysUpdate._
import setup.dependencyInstall._

@main def main() =
  if isProgramRoot() == false then pressToContinue("To set up Iris, you need to launch this utility as root!")
  else
    if askPrompt("We need to update your system to install all of the required dependencies that Iris needs to work properly." +
    "\nYou can still continue setting Iris up without updating, but you risk doing a partial update that can break your system" +
    "\nPress y to continue normally." +
    "\nPress n to continue without updating.") == true then
      sysUpdate()
      sysDependencies()
    else
      sysDependencies()
    if askPrompt("Would you like to enable the setup to theme flatpaks?" ) == true then
      installKvantumFlatpak()
      kvantumOverride()
      flatpakGtkOverride()
      flatpakIconOverride()
      writeQtPlatform()
    else
      writeQtPlatform()