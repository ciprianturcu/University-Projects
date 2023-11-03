import java.util.Random;
import java.util.TimerTask;

public class OperationTask extends TimerTask {
    @Override
    public void run() {
        Random random = new Random();
        int from = random.nextInt(Bank.NUM_ACCOUNTS);
        int to = random.nextInt(Bank.NUM_ACCOUNTS);
        int amount = random.nextInt(100);
        if (from != to) {
            Operation op = new Operation(Bank.accounts.get(from), Bank.accounts.get(to));
            op.transfer(amount);
        }
    }
}
