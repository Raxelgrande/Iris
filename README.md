# Iris
Iris is a Linux themeing manager that facilitates Linux OS customization, allowing a consistent look and feel of your desktop, without the hassle and experience usually needed just to change how things look.

We use a simple to use TUI interface and ship a companion program (Setup-Iris) that will set up everything for you, so your only worry is getting some nice themes to use. You can get nice themes [here](https://www.gnome-look.org/)

For frequently updated themes that support all of the theming features in Iris, we recommend you to use anything made by: [VinceLiuice](https://github.com/vinceliuice), [EliverLara](https://github.com/EliverLara), [Catppuccin](https://github.com/catppuccin) and [Dracula](https://github.com/dracula)

# Features & Showcase
- First run interactive configurator that intelligently installs dependencies and sets up your system for theming
- Easy to use, make and edit configuration files to save your theming settings
- BananaTUI library for a simple but intuitive interface
- Apply all theming settings at once (allowing for a one-click, change how all GUIs look at once)
- Quicklaunch of your theming configuration file (also allowing basic automation by using third party tools like cron)
- No hassle Libadwaita/GTK4 theming (so easy, it's easy to break by using themes that lack Libadwaita support, also enables fast testing for theme developers)
- Set Desktop Environment theme (currently supports Budgie, Cinnamon, GNOME, Xfce)
- Set Icon, Cursor and GTK3 themes
- No hassle QT theming using Kvantum, combined with the power of qtXct to allow things like changing QT's icon theme
- Flatpak support for the features mentioned above (GTK3/4, Libadwaita and Kvantum)

Video showcasing Iris applying a configuration with the Vimix Jade Theme by VinceLiuice


https://github.com/Raxelgrande/Iris/assets/93939943/57324eb5-f219-481e-a724-6b3debc60a48



# Requirements & how to use
Iris requires the following to work:
- Scala 3 or Java 11 (GraalVM to compile a native binary)
- Linux-based system
- Filesystem Hierarchy Standard support (only mayor distribution affected is NixOS)
- Systemd (optional, for distro detection)
- flatpak (optional), qt5ct, qt6ct, kvantum (Iris should automatically install them, take this as troubleshooting info)

When you download Iris in your format of choice (.jar file or native) you will see three files: iris, setup-iris and misc-flatpak-override
Iris is the principal part of the program, what you will use the most. 
Setup-Iris is the first run configuration to get all of the required system dependencies up and going, due to this, it requires ROOT, so checking the code is recommended if you like to have a secure system.
Misc is a small addon to Setup-Iris that gets some flatpak overrides up separate from Setup-Iris due to it requiring ROOT (Misc and Iris run on your normal user, no launch as ROOT/SUDO needed).

# Please launch them in this order the first time you download Iris: Setup-Iris -> Misc (optional, for flatpaks) -> Iris

As soon as you launch Iris, you will see something this:

![Captura de pantalla_2024-03-30_17-10-02](https://github.com/Raxelgrande/Iris/assets/93939943/a7900d26-fa72-4f28-87a6-14663c33728a)

You navigate Iris by typing a Number (the ones displayed at the left of the terminal) or sometimes pressing Enter.

If you want to launch your configuration file without entering Iris (what you need for automation), copy the name you gave it (either by going to "Modify Configurations" or HOME-FOLDER/.config/Iris/config-name)
Then add the configuration name as an argument when you launch Iris:
```
java -jar iris-java.jar CONFIG-NAME
```

# Download
You can find the newest Iris release [here](https://github.com/Raxelgrande/Iris/releases)

# Documentation
Documentation is being written, please wait warmly.

# Compile from source
You need Scala 3 to build Iris from source, and you can either use Scala-CLI or the scala compiler directly.

[Building documentation](/doc/build.md)
