package iris.interface
import iris.tui._
import iris.config._
import iris.theming._

def mainMenu() =
  val home = chooseOption_string(Seq("Load a Configuration", "Modify Configurations"), "Welcome to Iris!\nPlease select an option:")
  if home == "Load a Configuration" then 
    val conflist = chooseOption_array(listOfConfigs(), "Select one of your configurations to load:")
  else if home == "Modify Configurations" then 
    val confchange = chooseOption_array(listOfConfigs(), "Select one of your configurations to modify:")
