import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {

    public static final int NUM_ACCOUNTS = 80;
    public static final int INITIAL_BALANCE = 1000;
    public static final int NUM_OPERATIONS = 50;
    public static List<Account> accounts = new ArrayList<>();

    public static void main(String[] args) {
        createAccounts();
        scheduleOperations();
        scheduleCheckerTask();
    }

    private static void createAccounts() {
        for (int i = 0; i < NUM_ACCOUNTS; i++) {
            accounts.add(new Account(INITIAL_BALANCE));
        }
    }

    private static void scheduleCheckerTask() {
        var timer = new Timer();
        timer.schedule(new CheckerTask(), 10, 1000);
    }

    private static void scheduleOperations() {
        for(int i=0;i<NUM_OPERATIONS;i++)
        {
            var timer = new Timer();
            timer.schedule(new OperationTask(), 0, 500);
        }
    }
}
