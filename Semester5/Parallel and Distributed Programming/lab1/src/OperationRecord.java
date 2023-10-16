import java.util.concurrent.atomic.AtomicInteger;

public class OperationRecord {

    private static final AtomicInteger count = new AtomicInteger(0);
    private final int jobID;
    private final Account source;
    private final Account destination;
    private final Long sum;


    public OperationRecord(Account source, Account destination, Long sum) {
        this.jobID = count.incrementAndGet();
        this.source = source;
        this.destination = destination;
        this.sum = sum;
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

    public Long getSum() {
        return sum;
    }
}
