package utils;

import java.util.Scanner;

public class ConsoleIOHandler {
    public static Request getInput() {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] parts = line.split(" ", 2); // Split into two parts at the first occurrence of space
        return new Request(parts[0], parts[1]);
    }

    public static void writeOutput(String response) {
        System.out.println(response);
    }
}
