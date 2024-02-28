package iris.themeSelector
import iris.theming._
import iris.tui._

def pickGtkTheme() = 
    val gtkPick = readLoop_getListString(gtkList())
    if gtkPick != "" then 
        println(gtkPick)
