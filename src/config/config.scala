package iris.config
import java.io.File
import java.io.FileOutputStream
import scala.io.Source
import iris.distroFinder._


def listConfigs(): List[String] = 
  val irisConfLoc = getHome()+"/.config/Iris/"
  
  val irisConfList = File(irisConfLoc).list()
    if irisConfList != null then
    irisConfList.toList
    else List()

def searchFirstRun() = //TODO iterate using filenames to search for firstrun=true
  for (filename <- listConfigs())
    

    
    





