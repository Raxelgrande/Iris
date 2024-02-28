package iris.theming
import scala.sys.process._
import scala.io.Source
import java.io._
import iris.distroFinder._
import iris.themeSelector._

// Lists ./themes , /usr/share/themes and then joins them together
def gtkList(): List[String] = 
  val homeThemeLoc = s"${getHome()}/.themes"
  
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
def xfceCheckIcon() = List("xfconf-query", "-v", "-c", "xsettings", "-p", "/Net/IconThemeName").!!
def xfceCheckCursor() = List("xfconf-query", "-v", "-c", "xsettings", "-p", "/Gtk/CursorThemeName").!!

// XFCE set a theme 
def xfceSetGtk(theme: String) = List("xfconf-query", "-n", "-c", "xsettings", "-p", "-s", theme).!!
def xfceSetIcon(theme: String) = List("xfconf-query", "-n", "-c", "xsettings", "-p", "-s", theme).!!
def xfceSetCursor(theme: String) = List("xfconf-query", "-n", "-c", "xsettings", "-p", "-s", theme).!!

// MATE theme checking
def mateCheckGtk() = List("dconf", "read", "/org/mate/desktop/interface/gtk-theme").!!
def mateCheckMarco() = List("dconf", "read", "/org/mate/marco/general/theme").!!
def mateCheckIcon() = List("dconf", "read", "/org/mate/desktop/interface/icon-theme").!!
def mateCheckCursor() = List("dconf", "read", "/org/mate/desktop/peripherals/mouse/cursor-theme").!!

//MATE set a theme
def mateSetGtk(theme: String) = List("dconf", "write", "/org/mate/desktop/interface/gtk-theme", s"'$theme'").!!
def mateSetMarco(theme: String) = List("dconf", "write", "/org/mate/marco/general/theme", s"'$theme'").!!
def mateSetIcon(theme: String) = List("dconf", "write", "/org/mate/desktop/interface/icon-theme", s"'$theme'").!!
def mateSetCursor(theme: String) = List("dconf", "write", "/org/mate/desktop/peripherals/mouse/cursor-theme", s"'$theme'").!!

// Kvantum theme checking

def kvantumListThemes() = 
  val homeKvantumLoc = getHome()+"/.config/Kvantum"
    
  val userKvantum = File(homeKvantumLoc).list
  .filter(x => File(s"$homeKvantumLoc").isDirectory()).toList

  val sudoKvantum = File("/usr/share/Kvantum").list
  .filter(x => File(s"/usr/share/Kvantum").isDirectory()).toList

  val kvantumList = userKvantum +: sudoKvantum

//def kvantumCheckTheme() = read ~/.config/Kvantum/kvantum.kvconfig
//def kvantumSetTheme() = List("kvantummanager", "--set", pickKvantumTheme()).!!
