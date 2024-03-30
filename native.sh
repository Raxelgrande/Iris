echo "Building Linux native binaries (glibc, static, x86_64)"
native-image --no-fallback --static -O3 -jar iris-java.jar -o iris
native-image --no-fallback --static -O3 -jar iris-setup-java.jar -o iris-setup
native-image --no-fallback --static -03 -jar misc-flatpak-override-java.jar -o misc-flatpak-override

echo "Packaging Linux native binaries"
7z a -mx7 -mmt0 iris-linux-x86_64.zip iris iris-setup misc-flatpak-override
