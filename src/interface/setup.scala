package iris.setup
import iris.tui._
import iris.config._
import iris.distroFinder._
import iris.sysUpdate._
import iris.theming._
import iris.dependencyInstall._

def setup() =
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

  
  
    
    
