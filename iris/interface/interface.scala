package iris.interface
import iris.tui._
import iris.config._
import iris.theming._

def mainMenu() =
  val home = chooseOption_string(Seq("Create a Configuration", "Load a Configuration", "Modify Configurations"), "Welcome to Iris!\nPlease select an option:")
  if home == "Create a Configuration" then
    def create() = spawnAndRead("Please, write a name for your new configuration.")
    while listOfConfigs().contains(create()) do
      pressToContinue("The name "+create()+" Already exists")
      
      
  if home == "Load a Configuration" then 
    val conflist = chooseOption_array(listOfConfigs(), "Select one of your configurations to load:")
  else if home == "Modify Configurations" then 
    val confchange = chooseOption_array(listOfConfigs(), "Select one of your configurations to modify:")


