package iris.themeSelector
import iris.theming._
import iris.tui._

def pickGtkTheme(): String = readLoop_getListString(gtkList())
def pickIconTheme(): String = readLoop_getListString(iconList())
def pickKvantumTheme(): String = readLoop_getListString(kvantumList())