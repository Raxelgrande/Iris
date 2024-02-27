package iris.themeSelector
import iris.theming._
import iris.tui._

def pickGtkTheme() = 
    readLoop_list(gtkList())
