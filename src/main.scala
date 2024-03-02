import iris.distroFinder.getPackageManager 
import iris.sysUpdate._
import iris.dependencyInstall._
import iris.firstRun._
import iris.theming._
import iris.themeSelector._

import iris.tui.*

@main def main() =
    val color = pickKdeColor()
    if color != "" then kdeSetColorScheme(color)
    else pressToContinue("No theme was selected!")

    val cursor = pickKdeCursor()
    if cursor != "" then kdeSetCursorTheme(cursor)
    else pressToContinue("No theme was selected!")

    val global = pickKdeGlobal()
    if global != "" then kdeSetGlobalTheme(global)
    else pressToContinue("No theme was selected!")
    //if you read this you are a certified scalamancer
    