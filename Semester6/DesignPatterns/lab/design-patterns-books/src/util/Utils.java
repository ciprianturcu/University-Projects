package util;
import java.util.Scanner;

public class Utils {
    public static String safeNextLine(Scanner scanner, String errorMessage) {
        String input;
        while ((input = scanner.nextLine()).isEmpty()) {
            System.out.println(errorMessage);
        }
        return input;
    }
}