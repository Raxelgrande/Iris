package fileops

import java.io.File, java.nio.file.Files, java.nio.file.Path

def deleteDirectory(dir: String): Unit =
  val p_dir = Path.of(dir)
  if File(dir).isDirectory() then
    val subpaths = File(dir).list()
    for p <- subpaths do
      deleteDirectory(s"$dir/$p")
    Files.delete(p_dir)
  else
    Files.delete(p_dir)
