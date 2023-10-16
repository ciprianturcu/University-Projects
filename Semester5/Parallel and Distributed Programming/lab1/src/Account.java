import java.util.ArrayList;
import java.util.List;

public class Account {
    private List<OperationRecord> logs;

    private Long balance;

    public Account(List<OperationRecord> logs, Long balance) {
        this.logs = new ArrayList<>(logs);
        this.balance = balance;
    }

    public void addLog(OperationRecord record)
    {
        this.logs.add(record);
    }

    public List<OperationRecord> getLogs() {
        return logs;
    }

    public Long getBalance() {
        return balance;
    }

    public void setLogs(List<OperationRecord> logs) {
        this.logs = logs;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public void addBalance(final Long sum)
    {
        this.balance+=sum;
    }

    public void subtractBalance(final Long sum) {
        this.balance-=sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (!logs.equals(account.logs)) return false;
        return balance.equals(account.balance);
    }

    @Override
    public int hashCode() {
        int result = logs.hashCode();
        result = 31 * result + balance.hashCode();
        return result;
    }


}
