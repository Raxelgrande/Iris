package iris.themeSelector
import iris.theming._
import iris.tui._

def pickGtkTheme(): String = chooseOption_string(gtkList())
def pickIconTheme(): String = chooseOption_string(iconList())
def pickKvantumTheme(): String = chooseOption_string(kvantumList())

def askDesktop() =
  val title = "What is your desktop?"
  val select = chooseOption_string(Seq("Budgie", "Cinnamon", "GNOME", "Xfce"), title)
  if select == "" then println("The user cancelled!")
  else select

