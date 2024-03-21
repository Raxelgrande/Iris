# Banana TUI

This library lets you do many TUI-related tasks, such as reading user input, clearing your screen, reading an input based on a limited set of choices, setting terminal colors, etc.

This library relies on ANSI escape codes, and it works on the great vast majority of terminal screens an emulators. So far, the only one that didn't work for me is Powershell's default terminal emulator.


## Main functions

```scala
def spawnScreen(ui: String)
```
Clears the screen and the terminal history and prints ```ui```.

---

```scala
def clear()
```
Clears the terminal screen and history.

---

```scala
def saveScreen()
```
Saves the current terminal screen.

---

```scala
def restoreScreen()
```
Restores the terminal screen that was previously saved.

---

```scala
def moveCursor(mode: String, lines: Int)
```
Moves the cursor up and down, depending on the mode.

If mode is "up" then the cursor moves up, otherwise it goes down.

---

```scala
def clearBelowCursor(lines: Int)
```
Clear a certain amount of lines below the cursor.

---

```scala
def printStatus(msg: String, isError: Boolean = true)
```
Prints a fancy error or warning message. Set ```isError``` to false to print a warning.

---

## Color functions

```scala
def foreground(color: String = "default"): String
```
Returns the code to set the foreground color in the terminal, that being the text color.

The function does not set a specific color, instead it sets the color equivalent to your terminal's palette.

To set the color, use the string this function returns in your next print.

### Supported colors
* black
* red
* green
* yellow
* blue
* magenta
* cyan
* white
* default
* reset

Default is what the function returns if you pass an incorrect color string. Default is also what is used for text in your terminal by default.

---

```scala
def background(color: String = "default"): String
```
Returns the code to set the backgroun color in the terminal.

The function does not set a specific color, instead it sets the color equivalent to your terminal's palette.

To set the color, use the string this function returns in your next print.

### Supported colors
* black
* red
* green
* yellow
* blue
* magenta
* cyan
* white
* default
* reset

Default is what the function returns if you pass an incorrect color string.

---

## User input functions

```scala
def readUserInput(message: String = ""): String
```
Reads user input. If ```message``` is not an empty string, it also prints it.

---

```scala
def spawnAndRead(message: String): String
```
Clears the screen, prints ```message``` and returns the user input.

---

```scala
def askPrompt(ui: String, clear: Boolean = true): Boolean
```
Clears the screen, prints ```message``` with an additional (y/n) and returns true or false, depending if the user answered y/yes or not.

---

```scala
def answerToNumber(str: String): Int
```
Attempts to convert a string to an int. If the string does not represent an int, it returns -1.

---

```scala
def pressToContinue(message: String = ""): String
```
Prints ```message``` with an additional "press enter to continue" and reads user input.

---

```scala
def readLoop(txt: String, maxval: Int): Int
```
Prints ```txt``` and reads the user's answer. If the answer is not a number, 0 or below or equal than ```maxval```, the user has to answer again.

---

```scala
def chooseOption(l: Seq[String], title: String = "Choose an entry", first: String = "Exit"): Int
```
Groups all elements of ```l``` together nicely, with a ```title``` above, and the first option of name ```first```. The user has to select the value of an option.

The function returns the value the user chose.

---

```scala
def chooseOption_array(l: Array[String], title: String = "Choose an entry", first: String = "Exit"): Int
```
Just lik ```chooseOption()```, but with arrays.

Groups all elements of ```l``` together nicely, with a ```title``` above, and the first option of name ```first```. The user has to select the value of an option.

The function returns the value the user chose.

---

```scala
def chooseOption_string(l: Seq[String], title: String = "Choose an entry", first: String = "Exit"): String
```
```scala
def chooseOption_string(l: Array[String], title: String = "Choose an entry", first: String = "Exit"): String
```
Just like ```chooseOption()```, but it's nicer to use and you get the list element directly.

Groups all elements of ```l``` together nicely, with a ```title``` above, and the first option of name ```first```. The user has to select the value of an option.

The function returns the element of ```l``` the user chose, or an empty string if the choice was 0.

---

```scala
def readInt(txt: String): Int
```
Prints ```txt``` and reads the user input, if the input is not an int, it keeps reading.

---

```scala
def chooseOption_dir(txt: String): String
```
Similar to ```chooseOption()```, but the user has to type the path to a directory. If the answer is an empty string, the function assumes the current working directory.

If the answer does not lead to a directory, the function asks for input.

---

```scala
def chooseOption_file(txt: String): String
```
Similar to ```chooseOption()```, but the user has to type the path to a file.

If the answer does not lead to a file, the function asks for input again.
