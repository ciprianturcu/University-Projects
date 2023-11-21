import java.util.Scanner;

public class Main {

    private static void printFaMenu() {
        System.out.println("1. Display the states");
        System.out.println("2. Display the alphabet");
        System.out.println("3. Display the initial state");
        System.out.println("4. Display the final states");
        System.out.println("5. Display the transitions");
        System.out.println("6. Display if given sequence is accepted by the DFA");
    }

    private static void printScanProgramMenu() {
        System.out.println("1. p1.txt");
        System.out.println("2. p1err.txt");
        System.out.println("3. p2.txt");
        System.out.println("4. p3.txt");
    }

    private static void finiteAutomata() {
        String filePathIdentifiers = "src/identifiers.txt";
        String filePathConsts = "src/intConst.txt";
        String filePathfa = "src/fa.txt";

        FiniteAutomata fa = new FiniteAutomata();
        //fa.scanFa(filePathIdentifiers);
        fa.scanFa(filePathConsts);
        //fa.scanFa(filePathfa);

        printFaMenu();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Your option: ");

        String option = scanner.nextLine();

        while (true) {

            switch (option) {
                case "0":
                    return;
                case "1":
                    System.out.println("STATES: ");
                    System.out.println(fa.getStateSet());
                    System.out.println();
                    break;

                case "2":
                    System.out.println("ALPHABET: ");
                    System.out.println(fa.getAlphabet());
                    System.out.println();
                    break;

                case "3":
                    System.out.println("INITIAL STATE: ");
                    System.out.println(fa.getInitialState());
                    System.out.println();
                    break;

                case "4":
                    System.out.println("FINAL STATES: ");
                    System.out.println(fa.getFinalStates());
                    break;

                case "5":
                    System.out.println("TRANSITIONS: ");
                    System.out.println(fa.getTransitions());
                    System.out.println();
                    break;

                case "6":
                    System.out.println("Enter sequence: ");
                    option = scanner.nextLine();
                    System.out.println(fa.isSequenceAccepted(option));
                    System.out.println();
                    break;

                default:
                    System.out.println("Invalid command!");
                    break;

            }
            System.out.println();
            printFaMenu();
            System.out.println("Your option: ");
            option = scanner.nextLine();


        }
    }

    private static void scanner() {
        String filePath_p1 = "p1.txt";
        String filePath_p1err = "p1err.txt";
        String filePath_p2 = "p2.txt";
        String filePath_p3 = "p3.txt";

        CustomScanner cs = new CustomScanner();

        printScanProgramMenu();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Your option: ");

        String option = scanner.nextLine();
        while (true) {

            switch (option) {
                case "0":
                    return;
                case "1":
                    System.out.printf("Execution of %s pending...", filePath_p1);
                    cs.scanProgram(filePath_p1);
                    System.out.println("Execution finished!");
                    System.out.println();
                    break;

                case "2":
                    System.out.printf("Execution of %s pending...", filePath_p1err);
                    cs.scanProgram(filePath_p1err);
                    System.out.println("Execution finished!");
                    System.out.println();
                    break;

                case "3":
                    System.out.printf("Execution of %s pending...", filePath_p2);
                    cs.scanProgram(filePath_p2);
                    System.out.println("Execution finished!");
                    System.out.println();
                    break;

                case "4":
                    System.out.printf("Execution of %s pending...", filePath_p3);
                    cs.scanProgram(filePath_p3);
                    System.out.println("Execution finished!");
                    System.out.println();
                    break;
                default:
                    System.out.println("Invalid command!");
                    break;

            }
            System.out.println();
            printScanProgramMenu();
            System.out.println("Your option: ");
            option = scanner.nextLine();


        }
    }

    public static void main(String[] args) {
        System.out.println("1. Finite Automata");
        System.out.println("2. Scanner");
        System.out.println("Your option: ");

        Scanner scanner = new Scanner(System.in);
        String option = scanner.nextLine();

        switch (option) {
            case "1":
                finiteAutomata();
                break;
            case "2":
                scanner();
                break;
            default:
                System.out.println("Invalid command!");
                break;
        }
    }
}
