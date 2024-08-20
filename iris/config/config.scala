package iris.config
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.io.FileWriter
import scala.io.Source
import scala.util.matching.Regex
import bananatui.*
import iris.theming._


def listOfConfigs(): Array[String] = //I changed to array to remove the list conversion overhead
  val irisConfLoc = getHome()+"/.config/Iris/"
  val irisConfList = File(irisConfLoc).list()

  if irisConfList != null then
    irisConfList
  else Array()

def getConfig(filename: String): Iterator[String] =
  Source.fromFile(getHome()+"/.config/Iris/"+filename).getLines()

def getConfig_vector(filename: String): Vector[String] = getConfig(filename).toVector

def getConfig_list(filename: String): List[String] = getConfig(filename).toList

def getConfig_array(filename: String): Array[String] = getConfig(filename).toArray

def getConfig_string(filename: String): String =
  getConfig(filename)
    .map(x => x + '\n')
    .mkString

def getAllConfigs(): Vector[String] = //1 whole string per config to avoid matrices
  def groupConfigs(files: Array[String], cfgList: Vector[String] = Vector(), i: Int = 0): Vector[String] =
    if i >= files.length then
      cfgList
    else
      groupConfigs(files, cfgList :+ getConfig_string(files(i)), i+1)

  val files = listOfConfigs()
  groupConfigs(files)
  
def readConfig(filename: String, line: String, conf: String = ""): String =
  val value = Regex (line + "[a-zA-Z0-9\\-\\_]+")
  
  val config =
    if conf != "" then conf
    else getConfig_string(filename)
  val search = value.findFirstIn(config).mkString
  search

def readConfigValue(filename: String, line: String, conf: String = ""): String =
  val search = readConfig(filename, line, conf)
  val result = search.replaceAll(line, "")
  result

def loadConfig(filename: String) =
  val conf = getConfig_string(filename)

  val checkDesktopEnv = readConfigValue(filename, "desktop_environment=", conf)
  val checkGtk = readConfigValue(filename, "gtktheme=", conf)
  val checkLibadwaita = readConfigValue(filename, "libadwaita=", conf)
  val checkLibadwaitaVariant = readConfigValue(filename, "libadwaitavariant=", conf)
  val checkIcon = readConfigValue(filename, "icontheme=", conf)
  val checkCursor = readConfigValue(filename, "cursortheme=", conf)
  val checkDesktopTheme = readConfigValue(filename, "desktoptheme=", conf)
  val checkKvantum = readConfigValue(filename, "kvantumtheme=", conf)
  val qtState = readConfigValue(filename, "qt5ct=", conf)
  val flatpakState = readConfigValue(filename, "flatpak=", conf)
  val checkFlatpakGtk = readConfigValue(filename, "flatpakgtk=", conf)
  val checkFlatpakIcon = readConfigValue(filename, "flatpakicon=", conf)


  val themedesktop = checkDesktopEnv match
    case "GNOME" =>
      gnBgSetGtk(checkGtk)
      if checkLibadwaita == "ResetTheme" then 
        libadwaitaReset()
        libadwaitaWriteVariant(checkLibadwaitaVariant)
      else 
        libadwaitaSymlink(checkLibadwaita)
        libadwaitaWriteVariant(checkLibadwaitaVariant)
      gnomeSetShell(checkDesktopTheme)
      gnBgSetIcon(checkIcon)
      gnBgSetCursor(checkCursor)

    case "Budgie" =>
      gnBgSetGtk(checkGtk)
      if checkLibadwaita == "ResetTheme" then 
        libadwaitaReset()
        libadwaitaWriteVariant(checkLibadwaitaVariant)
      else 
        libadwaitaSymlink(checkLibadwaita)
        libadwaitaWriteVariant(checkLibadwaitaVariant)
      gnBgSetIcon(checkIcon)
      gnBgSetCursor(checkCursor)
      
    
    case "Cinnamon" =>
      cinnamonSetGtk(checkGtk)
      cinnamonSetShell(checkDesktopTheme)
      if checkLibadwaita == "ResetTheme" then 
        libadwaitaReset()
        libadwaitaWriteVariant(checkLibadwaitaVariant)
      else 
        libadwaitaSymlink(checkLibadwaita)
        libadwaitaWriteVariant(checkLibadwaitaVariant)
        libadwaitaCinnamonWriteVariant(checkLibadwaitaVariant)
      cinnamonSetIcon(checkIcon)
      cinnamonSetCursor(checkCursor)
      
    
    case "Xfce" =>
      xfceSetGtk(checkGtk)
      xfceSetXfwm(checkGtk)
      if checkLibadwaita == "ResetTheme" then 
        libadwaitaReset()
        libadwaitaWriteVariant(checkLibadwaitaVariant)
      else 
        libadwaitaSymlink(checkLibadwaita)
        libadwaitaWriteVariant(checkLibadwaitaVariant)
      xfceSetIcon(checkIcon)
      xfceSetCursor(checkCursor)
      
  if qtState == "true" then 
    qt5writeConf("kvantum", checkIcon)
    qt6writeConf("kvantum", checkIcon)
    kvantumSetTheme(checkKvantum)

  if flatpakState == "true" then
  flatpakSetGtk(checkFlatpakGtk)
  flatpakSetIcons(checkFlatpakIcon)
  

def createConfig(confname: String) =
  val confnameNoWhitespace = confname.filterNot(_.isWhitespace)
  val settings = String(s"themename=$confnameNoWhitespace\ndesktop_environment=\ngtktheme=\nlibadwaita=\nlibadwaitavariant=\nicontheme=\ncursortheme=\n" +
  "desktoptheme=\nkvantumtheme=\nqt5ct=\nflatpak=\nflatpakgtk=\nflatpakicon=")
  val configLocation = getHome()+"/.config/Iris/"
  

  if File(configLocation).exists() == false then //creates config location
    File(configLocation).mkdirs()
    val makeconf = FileWriter(File(configLocation+confnameNoWhitespace))
    makeconf.write(settings)
    makeconf.close()
  else if File(configLocation+confnameNoWhitespace).exists() == true then
    println("TODO, TUI error message config already exists, reasks question")
  else 
    val makeconf = FileWriter(File(configLocation+confnameNoWhitespace))
    makeconf.write(settings)
    makeconf.close()

def replaceLine(confname: String, line: String, newvalue: String) = //newvalue is the line like "gtktheme=" + the value you want
  val configLocation = getHome()+"/.config/Iris/"
  
  val currentconf = getConfig_string(confname)
  val currentconfList = getConfig_list(confname)
  
  val emptyreplace = currentconf.replaceAll(line, newvalue)
  val replace = currentconf.replaceAll(line+"[a-zA-Z0-9\\-\\_]+", newvalue)
  
  if currentconfList.contains(line) then 
    val emptywriter = FileWriter( File(configLocation+confname))
    emptywriter.write(emptyreplace)
    emptywriter.close()
  else
    val writer = FileWriter( File(configLocation+confname))
    writer.write(replace)
    writer.close()

def deleteConfig(confname: String) =
  val configLocation = getHome()+"/.config/Iris/"+confname 
  if File(configLocation).exists() then 
    File(configLocation).delete()
  else 
    pressToContinue(s"The configuration $confname doesn't exist.")

def getHome() = System.getProperty("user.home")
//outdated, will have to rewrite some tui functions first
// def selectConfiguration(config: String): String =
//   if config != "" then
//     config
//   else 
//     readLoop_getListString(listOfConfigs())

  
