package setup.setup
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
    if askPrompt("Would you like to enable the setup to theme flatpaks?" +
      "\nYou can always rerun this setup later if you change your mind about flatpak support." ) == true then
      flathubInstall()
      installKvantumFlatpak()
      kvantumOverride()
      flatpakGtkOverride()
      flatpakIconOverride()
    clear()  
    writeQtPlatform()
    pressToContinue("Due to how qt6ct and qt5ct works, we recommend that you launch them before creating a theme in Iris." +
      "\nIf you manually launch them and save the settings, we can use the generated config from the program to bring you better results" +
      "\nThis is especially important if you want a consistent font in QT programs." +
      "\nDue to how fonts work in Linux we can't automatically use your font in our theme automation, otherwise we would.")
    clear()
    pressToContinue("One thing, you will notice another program alongside Iris' main executable." +
      "\nThe program Misc contains some flatpak overrides we couldn't implement here due to how the Sudo/Root account works" +
      "\nPlease launch it after this if you are planning on using Flatpaks.")
