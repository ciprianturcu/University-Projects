import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Account {

    private static final AtomicInteger count = new AtomicInteger(0);
    private Integer accountId;
    private List<OperationRecord> logs;
    private Integer balance;
    public ReentrantLock balanceLock;
    public ReentrantLock logLock;
    private final Integer initialBalance;

    public Account(Integer initialBalance) {
        this.initialBalance = initialBalance;
        this.accountId = count.incrementAndGet();
        this.logs = new ArrayList<>();
        this.balance = initialBalance;
        this.balanceLock = new ReentrantLock();
        this.logLock = new ReentrantLock();
    }

    public void addLog(OperationRecord record) {
        this.logLock.lock();
        this.logs.add(record);
        this.logLock.unlock();
    }

    public int getAccountId() {
        return accountId;
    }

    public List<OperationRecord> getLogs() {
        return logs;
    }

    public int getBalance() {
        return balance;
    }

    public void deposit(final int sum) {
        this.balanceLock.lock();
        this.balance += sum;
        this.balanceLock.unlock();
    }

    public void withdraw(final int sum) {
        this.balanceLock.lock();
        this.balance -= sum;
        this.balanceLock.unlock();
    }
}
