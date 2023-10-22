public class Operation {

    private Account source;
    private Account destination;

    public Operation(Account source, Account destination) {
        this.source = source;
        this.destination = destination;
    }

    public void transfer(final int sum) {

        source.balanceLock.lock();
        if (source.getBalance() - sum < 0) {
            source.balanceLock.unlock();
            return;
        }
        source.balanceLock.unlock();
        source.withdraw(sum);
        destination.deposit(sum);
        System.out.println("withdrew :" + sum + ", from " + source.getAccountId() + ", deposited :" + sum + ", to" + destination.getAccountId());
        OperationRecord record = new OperationRecord(source, destination, sum);
        source.addLog(record);
        destination.addLog(record);
    }
}
