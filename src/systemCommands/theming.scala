package iris.theming
import scala.sys.process._
import scala.io.Source
import java.io._
import iris.distroFinder._
import iris.themeSelector._

// Lists ./themes , /usr/share/themes and then joins them together
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

 
