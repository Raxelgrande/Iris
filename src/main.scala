import iris.distroFinder.getPackageManager 
import iris.sysUpdate._
import iris.dependencyInstall._
import iris.firstRun._
import iris.theming._
import iris.themeSelector._
import iris.config._

import iris.tui.*

@main def main() =
    val lines = linesOfAllConfigs()
    println(lines)
    //if you read this you are a certified scalamancer