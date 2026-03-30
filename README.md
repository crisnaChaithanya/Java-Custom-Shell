# Java Custom Shell (CLI Interpreter)

I built this project to learn how a command-line shell (like Bash or Zsh) actually works under the hood. It’s a functional terminal written in Java that can handle built-in commands and run external programs.

## 🌟 What it can do

* **Command Loop (REPL):** It stays open and ready for input, just like a real terminal.
* **Built-in Commands:** I implemented `echo`, `pwd`, `cd`, `type`, and `exit` from scratch.
* **Smart Argument Parsing:** * It handles **Single Quotes** (`' '`) and **Double Quotes** (`" "`) properly.
    * It knows how to keep spaces together if they are inside quotes (e.g., `echo "hello world"`).
* **Running System Programs:** If you type a command it doesn't recognize (like `ls` or `python`), it searches the system `PATH` to find and run that program.
* **Directory Navigation:** The `cd` command supports absolute paths, relative paths, and the `~` shortcut for the home directory.

## 🧠 What I learned while building this

This project was a great way to dive into some core Java and OS concepts:

1.  **Parsing with Logic:** Instead of just using `split(" ")`, I wrote a manual loop to track "quote states." This was the hardest part—making sure the shell knows when a space is a separator and when it's just part of a sentence.
2.  **Process Management:** I learned how to use Java's `ProcessBuilder` to start other programs and connect them to the terminal.
3.  **Environment Variables:** I learned how to pull information like `PATH` and `HOME` from the operating system to make the shell dynamic.
4.  **File Handling:** I used the `File` class to resolve paths and make sure `cd` actually moves to the right place

## 🛠️ Tech Stack

* **Language:** Java 21+ (Utilizing modern features like Switch Expressions and Pattern Matching).
* **Core APIs:** `java.lang.ProcessBuilder`, `java.io.File`, `java.util.Scanner`, `java.lang.System`.
* **Concepts:** Tokenization, I/O Stream Redirection, Environment Variable Resolution, Process Management.

## 💻 Setup & Usage

### 1. Prerequisites
Ensure you have **Java 21** or higher installed on your system.
```bash
java -version

1. Installation
git clone [https://github.com/crisnaChaithanya/Java-Custom-Shell.git]
cd Java-Custom-Shell

2. Compilation
javac Main.java

3.Running the shell
java Main

