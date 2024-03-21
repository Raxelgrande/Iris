package iris.themeSelector
import iris.theming._
import iris.tui._

def pickGtkTheme(): String = chooseOption_string(gtkList())
def pickIconTheme(): String = chooseOption_string(iconList())
def pickKvantumTheme(): String = chooseOption_string(kvantumList())

def askDesktop(): String = //how to recurse here?
  val title = "What desktop do you want to use with your new configuration?"
  val select = chooseOption_string(List("Budgie", "Cinnamon", "GNOME", "Xfce"), title)
  if select == "Exit" then askDesktop()
  else return select

