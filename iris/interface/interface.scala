package iris.interface
import iris.tui._
import iris.config._
import iris.theming._
import iris.themeSelector._

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
      val conflist = chooseOption_astring(listOfConfigs(), "Select one of your configuration files to load:")
    case "Modify Configurations" =>
      val confchange = chooseOption_astring(listOfConfigs(), "Select one of your configuration files to modify:")
      def confchangeColor = foreground("green")+confchange

      if getConfig_list(confchange).contains("desktop_environment=") then 
        val selectedDesktop = askDesktop()
        replaceLine(confchange, "desktop_environment=", "desktop_environment="+selectedDesktop)

      
      val themeList = chooseOption_string(List("Cursor Theme", "Desktop Environment", "Desktop Theme", "Flatpak GTK Theme", 
      "Full QT Theming", "GTK Theme", "Icon Theme", "Kvantum Theme", "Libadwaita/GTK4 Theme"), s"Your currently selected configuration is $confchangeColor"+
      "\nSelect a value to modify:")
      
      val themeLine = themeList match 
        case "Cursor Theme" => "cursortheme="
        case "Desktop Environment" => "desktop_environment="
        case "Desktop Theme" => "desktoptheme="
        case "Flatpak GTK Theme" => "flatpakgtk="
        case "Full QT Theming" => "qt5ct="
        case "GTK Theme" => "gtktheme="
        case "Icon Theme" => "icontheme="
        case "Kvantum Theme" => "kvantumtheme="
        case "Libadwaita/GTK4 Theme" => "libadwaita="
  


      val themeAction = themeList match
        case "Cursor Theme" => 
          
          replaceLine(confchange, themeLine, themeLine+"value")
      

      
  
  
  

