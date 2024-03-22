package iris.themeSelector
import iris.theming._
import iris.tui._
import iris.config.readConfig

def pickGtkTheme(): String = chooseOption_string(gtkList())
def pickIconTheme(): String = chooseOption_string(iconList())
def pickKvantumTheme(): String = chooseOption_string(kvantumList())

def askDesktop(): String = 
  val title = "What desktop do you want to use with your new configuration?"
  val select = chooseOption_string(List("Budgie", "Cinnamon", "GNOME", "Xfce"), title)
  if select == "Exit" then askDesktop()
  else select

def setDesktopTheme(filename: String, theme: String) =
  val checkdesktop = readConfig(filename, "desktop_environment=")
  val themeset = checkdesktop match
    case "desktop_environment=GNOME" =>
      gnomeSetShell(theme)
    case "desktop_environment=Budgie" =>
      gnBgSetGtk(theme)
    case "desktop_environment=Cinnamon" =>
      cinnamonSetShell(theme)
    case "desktop_environment=Xfce" =>
      xfceSetGtk(theme)
