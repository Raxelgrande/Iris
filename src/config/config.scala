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

def linesOfAllConfigs() = 
  for (filename <- listOfConfigs()) do 
    Source.fromFile(getHome()+"/.config/Iris/"+filename)
    .getLines()
    .toList
  

    

    

    
    





