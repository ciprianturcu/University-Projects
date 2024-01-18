import Message.BaseMessage;
import Message.CloseMessage;
import Message.SubscribeMessage;
import Message.UpdateMessage;
import mpi.MPI;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DSM {
    public Map<String, Set<Integer>> subscribersOfVariables;
    public static final Lock lock = new ReentrantLock();
    public int var1, var2, var3;


    public DSM() {
        this.var1 = 0;
        this.var2 = 1;
        this.var3 = 2;
        this.subscribersOfVariables = new ConcurrentHashMap<>();
        this.subscribersOfVariables.put("var1", new HashSet<>());
        this.subscribersOfVariables.put("var2", new HashSet<>());
        this.subscribersOfVariables.put("var3", new HashSet<>());
    }

    public void setVariable(String variable, int value) {
        if (variable.equals("var1"))
            this.var1 = value;
        if (variable.equals("var2"))
            this.var2 = value;
        if (variable.equals("var3"))
            this.var3 = value;
    }

    public void handleUpdateVariable(String variable, int value) {
        lock.lock();
        this.setVariable(variable, value);
        BaseMessage message = new UpdateMessage(variable, value);
        this.sendMessageToSubscribersOfVariable(variable, message);
        lock.unlock();
    }

    public void updateVariable(String variable, int oldValue, int newValue) {
        if (variable.equals("var1") && this.var1 == oldValue)
            handleUpdateVariable("var1", newValue);
        if (variable.equals("var2") && this.var2 == oldValue)
            handleUpdateVariable("var2", newValue);
        if (variable.equals("var3") && this.var3 == oldValue)
            handleUpdateVariable("var3", newValue);
    }

    public void sendMessageToSubscribersOfVariable(String variable, BaseMessage message) {
        for (int i = 0; i < MPI.COMM_WORLD.Size(); i++) {
            if (MPI.COMM_WORLD.Rank() == i || !subscribersOfVariables.get(variable).contains(i)) {
                continue;
            }
            MPI.COMM_WORLD.Send(new Object[]{message}, 0, 1, MPI.OBJECT, i, 0);
        }
    }

    private void sendMessageToAllSubscribers(BaseMessage message) {
        for (int i = 0; i < MPI.COMM_WORLD.Size(); i++) {
            if (MPI.COMM_WORLD.Rank() == i && !(message instanceof CloseMessage)) {
                continue;
            }
            //System.out.println("sent message" + message + "to " + i);
            MPI.COMM_WORLD.Send(new Object[]{message}, 0, 1, MPI.OBJECT, i, 0);
        }
    }

    public void subscribeToVariable(String variable) {
        Set<Integer> subscribersOfVariable = this.subscribersOfVariables.get(variable);
        subscribersOfVariable.add(MPI.COMM_WORLD.Rank());
        this.subscribersOfVariables.put(variable, subscribersOfVariable);
        sendMessageToAllSubscribers(new SubscribeMessage(variable, MPI.COMM_WORLD.Rank()));
    }

    public void syncSubscriptions(String variable, int rank) {
        Set<Integer> subscribersOfVariable = this.subscribersOfVariables.get(variable);
        subscribersOfVariable.add(rank);
        this.subscribersOfVariables.put(variable, subscribersOfVariable);
    }

    public void close() {
        this.sendMessageToAllSubscribers(new CloseMessage());
    }

}
