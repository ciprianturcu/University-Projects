import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class Checker {
    private final List<Account> accounts;

    private final Integer initialBalance;


    public Checker(List<Account> accounts, Integer initialBalance) {
        this.accounts = accounts;
        this.initialBalance = initialBalance;
    }

    public boolean runChecker() {
        AtomicBoolean isValid = new AtomicBoolean(true);
        try {
            lockAccounts();
            if (!checkAccounts())
                isValid.set(false);
        } finally {
            unlockAccounts();
        }
        return isValid.get();
    }

    private boolean checkAccounts() {
        AtomicBoolean isValid = new AtomicBoolean(true);

        for (Account account : accounts) {
            if (!checkAccount(account))
                isValid.set(false);
        }
        if (!checkAccountsOperationLogs())
            isValid.set(false);

        return isValid.get();
    }

    private boolean checkAccount(Account account) {
        Integer logBalance = account.getLogs().stream()
                .map(operationRecord -> operationRecord.getDestination().getAccountId() == account.getAccountId() ? operationRecord.getSum() : operationRecord.getSum() * (-1))
                .reduce(initialBalance, Integer::sum);
        return logBalance == account.getBalance();

    }

    private boolean checkAccountsOperationLogs() {
        Set<OperationRecord> allRecords = this.accounts.stream().flatMap(account -> account.getLogs().stream()).collect(Collectors.toSet());
        for (OperationRecord operationRecord : allRecords) {
            Account source = operationRecord.getSource();
            Account destination = operationRecord.getDestination();
            if (!source.getLogs().contains(operationRecord) || !destination.getLogs().contains(operationRecord)) {
                return false;
            }
        }
        return true;
    }

    private void lockAccounts() {
        this.accounts.forEach(account -> {
            account.balanceLock.lock();
            account.logLock.lock();
        });
    }

    private void unlockAccounts() {
        this.accounts.forEach(account -> {
            account.balanceLock.unlock();
            account.logLock.unlock();
        });
    }
}
