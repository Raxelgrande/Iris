import iris.distroFinder.getPackageManager 
import iris.sysUpdate._
import iris.dependencyInstall._
import iris.setup._
import iris.interface._
import iris.theming._
import iris.themeSelector._
import iris.config._
import iris.tui.*
import iris.distroFinder.getHome



@main def main() =
  println(getHome())

  //if you read this you are a certified scalamancer