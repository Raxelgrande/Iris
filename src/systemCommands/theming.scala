package iris.theming
import scala.sys.process._

// GNOME & Budgie theme checking
def gnBgCheckGtk() = List("gsettings", "get", "org.gnome.desktop.interface", "gtk-theme").!!
def gnBgCheckIcon() = List("gsettings", "get", "org.gnome.desktop.interface", "icon-theme").!!
def gnBgCheckCursor() = List("gsettings", "get", "org.gnome.desktop.interface", "cursor-theme").!!
// GNOME Shell theme check (requires user-themes extension)
def gnomeCheckShell() = List("dconf", "read", "/org/gnome/shell/extensions/user-theme/name").!!

// GNOME & Budgie set a theme
def gnBgSetGtk() = List("gsettings", "set", "org.gnome.desktop.interface", "gtk-theme", selGtkTheme()).!!
def gnBgSetIcon() = List("gsettings", "set", "org.gnome.desktop.interface", "icon-theme", selIconTheme()).!!
def gnBgSetCursor() = List("gsettings", "set", "org.gnome.desktop.interface", "cursor-theme", selCursorTheme()).!!
// GNOME Shell set a theme 
def gnomeSetShell() = List("dconf", "write", "/org/gnome/shell/extensions/user-theme/name", s"'${selGnomeTheme()}'").!!


// Cinnamon theme checking 
def cinnamonCheckGtk() = List("gsettings", "get", "org.cinnamon.desktop.interface", "gtk-theme").!!
def cinnamonCheckIcon() = List("gsettings", "get", "org.cinnamon.desktop.interface", "icon-theme").!!
def cinnamonCheckCursor() = List("gsettings", "get", "org.cinnamon.desktop.interface", "cursor-theme").!!
def cinnamonCheckShell() = List("gsetitngs", "get", "org.cinnamon.theme", "name").!!

// Cinnamon set a theme
def cinnamonSetGtk() = List("gsetitngs", "set", "org.cinnamon.desktop.interface", "gtk-theme", selGtkTheme()).!! 
def cinnamonSetIcon() = List("gsetitngs", "set", "org.cinnamon.desktop.interface", "icon-theme", selIconTheme()).!!
def cinnamonSetCursor() = List("gsetitngs", "set", "org.cinnamon.desktop.interface", "cursor-theme", selCursorTheme()).!!
def cinnamonSetShell() = List("gsetitngs", "set", "org.cinnamon.theme", "name", selCinnamonTheme()).!!

// XFCE theme checking
def xfceCheckGtk() = List("xfconf-query", "-v", "-c", "xsettings", "-p", "/Net/ThemeName").!!
def xfceCheckIcon() = List("xfconf-query", "-v", "-c", "xsettings", "-p", "/Net/IconThemeName").!!
def xfceCheckCursor() = List("xfconf-query", "-v", "-c", "xsettings", "-p", "/Gtk/CursorThemeName").!!

// XFCE set a theme 
def xfceSetGtk() = List("xfconf-query", "-n", "-c", "xsettings", "-p", "-s", selGtkTheme()).!!
def xfceSetIcon() = List("xfconf-query", "-n", "-c", "xsettings", "-p", "-s", selIconTheme()).!!
def xfceSetCursor() = List("xfconf-query", "-n", "-c", "xsettings", "-p", "-s", selCursorTheme()).!!

// 