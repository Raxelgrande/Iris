dir_iris="iris/*.scala iris/*/*.scala"
dir_setup="setup-iris/*.scala setup-iris/*/*.scala"
dir_lib="lib/*/*.scala"
dir_misc="misc/*.scala"


echo "Building lightweight JAR"
scalac $dir_iris $dir_lib -d iris.jar
scalac $dir_setup $dir_iris -d setup-iris.jar
scalac $dir_misc -d misc-flatpak-override.jar


echo "Building Fat JAR"
steam-run scala-cli --power package iris lib --assembly --preamble=false --jvm 11 -o iris-java.jar
steam-run scala-cli --power package setup-iris lib --assembly --preamble=false --jvm 11 -o setup-iris-java.jar
steam-run scala-cli --power package misc --assembly --preamble=false --jvm 11 -f -o misc-flatpak-override-java.jar

