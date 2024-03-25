import iris.interface._
import iris.theming._
import iris.themeSelector._
import iris.config._
import bananatui.*



@main def main(theme: String*) =
  val converted = theme.toString()
  if converted == "List()" then
    mainMenu()
  else 
    loadConfig(theme.head)
  //if you read this you are a certified scalamancer