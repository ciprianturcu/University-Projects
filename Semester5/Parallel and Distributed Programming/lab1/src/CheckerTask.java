import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class CheckerTask extends TimerTask {

    private AtomicInteger count = new AtomicInteger(0);
    @Override
    public void run() {

        var checker = new Checker(Bank.accounts, Bank.INITIAL_BALANCE);
        int run = count.getAndIncrement();
        System.out.println("Starting check on run " + run + " ...");
        var result = checker.runChecker();

        if (result)
            System.out.println("Successfully check on run "+run+"!");
        else
            System.out.println("Faulty check on run "+run+"!");

    }
}