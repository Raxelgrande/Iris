package iris.firstRun
import iris.tui._
import iris.config._
import iris.distroFinder._
import iris.sysUpdate.sysUpdate
import iris.theming._

def firstRun() =
  getPackageManager()
  if askPrompt("We need to update your system to install all of the required dependencies that Iris needs to work properly." +
  "\nYou can still continue setting Iris up without updating, but you risk doing a partial update that can break your system" +
  "\nPress y to continue normally." +
  "\nPress n to continue without updating.") == true then
    sysUpdate()
    askDesktop()
  else askDesktop()

  
  
    
    