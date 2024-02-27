# Iris
This repository is newborn. This readme is under development too, expect a more polished experience soon.

Iris is a Linux themeing manager that facilitates OS customization, allowing a consistent look and feel of your desktop, without the hassle and experience usually needed just to change how things look.

We use a simple to use and configure TUI interface that will set up everything for you, so your only worry is getting some nice themes to use.

# Features/Roadmap
- First run interactive configurator that intelligently installs dependencies and sets up your system for theming (WIP)
- Automatization and configuration files to save your settings (TODO)
- Set and view list of available GTK3/4 Themes (WIP)
- Set Libadwaita theme (TODO)
- Set Desktop Environment theme (all major/mainstream DEs supported) (WIP)
- Set Kvantum theme for QT programs (WIP)
- Flatpak support for GTK3/4, Libadwaita and Kvantum (WIP)
- Set Icon theme (WIP)
- Set Cursor theme (WIP)

# Requirements & how to use
Iris requires the following to work:
- Scala 3 or Java 8
- Linux-based system
- Systemd (optional, for distro detection)

# Download
TODO

# Compile from source
You need Scala 3 to build Iris from source. You can use the scalac compiler or scala-cli.

You can compile Iris directly with scalac this way:
```
scalac src/*.scala src/*/*.scala -d iris.jar
```
This JAR can be launched with scala or scala-cli

For more information and alternatives on compiling Iris, check the link below.
TODO
