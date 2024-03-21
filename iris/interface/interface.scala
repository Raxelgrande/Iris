package iris.interface
import iris.tui._
import iris.config._
import iris.theming._

def mainMenu() =
  def create(existingConfs: Array[String]): String =
    val confname = spawnAndRead("Please, write a name for your new configuration.")
    if existingConfs.contains(confname) then
      pressToContinue(s"The name $confname already exists")
      create(existingConfs)
    else
      confname



  val home = chooseOption_string(Seq("Create a Configuration", "Load a Configuration", "Modify Configurations"), "Welcome to Iris!\nPlease select an option:")

  home match
    case "Create a Configuration" =>
      createConfig(create(listOfConfigs()))
    case "Load a Configuration" =>
      val conflist = chooseOption_array(listOfConfigs(), "Select one of your configurations to load:")
    case "Modify Configurations" =>
      val confchange = chooseOption_array(listOfConfigs(), "Select one of your configurations to modify:")
