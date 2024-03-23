package iris.interface
import iris.tui._
import iris.config._
import iris.theming._
import iris.themeSelector._

def mainMenu(): Unit =
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
      mainMenu()
    case "Load a Configuration" =>
      val conflist = chooseOption_astring(listOfConfigs(), "Select one of your configuration files to load:")
   
   
    case "Modify Configurations" =>
      val confchange = chooseOption_astring(listOfConfigs(), "Select one of your configuration files to modify:")
      if confchange == "" then
        mainMenu()
      def confchangeColor = foreground("green")+confchange

      if getConfig_list(confchange).contains("desktop_environment=") then 
        val selectedDesktop = askDesktop()
        replaceLine(confchange, "desktop_environment=", "desktop_environment="+selectedDesktop)
      
      val themeList = chooseOption_string(List("Cursor Theme", "Desktop Environment", "Desktop Theme","Flatpak", "Flatpak GTK Theme", 
      "Flatpak Icon Theme", "Full QT Theming", "GTK Theme", "Icon Theme", "Kvantum Theme", "Libadwaita/GTK4 Theme"), "Select a value to modify:"+
      s"\nYour currently selected configuration is $confchangeColor")
      
      val themeLine = themeList match 
        case "Cursor Theme" => "cursortheme="
        case "Desktop Environment" => "desktop_environment="
        case "Desktop Theme" => "desktoptheme="
        case "Flatpak" => "flatpak="
        case "Flatpak GTK Theme" => "flatpakgtk="
        case "Flatpak Icon Theme" => "flatpakicon="
        case "Full QT Theming" => "qt5ct="
        case "GTK Theme" => "gtktheme="
        case "Icon Theme" => "icontheme="
        case "Kvantum Theme" => "kvantumtheme="
        case "Libadwaita/GTK4 Theme" => "libadwaita="
  


      val themeAction = themeList match
        case "Cursor Theme" => 
          val cursorlist = chooseOption_string(iconList(), "Select a cursor theme to save in your config:" +
            "\nWarning! We can't separate Cursors from Icons, make sure you pick the right option, check in your Desktop's GUI first!")
          if cursorlist == "" then
            pressToContinue(foreground("red")+"Warning!!!" +
              "\nBefore loading this configuration, please select one of the available themes, Iris will malfunction without a value."+foreground("default"))
            mainMenu()
          else 
            replaceLine(confchange, themeLine, themeLine+cursorlist)
            mainMenu()
        
        case "Desktop Environment" =>
          val desktoplist = chooseOption_string(List("Budgie", "Cinnamon", "GNOME", "Xfce"), "Select a Desktop Environment to save in your config:")
          if desktoplist == "" then
            pressToContinue(foreground("red")+"Warning!!!" +
              "\nBefore loading this configuration, please select one of the available themes, Iris will malfunction without a value."+foreground("default"))
            mainMenu()
          else
            replaceLine(confchange, themeLine, themeLine+desktoplist)
            mainMenu()
        
        case "Desktop Theme" =>
          val desktoptheme = chooseOption_string(gtkList(), "Select a Desktop Theme to save in your config:")
          if desktoptheme == "" then
            pressToContinue(foreground("red")+"Warning!!!" +
              "\nBefore loading this configuration, please select one of the available themes, Iris will malfunction without a value."+foreground("default"))
            mainMenu()
          else  
            replaceLine(confchange, themeLine, themeLine+desktoptheme)
            mainMenu()

        case "Flatpak" =>
          val flatpakbool = askPrompt("Would you like to enable Flatpak theming? " +
            "\nIf you say yes you will later have to fill the other two Flatpak options.")
          if flatpakbool == true then
            replaceLine(confchange, themeLine, themeLine+"true")
            mainMenu()
          else 
            replaceLine(confchange, themeLine, themeLine+"false")
            mainMenu()
        
        case "Flatpak GTK Theme" =>
          val flatpakgtk = chooseOption_string(gtkList(), "Select a GTK Theme to apply in Flatpaks:")
          if flatpakgtk == "" then
            pressToContinue(foreground("red")+"Warning!!!" +
              "\nBefore loading this configuration, please select one of the available themes, Iris will malfunction without a value."+foreground("default"))
            mainMenu()
          else 
            replaceLine(confchange, themeLine, themeLine+flatpakgtk)
            mainMenu()

        case "Flatpak Icon Theme" =>
          val flatpakicon = chooseOption_string(iconList(), "Select an Icon Theme to apply in Flatpaks." +
            "\nWarning! We can't separate Icons from Cursors, make sure you pick the right option, check in your desktop's GUI first!")
          if flatpakicon == "" then
            pressToContinue(foreground("red")+"Warning!!!" +
              "\nBefore loading this configuration, please select one of the available themes, Iris will malfunction without a value."+foreground("default"))
            mainMenu()
          else 
            replaceLine(confchange, themeLine, themeLine+flatpakicon)
            mainMenu()

        case "Full QT Theming" =>
          val qtbool = askPrompt("Would you like to enable Full QT Theming?" +
            "\nThis means that your selected Icon Theme & Kvantum Theme will get applied to every QT program, including Flatpaks.")
            if qtbool == true then 
              replaceLine(confchange, themeLine, themeLine+"true")
              mainMenu()
            else 
              replaceLine(confchange, themeLine, themeLine+"false")
              mainMenu()

        case "GTK Theme" =>
          val gtktheme = chooseOption_string(gtkList(), "Select a GTK Theme to save in your config:")
          if gtktheme == "" then
            pressToContinue(foreground("red")+"Warning!!!" +
              "\nBefore loading this configuration, please select one of the available themes, Iris will malfunction without a value."+foreground("default"))
            mainMenu()
          else
            replaceLine(confchange, themeLine, themeLine+gtktheme)
            mainMenu()

        case "Icon Theme" =>
          val icontheme = chooseOption_string(iconList(), "Select an Icon Theme to save in your config." +
            "\nWarning! We can't separate Icons from Cursors, make sure you pick the right option, check in your desktop's GUI first!")
          if icontheme == "" then
            pressToContinue(foreground("red")+"Warning!!!" +
              "\nBefore loading this configuration, please select one of the available themes, Iris will malfunction without a value."+foreground("default"))
            mainMenu()
          else
            replaceLine(confchange, themeLine, themeLine+icontheme)
            mainMenu()
        
        case "Kvantum Theme" =>
          val kvantumtheme = chooseOption_string(kvantumList(), "Select a Kvantum Theme to save in your config. " +
            "\nWarning! For this to be effective outside Kvantum, please say yes in Full QT Theming.")
          if kvantumtheme == "" then 
            pressToContinue(foreground("red")+"Warning!!!" +
              "\nBefore loading this configuration, please select one of the available themes, Iris will malfunction without a value."+foreground("default"))
            mainMenu()
          else
            replaceLine(confchange, themeLine, themeLine+kvantumtheme)
            mainMenu()
        
        case "Libadwaita/GTK4 Theme" =>
          val libadwaitatheme = chooseOption_string(gtkList(), "Select a GTK Theme to apply in Libadwaita/GTK4 programs." +
            "\nWe recommend that you use the same Theme you selected in GTK Theme for optimal results.")
          if libadwaitatheme == "" then
            pressToContinue(foreground("red")+"Warning!!!" +
              "\nBefore loading this configuration, please select one of the available themes, Iris will malfunction without a value."+foreground("default"))
            mainMenu()
          else 
            replaceLine(confchange, themeLine, themeLine+libadwaitatheme)
            mainMenu()
      

      
  
  
  

