echo "Building Linux native binaries (glibc, static, x86_64)"
native-image --no-fallback --static -O3 -jar iris-java.jar -o Iris
native-image --no-fallback --static -O3 -jar setup-iris-java.jar -o SetupIris
native-image --no-fallback --static -03 -jar misc-flatpak-override-java.jar -o MiscFlatpakOverride

echo "Packaging Linux native binaries"
7z a -mx7 -mmt0 iris-linux-x86_64.zip Iris SetupIris MiscFlatpakOverride
