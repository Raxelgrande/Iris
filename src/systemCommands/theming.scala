package iris.theming
import scala.sys.process._
import scala.io.Source
import java.io._
import iris.distroFinder._

// Lists ./themes , /usr/share/themes and then joins them together
def gtkList() = 
  val homeThemeLoc = getHome()+"/.themes"
  
  val userList = File(homeThemeLoc).list()
  .filter(x => File(s"$homeThemeLoc").isDirectory()).toVector
   
   val sudoList = File("/usr/share/themes").list()
  .filter(x => File(s"/usr/share/themes").isDirectory()).toVector

  val gtkList = userList +: sudoList


// List ./icons
def iconList() =
  val homeIconLoc = getHome()+"/.icons"

  val userList = File(homeIconLoc).list()
  .filter(x => File(s"$homeIconLoc").isDirectory()).toVector

  val sudoList = File("/usr/share/icons").list()
  .filter(x => File(s"/usr/share/icons").isDirectory()).toVector

  val iconList = userList +: sudoList



// GNOME & Budgie theme checking
def gnBgCheckGtk() = List("gsettings", "get", "org.gnome.desktop.interface", "gtk-theme").!!
def gnBgCheckIcon() = List("gsettings", "get", "org.gnome.desktop.interface", "icon-theme").!!
def gnBgCheckCursor() = List("gsettings", "get", "org.gnome.desktop.interface", "cursor-theme").!!
// GNOME Shell theme check (requires user-themes extension)
def gnomeCheckShell() = List("dconf", "read", "/org/gnome/shell/extensions/user-theme/name").!!

// GNOME & Budgie set a theme
//def gnBgSetGtk() = List("gsettings", "set", "org.gnome.desktop.interface", "gtk-theme", pickGtkTheme()).!!
//def gnBgSetIcon() = List("gsettings", "set", "org.gnome.desktop.interface", "icon-theme", pickIconTheme()).!!
//def gnBgSetCursor() = List("gsettings", "set", "org.gnome.desktop.interface", "cursor-theme", pickCursorTheme()).!!
// GNOME Shell set a theme 
//def gnomeSetShell() = List("dconf", "write", "/org/gnome/shell/extensions/user-theme/name", s"'${pickGnomeTheme()}'").!!


// Cinnamon theme checking 
def cinnamonCheckGtk() = List("gsettings", "get", "org.cinnamon.desktop.interface", "gtk-theme").!!
def cinnamonCheckIcon() = List("gsettings", "get", "org.cinnamon.desktop.interface", "icon-theme").!!
def cinnamonCheckCursor() = List("gsettings", "get", "org.cinnamon.desktop.interface", "cursor-theme").!!
def cinnamonCheckShell() = List("gsetitngs", "get", "org.cinnamon.theme", "name").!!

// Cinnamon set a theme
//def cinnamonSetGtk() = List("gsetitngs", "set", "org.cinnamon.desktop.interface", "gtk-theme", pickGtkTheme()).!! 
//def cinnamonSetIcon() = List("gsetitngs", "set", "org.cinnamon.desktop.interface", "icon-theme", pickIconTheme()).!!
//def cinnamonSetCursor() = List("gsetitngs", "set", "org.cinnamon.desktop.interface", "cursor-theme", pickCursorTheme()).!!
//def cinnamonSetShell() = List("gsetitngs", "set", "org.cinnamon.theme", "name", pickCinnamonTheme()).!!

// XFCE theme checking
def xfceCheckGtk() = List("xfconf-query", "-v", "-c", "xsettings", "-p", "/Net/ThemeName").!!
def xfceCheckIcon() = List("xfconf-query", "-v", "-c", "xsettings", "-p", "/Net/IconThemeName").!!
def xfceCheckCursor() = List("xfconf-query", "-v", "-c", "xsettings", "-p", "/Gtk/CursorThemeName").!!

// XFCE set a theme 
//def xfceSetGtk() = List("xfconf-query", "-n", "-c", "xsettings", "-p", "-s", pickGtkTheme()).!!
//def xfceSetIcon() = List("xfconf-query", "-n", "-c", "xsettings", "-p", "-s", pickIconTheme()).!!
//def xfceSetCursor() = List("xfconf-query", "-n", "-c", "xsettings", "-p", "-s", pickCursorTheme()).!!

// MATE theme checking
def mateCheckGtk() = List("dconf", "read", "/org/mate/desktop/interface/gtk-theme").!!
def mateCheckMarco() = List("dconf", "read", "/org/mate/marco/general/theme").!!
def mateCheckIcon() = List("dconf", "read", "/org/mate/desktop/interface/icon-theme").!!
def mateCheckCursor() = List("dconf", "read", "/org/mate/desktop/peripherals/mouse/cursor-theme").!!

//MATE set a theme
//def mateSetGtk() = List("dconf", "write", "/org/mate/desktop/interface/gtk-theme", s"'${pickGtkTheme()}'").!!
//def mateSetMarco() = List("dconf", "write", "/org/mate/marco/general/theme", s"'${pickGtkTheme()}'").!!
//def mateSetIcon() = List("dconf", "write", "/org/mate/desktop/interface/icon-theme", s"'${pickIconTheme()}'").!!
//def mateSetCursor() = List("dconf", "write", "/org/mate/desktop/peripherals/mouse/cursor-theme", s"'${pickCursorTheme()}'").!!

// Kvantum theme checking

def kvantumListThemes() = 
  val homeKvantumLoc = getHome()+"/.config/Kvantum"
    
  val userKvantum = File(homeKvantumLoc).list
  .filter(x => File(s"$homeKvantumLoc").isDirectory()).toVector

  val sudoKvantum = File("/usr/share/Kvantum").list
  .filter(x => File(s"/usr/share/Kvantum").isDirectory()).toVector

  val kvantumList = userKvantum +: sudoKvantum

//def kvantumCheckTheme() = read ~/.config/Kvantum/kvantum.kvconfig
//def kvantumSetTheme() = List("kvantummanager", "--set", pickKvantumTheme()).!!
