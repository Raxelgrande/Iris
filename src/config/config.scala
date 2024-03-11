package iris.config
import java.io.File
import java.io.FileOutputStream
import scala.io.Source
import iris.distroFinder._
import iris.tui._


def listOfConfigs(): List[String] = 
  val irisConfLoc = getHome()+"/.config/Iris/"
  
  val irisConfList = File(irisConfLoc).list()
  if irisConfList != null then
    irisConfList.toList
  else List()

def linesOfAllConfigs(): String = //temporary code to show how its working
  var allLines = ""
  for filename <- listOfConfigs() do
    val lines = 
      Source.fromFile(getHome()+"/.config/Iris/"+filename)
        .getLines()
        .toVector //fuck it
        .map(x => x + '\n')
        .mkString //without parentheses works???? looks like its a different method
    allLines += lines //procedural solution, alternative could be a recursive function
  allLines

def selectConfiguration() = //maybe let the user select what config to read?
  var config = readLoop_getListString(listOfConfigs())

    

    
    





