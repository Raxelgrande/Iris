package iris.themeSelector
import iris.theming._
import iris.distroFinder._
import iris.tui._

def pickGtkTheme(): String = readLoop_getListString(gtkList())
def pickIconTheme(): String = readLoop_getListString(iconList())
def pickKvantumTheme(): String = readLoop_getListString(kvantumList())
def pickKdeColor(): String = readLoop_getListString(kdeListColorScheme())
def pickKdeCursor(): String = readLoop_getListString(kdeListCursorTheme())
def pickKdeGlobal(): String = readLoop_getListString(kdeListGlobalTheme())


def selectDesktop(): String = readLoop_getListString(desktopList(), "Please, select your Desktop Environment.\n")