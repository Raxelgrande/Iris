import iris.distroFinder.getPackageManager 
import iris.sysUpdate._
import iris.dependencyInstall._
import iris.firstRun._
import iris.theming._
import iris.themeSelector._

import iris.tui.*

@main def main() =
    val global = pickKdeGlobal()
    if global != "" then pickKdeGlobal(global)
    else pressToContinue("No theme was selected!")
    //if you read this you are a certified scalamancer
    