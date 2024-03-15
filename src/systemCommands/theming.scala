package iris.theming

import scala.sys.process._
import scala.io.Source
//import java.io._
import java.nio.file.{Files, Path}, java.io.File

import iris.distroFinder._
import iris.themeSelector._
import iris.tui._

def askDesktop() =
  val title = "What is your desktop?"
  val select = chooseOption_string(Seq("Budgie", "Cinnamon", "GNOME", "Xfce"), title)
  if select == "" then println("The user cancelled!")
  else println(select)


// Returns every location of gtk folders as one
def gtkList(): List[String] = 
  val homeThemeLoc = getHome()+"/.themes"
  
  val userList = File(homeThemeLoc).list()
  val sudoList = File("/usr/share/themes").list()

  //you cant mutate lists, else you could write the solution in a cleaner procedural way
  //instead im un nulling this bitch
  val ul =
    if userList != null then
      userList
      .filter(x => File(s"$homeThemeLoc/$x").isDirectory())
      .toList //todo: make a readLoop_list alternative for arrays so you avoid this slow conversion
      else List()
  val sl =
    if sudoList != null then
      sudoList
      .filter(x => File(s"/usr/share/themes/$x").isDirectory())
      .toList
      else List()
  //the tolist and filter also crash this fucker if the bitch array is null so i moved it here
  ul ++ sl 

def gtkUserList(): List[String] =
  val homeThemeLoc = getHome()+"/.themes"
  
  val userList = File(homeThemeLoc).list()

  val ul =
    if userList != null then
      userList
      .filter(x => File(s"$homeThemeLoc/$x").isDirectory())
      .toList //todo: make a readLoop_list alternative for arrays so you avoid this slow conversion
    else List()
  ul

def gtkSudoList(): List[String] =
  val sudoList = File("/usr/share/themes").list()

  val sl =
    if sudoList != null then
      sudoList
      .filter(x => File(s"/usr/share/themes/$x").isDirectory())
      .toList
    else List()
  sl

//TEMPORARY!!! Not perfect, works only on normal user, Iris runs on SUDO.
def libadwaitaSymlink() = //for applying a theme, not for enabling the configuration
  def createSymlink(link: String, target: String) = //link is the symlink it creates, not target
    val output = Path.of(s"$link/${File(target).getName()}")
    Files.deleteIfExists(output)
    Files.createSymbolicLink(output, Path.of(target))

  def copyDir(in: String, out: String, replace: Boolean = false): Unit =
    val newout =
      if replace then out
      else s"$out/${File(in).getName()}"
    val output = Path.of(newout)

    if !File(out).isDirectory() then File(out).mkdirs()
    Files.deleteIfExists(output)

    Files.copy(Path.of(in), output)
    if File(in).isDirectory() then
      val copyfiles = File(in).list()
      for f <- copyfiles do copyDir(s"$in/$f", newout, false)

  def replaceDir(in: String, out: String) = copyDir(in, out, true)
  //copyDir creates a new dir inside out, replaceDir replaces out

  def copyFile(in: String, out: String) =
    val output = Path.of(s"$out/${File(in).getName()}")
    Files.deleteIfExists(output)
    Files.copy(Path.of(in), output)

  val activeTheme = "vimix-dark-jade" //needs to get the value from reading the selected configuration file
  val gtk4Folder = getHome()+"/.config/gtk-4.0/"
  val sudoTheme = "/usr/share/themes/"+activeTheme
  val userTheme = getHome()+"/.themes/"+activeTheme


  val userGtkAssets = getHome()+"/.themes/"+activeTheme+"/gtk-4.0/assets/"
  val userCss = getHome()+"/.themes/"+activeTheme+"/gtk-4.0/gtk.css"
  val userCssDark = getHome()+"/.themes/"+activeTheme+"/gtk-4.0/gtk-dark.css"
  
  if !File(gtk4Folder).exists() then File(gtk4Folder).mkdirs()

  if gtkUserList().contains(activeTheme) then
    createSymlink(gtk4Folder, userGtkAssets)
    //List("ln", "-sf", userGtkAssets, gtk4Folder).!<
    createSymlink(gtk4Folder, userCss)
    //List("ln", "-sf", userCss, gtk4Folder).!<
    createSymlink(gtk4Folder, userCssDark)
    //List("ln", "-sf", userCssDark, gtk4Folder).!<
  else if File(sudoTheme).exists() then
    File(userTheme).mkdirs()
    replaceDir(sudoTheme, userTheme)
    //List("cp", "-rT", sudoTheme, userTheme).!<
    copyDir(userGtkAssets, gtk4Folder)
    //List("ln", "-sf", userGtkAssets, gtk4Folder).!<
    copyFile(userCss, gtk4Folder)
    //List("ln", "-sf", userCss, gtk4Folder).!<
    copyFile(userCssDark, gtk4Folder)
    //List("ln", "-sf", userCssDark, gtk4Folder).!<
  else 
    println("Your selected theme is not installed in the system.")
    System.exit(0)

// List ./icons
def iconList() =
  val homeIconLoc = getHome()+"/.icons"
  
  val userList = File(homeIconLoc).list()
  val sudoList = File("/usr/share/icons/").list()

// Same algorithm of gtkList 
  val ul =
    if userList != null then
      userList
      .filter(x => File(s"$homeIconLoc/$x").isDirectory())
      .toList
      else List()
  val sl =
    if sudoList != null then 
      sudoList
      .filter(x => File(s"/usr/share/icons/$x").isDirectory())
      .toList
      else List()
  ul ++ sl


// GNOME & Budgie theme checking
def gnBgCheckGtk() = List("gsettings", "get", "org.gnome.desktop.interface", "gtk-theme").!!
def gnBgCheckIcon() = List("gsettings", "get", "org.gnome.desktop.interface", "icon-theme").!!
def gnBgCheckCursor() = List("gsettings", "get", "org.gnome.desktop.interface", "cursor-theme").!!
// GNOME Shell theme check (requires user-themes extension)
def gnomeCheckShell() = List("dconf", "read", "/org/gnome/shell/extensions/user-theme/name").!!

// GNOME & Budgie set a theme
def gnBgSetGtk(theme: String) = List("gsettings", "set", "org.gnome.desktop.interface", "gtk-theme", theme).!!
def gnBgSetIcon(theme: String) = List("gsettings", "set", "org.gnome.desktop.interface", "icon-theme", theme).!!
def gnBgSetCursor(theme: String) = List("gsettings", "set", "org.gnome.desktop.interface", "cursor-theme", theme).!!
// GNOME Shell set a theme 
def gnomeSetShell(theme: String) = List("dconf", "write", "/org/gnome/shell/extensions/user-theme/name", s"'$theme'").!!


// Cinnamon theme checking 
def cinnamonCheckGtk() = List("gsettings", "get", "org.cinnamon.desktop.interface", "gtk-theme").!!
def cinnamonCheckIcon() = List("gsettings", "get", "org.cinnamon.desktop.interface", "icon-theme").!!
def cinnamonCheckCursor() = List("gsettings", "get", "org.cinnamon.desktop.interface", "cursor-theme").!!
def cinnamonCheckShell() = List("gsettings", "get", "org.cinnamon.theme", "name").!!

// Cinnamon set a theme
def cinnamonSetGtk(theme: String) = List("gsettings", "set", "org.cinnamon.desktop.interface", "gtk-theme", theme).!! 
def cinnamonSetIcon(theme: String) = List("gsettings", "set", "org.cinnamon.desktop.interface", "icon-theme", theme).!!
def cinnamonSetCursor(theme: String) = List("gsettings", "set", "org.cinnamon.desktop.interface", "cursor-theme", theme).!!
def cinnamonSetShell(theme: String) = List("gsettings", "set", "org.cinnamon.theme", "name", theme).!!

// XFCE theme checking
def xfceCheckGtk() = List("xfconf-query", "-v", "-c", "xsettings", "-p", "/Net/ThemeName").!!
def xfceCheckXfwm() = List("xfconf-query", "-v", "-c", "xfwm4", "-p", "/general/theme").!!
def xfceCheckIcon() = List("xfconf-query", "-v", "-c", "xsettings", "-p", "/Net/IconThemeName").!!
def xfceCheckCursor() = List("xfconf-query", "-v", "-c", "xsettings", "-p", "/Gtk/CursorThemeName").!!

// XFCE set a theme 
def xfceSetGtk(theme: String) = List("xfconf-query", "-n", "-c", "xsettings", "-p", "/Net/ThemeName", "-s", theme).!!
def xfceSetXfwm(theme: String) = List("xfconf-query", "-n", "-c", "xfwm4", "-p", "/general/theme", "-s", theme).!!
def xfceSetIcon(theme: String) = List("xfconf-query", "-n", "-c", "xsettings", "-p", "/Net/IconThemeName", "-s", theme).!!
def xfceSetCursor(theme: String) = List("xfconf-query", "-n", "-c", "xsettings", "-p", "/Gtk/CursorThemeName", "-s", theme).!!

// KDE theme list
// TODO Algorithm that gets the list without fancy stupid KDE shit
def kdeListColorScheme(): List[String] = 
  val kdeGetColorScheme = List("plasma-apply-colorscheme", "-l").!!
  val kdeSplitColorScheme = kdeGetColorScheme.split(" ").toList
  kdeSplitColorScheme
  

def kdeListCursorTheme(): List[String] =
  val kdeGetCursorTheme = List("plasma-apply-cursortheme", "--list-themes").!!
  val kdeSplitCursorTheme = kdeGetCursorTheme.split(" ").toList
  kdeSplitCursorTheme

def kdeListGlobalTheme(): List[String] =
  val kdeGetGlobalTheme = List("plasma-apply-lookandfeel", "-l").!!
  val kdeSplitGlobalTheme = kdeGetGlobalTheme.split(" ").toList
  kdeSplitGlobalTheme

def kdeSetColorScheme(theme: String) = List("plasma-apply-colorscheme", theme).run()
def kdeSetCursorTheme(theme: String) = List("plasma-apply-cursortheme", theme).run()
def kdeSetGlobalTheme(theme: String) = List("plasma-apply-lookandfeel", "-a", theme).run()


// Kvantum theme checking

def kvantumList() = 
  val homeKvantumLoc = getHome()+"/.config/Kvantum"
  val userKvantum = File(homeKvantumLoc).list()
  val sudoKvantum = File("/usr/share/Kvantum").list()

  val ul = 
    if userKvantum != null then
      userKvantum
      .filter(x => File(s"$homeKvantumLoc/$x").isDirectory())
      .toList
      else List()
  val sl =
    if sudoKvantum != null then 
      sudoKvantum
      .filter(x => File(s"/usr/share/Kvantum").isDirectory())
      .toList
      else List()
  ul ++ sl

def kvantumSetTheme(theme: String) = List("kvantummanager", "--set", theme).!!

 
