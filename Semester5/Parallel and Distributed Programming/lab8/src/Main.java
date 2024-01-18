
import mpi.MPI;
import Message.CloseMessage;
import Message.BaseMessage;
import Message.SubscribeMessage;
import Message.UpdateMessage;

public class Main {

    private static class Listener implements Runnable {

        private final DSM dsm;

        public Listener(DSM dsm) {
            this.dsm = dsm;
        }

        @Override
        public void run() {
            while (true) {
                System.out.println("Rank " + MPI.COMM_WORLD.Rank() + " waiting..");
                Object[] messages = new Object[1];

                MPI.COMM_WORLD.Recv(messages, 0, 1, MPI.OBJECT, MPI.ANY_SOURCE, MPI.ANY_TAG);
                BaseMessage message = (BaseMessage) messages[0];

                if (message instanceof CloseMessage){
                    System.out.println("Rank " + MPI.COMM_WORLD.Rank() + " stopped listening...");
                    return;
                }
                else if (message instanceof SubscribeMessage) {
                    SubscribeMessage subscribeMessage = (SubscribeMessage) message;
                    System.out.println("Subscribe message received");
                    System.out.println("Rank " + MPI.COMM_WORLD.Rank() + " received: rank " + subscribeMessage.rank + " subscribes to " + subscribeMessage.variable);
                    dsm.syncSubscriptions(subscribeMessage.variable, subscribeMessage.rank);
                }
                else if (message instanceof UpdateMessage) {
                    UpdateMessage updateMessage = (UpdateMessage) message;
                    System.out.println("Update message received");
                    System.out.println("Rank " + MPI.COMM_WORLD.Rank() + " received:" + updateMessage.variable + "->" + updateMessage.value);
                    dsm.setVariable(updateMessage.variable, updateMessage.value);
                }

                writeAll(dsm);
            }
        }
    }

    public static void writeAll(DSM dsm) {
        StringBuilder sb = new StringBuilder();
        sb.append("Write all\n");
        sb.append("Rank ").append(MPI.COMM_WORLD.Rank()).append("->a = ").append(dsm.var1).append(" b = ").append(dsm.var2).append(" c = ").append(dsm.var3).append("\n");
        sb.append("Subscribers: \n");
        for (String var : dsm.subscribersOfVariables.keySet()) {
            sb.append(var).append("->").append(dsm.subscribersOfVariables.get(var).toString()).append("\n");
        }
        System.out.println(sb.toString());
    }

    public static void main(String[] args) throws InterruptedException {
        // write your code here
        MPI.Init(args);
        DSM dsm = new DSM();
        int me = MPI.COMM_WORLD.Rank();
        if (me == 0) {
            Thread thread = new Thread(new Listener(dsm));

            thread.start();

            dsm.subscribeToVariable("var1");
            dsm.subscribeToVariable("var2");
            dsm.subscribeToVariable("var3");
            dsm.updateVariable("var1",0,111);
            dsm.updateVariable("var3",2,333);
            dsm.updateVariable("var2",100, 101);
            dsm.close();

            thread.join();

        } else if (me == 1) {
            Thread thread = new Thread(new Listener(dsm));

            thread.start();

            dsm.subscribeToVariable("var1");
            dsm.subscribeToVariable("var3");


            thread.join();
        } else if (me == 2) {
            Thread thread = new Thread(new Listener(dsm));

            thread.start();

            dsm.subscribeToVariable("var2");
            dsm.updateVariable("var2", 1, 100);

            thread.join();
        }
        MPI.Finalize();
    }
}