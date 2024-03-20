package iris.interface
import iris.tui._
import iris.config._
import iris.theming._
import iris.setup._ 

def mainMenu() =
  val main = chooseOption_string(Seq("Load Configuration", "Modify Configurations", "Re-run setup"), "Welcome to Iris!\nPlease select an option:")
