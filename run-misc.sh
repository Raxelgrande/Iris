echo "Building Misc"
scalac $dir_misc -d misc-flatpak-override.jar && echo "Running Misc" && scala misc-flatpak-override.jar && rm scala misc-flatpak-override.jar
