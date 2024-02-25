package iris.tui

import java.io.File
import scala.sys.process.*
import scala.io.StdIn.readLine

//Handy functions for reading user input while printing information in a fancy way
//Some of these functions also clear the terminal, such as spawnAndRead()
//The "loop" functions are good for when you only want to proceed once the user specifies a correct answer

def askPrompt(ui: String, clear: Boolean = true): Boolean = // User input for a Yes/No question
  val yellow = foreground("yellow")
  val default = foreground("default")
  val answer =
    if clear then
      spawnAndRead(s"\u001B[3J\u001B[1J\u001B[H$ui ${yellow}(y/n)$default\n").toLowerCase
    else
      readUserInput(s"$ui ${yellow}(y/n)$default\n").toLowerCase
  if answer == "yes" || answer == "y" then
    true
  else
    false

def answerToNumber(str: String): Int =
  try
    str.toInt
  catch
    case e: Exception => -1

def readUserInput(message: String = ""): String =
  if message != "" then
    println(message)
  readLine()

def spawnAndRead(message: String): String = readUserInput(s"\u001B[3J\u001B[1J\u001B[H$message") // Clears the screen and readUserInput

def pressToContinue(message: String = ""): String = readUserInput(message + "\n\nPress enter to continue")

def getList(l: List[String], txt: String = s"Choose an entry\n\n${foreground("green")}${0}:${foreground("default")} Exit\n\n", i: Int = 0): String =
  if i >= l.length then
    txt
  else
    val line = s"${foreground("green")}${i+1}:${foreground("default")} ${l(i)}\n"
    getList(l, txt + line, i+1)

def readLoop(txt: String, maxval: Int): Int =
  val answer = answerToNumber(spawnAndRead(txt))
  if answer == 0 || (1 to maxval).contains(answer) then
    answer
  else
    readLoop(txt, maxval)

def readLoop_list(l: List[String], title: String = s"Choose an entry\n\n${foreground("green")}${0}:${foreground("default")} Exit\n\n"): Int =
  val txt_list = getList(l, title)
  readLoop(txt_list, l.length)

def readLoop_int(txt: String): Int =
  val answer = answerToNumber(spawnAndRead(txt))
  if answer != -1 then
    answer
  else
    readLoop_int(txt)

def readLoop_dir(txt: String): String =
  val answer = spawnAndRead(txt)
  if File(answer).isDirectory() then
    answer
  else if answer == "" then
    "."
  else
    pressToContinue("That is not a real path in your system!")
    readLoop_dir(txt)

def readLoop_file(txt: String): String =
  val answer = spawnAndRead(txt)
  if File(answer).isFile() then
    answer
  else if answer == "" then
    "."
  else
    pressToContinue("That is not a real file in your system!")
    readLoop_dir(txt)
