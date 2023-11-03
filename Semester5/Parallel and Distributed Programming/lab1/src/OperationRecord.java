import java.util.concurrent.atomic.AtomicInteger;

public class OperationRecord {

    private static final AtomicInteger count = new AtomicInteger(0);
    private final int jobID;
    private final Account source;
    private final Account destination;
    private final int sum;


    public OperationRecord(Account source, Account destination, int sum) {
        this.jobID = count.incrementAndGet();
        this.source = source;
        this.destination = destination;
        this.sum = sum;
        //System.out.println("operation with jobId: " + this.jobID + ", from account: " + this.source.getAccountId() + ", to account: " + this.destination.getAccountId());
    }

    public int getJobID() {
        return jobID;
    }

    public Account getSource() {
        return source;
    }

    public Account getDestination() {
        return destination;
    }

    public int getSum() {
        return sum;
    }
}
