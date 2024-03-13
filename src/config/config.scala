package iris.config
import java.io.File
import java.io.FileOutputStream
import scala.io.Source
import iris.distroFinder._
import iris.tui._


def listOfConfigs(): Array[String] = //I changed to array to remove the list conversion overhead
  val irisConfLoc = getHome()+"/.config/Iris/"
  val irisConfList = File(irisConfLoc).list()

  if irisConfList != null then
    irisConfList
  else Array()

def linesOfAllConfigs(): String =
  var allLines = ""
  for filename <- listOfConfigs() do
    allLines += readConfig_string(filename)
  allLines

def readConfig(filename: String): Iterator[String] =
  Source.fromFile(getHome()+"/.config/Iris/"+filename).getLines()

def readConfig_vector(filename: String): Vector[String] = readConfig(filename).toVector

def readConfig_list(filename: String): List[String] = readConfig(filename).toList

def readConfig_array(filename: String): Array[String] = readConfig(filename).toArray

def readConfig_string(filename: String): String =
  readConfig(filename)
    .map(x => x + '\n')
    .mkString

def readAllConfigs(): Vector[String] = //1 whole string per config to avoid matrices
  def groupConfigs(files: Array[String], cfgList: Vector[String] = Vector(), i: Int = 0): Vector[String] =
    if i >= files.length then
      cfgList
    else
      groupConfigs(files, cfgList :+ readConfig_string(files(i)), i+1)

  val files = listOfConfigs()
  groupConfigs(files)
  
//outdated, will have to rewrite some tui functions first
// def selectConfiguration(config: String): String =
//   if config != "" then
//     config
//   else 
//     readLoop_getListString(listOfConfigs())