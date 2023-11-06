import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ScalarProduct {
    //general objects
    private ArrayList<Integer> vector1;
    private ArrayList<Integer> vector2;
    private long sum = 0L;
    private final Lock lock = new ReentrantLock();
    private final Condition productAvailable = lock.newCondition();

    //method 1
    private long product;
    private boolean productReady = false;
    private boolean consumeReady = false;

    //method 2
    private ArrayList<Long> products;


    private void computeScalarProductMethod1() {
        Thread producer = new Thread(() -> {
            for (int i = 0; i < vector1.size(); i++) {
                lock.lock();
                try {
                    while(productReady)
                        productAvailable.await();
                    product = (long) vector1.get(i) * vector2.get(i);
                    System.out.println("Produced product of " + vector1.get(i) + " and " + vector2.get(i) + " with product of " + vector1.get(i) * vector2.get(i));
                    productReady = true;
                    productAvailable.signal();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
//            lock.lock();
//            try {
//                allProductsProduced = true; // Signal one last time to unblock the consumer if it's still waiting
//                productAvailable.signal();
//            } finally {
//                lock.unlock();
//            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < vector1.size(); i++) {
                lock.lock();
                try {
                    while (!productReady) {
                        productAvailable.await();
                    }
                    System.out.println("Consumed product :" + product);
                    sum += (int) product;
                    productReady = false;
                    productAvailable.signal();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        });

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (runCheck(sum)) {
            System.out.println("Check ok. The scalar product of the two vectors is: " + sum);
        }
    }

    private void computeScalarProductMethod2() {
        Thread producer = new Thread(() -> {
            for (int i = 0; i < vector1.size(); i++) {
                lock.lock();
                try {
                    products.add(((long) vector1.get(i) * vector2.get(i)));
                    System.out.println("Produced product of " + vector1.get(i) + " and " + vector2.get(i) + " with product of " + vector1.get(i) * vector2.get(i));
                    productAvailable.signal();
                } finally {
                    lock.unlock();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < vector1.size(); i++) {
                lock.lock();
                try {
                    while (products.isEmpty()) {
                        productAvailable.await();
                    }
                    System.out.println("Consumed product :" + products.get(0));
                    sum += products.remove(0);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }

        });

        producer.start();
        consumer.start();
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (runCheck(sum)) {
            System.out.println("Check ok. The scalar product of the two vectors is: " + sum);
        }

    }

    private boolean runCheck(Long sum) {
        long localSum = 0L;
        for (int i = 0; i < vector1.size(); i++) {
            localSum += (long) vector1.get(i) * vector2.get(i);
        }
        if (localSum != sum)
            throw new RuntimeException("sum is incorrect, expected was " + localSum + " instead got " + sum);
        return true;
    }

    public void execute() {
        initVectors();
        //computeScalarProductMethod1();
        computeScalarProductMethod2();
    }

    private void initVectors() {
        this.vector1 = new ArrayList<>();
        this.vector2 = new ArrayList<>();
        this.products = new ArrayList<>();
        final int nrOfElements = 30;
        for (int i = 0; i < nrOfElements; i++) {
            vector1.add(i);
            vector2.add(nrOfElements - i);
        }
    }
}
