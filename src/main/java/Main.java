import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static String currentDir = System.getProperty("user.dir");
    private static final String HOME_DIR = System.getenv("HOME");

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("$ ");
            System.out.flush();
            String input = scanner.nextLine();
            List<String> arguments = parseArgs(input);
            if (arguments.isEmpty()) continue;
            String cmd = arguments.get(0);

            switch (cmd) {
                case "exit" -> System.exit(0);
                case "pwd"  -> System.out.println(currentDir);
                case "cd"   -> handleCd(arguments);
                case "type" -> handleType(arguments);
                case "echo" -> handleEcho(arguments);
                default     -> handleExternalCommand(arguments);
            }
        }
    }
    private static void handleCd(List<String> args) {
        if (args.size() < 2) return;
        String dir = args.get(1).equals("~") ? HOME_DIR : args.get(1);
        File file = dir.startsWith("/") ? new File(dir) : new File(currentDir, dir);

        try {
            if (file.exists() && file.isDirectory()) {
                currentDir = file.getCanonicalPath();
            } else {
                System.out.println("cd: " + dir + ": No such file or directory");
            }
        } catch (IOException e) {
            System.out.println("cd: " + dir + ": No such file or directory");
        }
    }
    private static void handleType(List<String> args) {
        if (args.size() < 2) return;
        String target = args.get(1);
        List<String> builtins = List.of("type", "exit", "echo", "pwd", "cd");

        if (builtins.contains(target)) {
            System.out.println(target + " is a shell builtin");
        } else {
            String path = getExecutablePath(target);
            System.out.println(path == null ? target + ": not found" : target + " is " + path);
        }
    }
    private static void handleEcho(List<String> args) {
        for (int i = 1; i < args.size(); i++) {
            System.out.print(args.get(i) + (i == args.size() - 1 ? "" : " "));
        }
        System.out.println();
    }
    private static void handleExternalCommand(List<String> args) {
        String cmd = args.get(0);
        String exePath = getExecutablePath(cmd);
        if (exePath == null) {
            System.out.println(cmd + ": command not found");
        } else {
            try {
                new ProcessBuilder(args).inheritIO().start().waitFor();
            } catch (Exception e) {
                System.out.println("error running the command");
            }
        }
    }
    private static List<String> parseArgs(String command) {
        List<String> result = new ArrayList<>();
        StringBuilder currentWord = new StringBuilder();
        boolean inSingle = false, inDouble = false;

        for (int i = 0; i < command.length(); i++) {
            char c = command.charAt(i);
            if (c == '\'' && !inDouble) inSingle = !inSingle;
            else if (c == '\"' && !inSingle) inDouble = !inDouble;
            else if (c == ' ' && !inSingle && !inDouble) {
                if (!currentWord.isEmpty()) {
                    result.add(currentWord.toString());
                    currentWord.setLength(0);
                }
            } else {
                currentWord.append(c);
            }
        }
        if (!currentWord.isEmpty()) result.add(currentWord.toString());
        return result;
    }
    private static String getExecutablePath(String command) {
        String pathEnv = System.getenv("PATH");
        if (pathEnv == null) return null;
        for (String dir : pathEnv.split(File.pathSeparator)) {
            File file = new File(dir, command);
            if (file.exists() && file.canExecute()) return file.getAbsolutePath();
        }
        return null;
    }
}