dir_iris="iris/*.scala iris/*/*.scala"
dir_setup="setup-iris/*.scala setup-iris/*/*.scala"
dir_lib="lib/*/*.scala"
dir_misc="misc/*.scala"


echo "Building Iris"
scalac $dir_iris $dir_lib -d iris.jar

echo "Building setup-iris"
scalac $dir_setup $dir_lib -d setup-iris.jar

echo "Building misc-flatpak-override"
scalac $dir_misc -d misc-flatpak-override.jar

