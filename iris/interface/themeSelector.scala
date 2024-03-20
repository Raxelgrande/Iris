package iris.themeSelector
import iris.theming._
import iris.distroFinder._
import iris.tui._

def pickGtkTheme(): String = chooseOption_string(gtkList())
def pickIconTheme(): String = chooseOption_string(iconList())
def pickKvantumTheme(): String = chooseOption_string(kvantumList())

// DO NOT USE
def pickKdeColor(): String = chooseOption_string(kdeListColorScheme())
def pickKdeCursor(): String = chooseOption_string(kdeListCursorTheme())
def pickKdeGlobal(): String = chooseOption_string(kdeListGlobalTheme())


def selectDesktop(): String = chooseOption_string(desktopList(), "Please, select your Desktop Environment.\n")