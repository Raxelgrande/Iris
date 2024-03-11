package iris.config
import java.io.File
import java.io.FileOutputStream
import scala.io.Source
import iris.distroFinder._


def listOfConfigs(): List[String] = 
  val irisConfLoc = getHome()+"/.config/Iris/"
  
  val irisConfList = File(irisConfLoc).list()
  if irisConfList != null then
    irisConfList.toList
  else List()

def linesOfAllConfigs(): Unit =
  var allLines = ""
  for (filename <- listOfConfigs()) do
    allLines +=
      Source.fromFile(getHome()+"/.config/Iris/"+filename)
        .getLines()
        .map(x => x + "\n")
        .mkString()

    

    

    
    





