import iris.distroFinder.getPackageManager 
import iris.sysUpdate._
import iris.dependencyInstall._
import iris.firstRun._
import iris.theming._
import iris.themeSelector._

import iris.tui.*

@main def main() =
    val theme = pickGtkTheme()
    if theme != "" then xfceSetGtk(theme)
    else pressToContinue("No theme was selected!")

    val wm = pickGtkTheme()
    if wm != "" then xfceSetXfwm(wm)
    else pressToContinue("No theme was selected!")

    val icons = pickIconTheme()
    if icons != "" then xfceSetIcon(icons)
    else pressToContinue("No theme was selected!")

    val cursor = pickIconTheme()
    if cursor != "" then xfceSetCursor(cursor)
    else pressToContinue("No theme was selected")
    //if you read this you are a certified scalamancer
    