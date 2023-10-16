public class Operation {

    private Account source;
    private Account destination;

    public Operation(Account source, Account destination) {
        this.source = source;
        this.destination = destination;
    }

    public Account getSource() {
        return source;
    }

    public Account getDestination() {
        return destination;
    }

    public void transfer(final Long sum) {
        source.subtractBalance(sum);
        destination.addBalance(sum);
        OperationRecord record = new OperationRecord(source, destination, sum);
        source.addLog(record);
        destination.addLog(record);
    }

}
