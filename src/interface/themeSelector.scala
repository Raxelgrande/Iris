package iris.themeSelector
import iris.theming._
import iris.tui._

def pickGtkTheme(): String = readLoop_getListString(gtkList())
