import mpi.MPI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Main {
    static final int polynomialDegree = 80;
    static final String algorithmType = "CLASSIC";

    public static void main(String[] args) {
        MPI.Init(args);

        int idOfCurrentProcess = MPI.COMM_WORLD.Rank();     // one's own ID
        int size = MPI.COMM_WORLD.Size();                   // the number of launched instances

        if (idOfCurrentProcess == 0){
            System.out.println("Parent process: \n");
            Polynomial p1 = new Polynomial(polynomialDegree);
            Polynomial p2 = new Polynomial(polynomialDegree);

            System.out.println("Polynomial 1:" + p1);
            System.out.println("Polynomial 2:" + p2);
            System.out.println("\n");

            parentTask(p1, p2, size);
        } else {
            if (Objects.equals(algorithmType, "CLASSIC")) {
                childClassicTask(idOfCurrentProcess);
            } else {
                childKaratsubaTask(idOfCurrentProcess);
            }
        }

        MPI.Finalize();
    }

    public static void parentTask(Polynomial p1, Polynomial p2, int numberOfProcesses) {
        Polynomial resultOfParentComputation;

        // split tasks to children
        int taskLength = (p1.getDegree() + 1) / numberOfProcesses;
        int startCoefficientOfParent = 0;

        int endCoefficientOfParent = taskLength - 1 + (p1.getDegree() + 1) % numberOfProcesses;

        int endCoefficient = endCoefficientOfParent;
        for(int i = 1; i < numberOfProcesses; i++) {              // send to children
            int startCoefficient = endCoefficient + 1;
            endCoefficient = endCoefficient + taskLength;

            // offset = from where we start (what we send)
            // count = how much we send
            // MPI.OBJECT, MPI.INT = parameter type
            // i = id of the process to which we send the information
            MPI.COMM_WORLD.Send(new Object[]{p1}, 0, 1, MPI.OBJECT, i, 0);
            MPI.COMM_WORLD.Send(new Object[]{p2}, 0, 1, MPI.OBJECT, i, 0);
            MPI.COMM_WORLD.Send(new int[]{startCoefficient}, 0, 1, MPI.INT, i, 0);
            MPI.COMM_WORLD.Send(new int[]{endCoefficient}, 0, 1, MPI.INT, i, 0);
        }

        if(Objects.equals(algorithmType, "CLASSIC")) {
            System.out.println("Parent from " + startCoefficientOfParent + " to " + endCoefficientOfParent);

            resultOfParentComputation = Polynomial.computeCoefficientsOfResult(p1, p2, startCoefficientOfParent, endCoefficientOfParent);
        }
        else {
            for (int j = endCoefficientOfParent; j < p1.getCoefficients().size(); j++) {
                p1.getCoefficients().set(j, 0);
            }

            System.out.println("Parent from " + startCoefficientOfParent + " to " + endCoefficientOfParent);

            resultOfParentComputation = Polynomial.KaratsubaSequential(p1, p2);
        }

        System.out.println("Parent done.");

        Object[] results = new Object[numberOfProcesses-1];
        for (int i = 1; i  < numberOfProcesses; i++){
            // source = id of the process from which we receive the information
            MPI.COMM_WORLD.Recv(results, i-1, 1, MPI.OBJECT, i, 0);
        }

        ArrayList<Object> resultsAsArray = new ArrayList<>(Arrays.asList(results));
        resultsAsArray.add(resultOfParentComputation);

        Polynomial finalResult = Polynomial.computeSumOfPolynomials(resultsAsArray);

        System.out.println("Final result: " + finalResult);
    }

    public static void childClassicTask(int id) {
        Object[] p1Object = new Object[2];
        Object[] p2Object= new Object[2];
        int[] startCoefficient = new int[1];
        int[] endCoefficient = new int[1];

        MPI.COMM_WORLD.Recv(p1Object, 0, 1, MPI.OBJECT, 0, 0);
        MPI.COMM_WORLD.Recv(p2Object, 0, 1, MPI.OBJECT, 0, 0);
        MPI.COMM_WORLD.Recv(startCoefficient, 0, 1, MPI.INT, 0, 0);
        MPI.COMM_WORLD.Recv(endCoefficient, 0, 1, MPI.INT, 0, 0);

        Polynomial p1 = (Polynomial) p1Object[0];
        Polynomial p2 = (Polynomial) p2Object[0];

        System.out.println("Child with id " + id + " from " + startCoefficient[0] + " to " + endCoefficient[0]);

        Polynomial result = Polynomial.computeCoefficientsOfResult(p1, p2, startCoefficient[0], endCoefficient[0]);
        MPI.COMM_WORLD.Send(new Object[]{result}, 0, 1, MPI.OBJECT, 0, 0);

        System.out.println("Child with id " + id + " done.");
    }

    public static void childKaratsubaTask(int id) {
        Object[] p1Object = new Object[2];
        Object[] p2Object= new Object[2];
        int[] startCoefficient = new int[1];
        int[] endCoefficient = new int[1];

        MPI.COMM_WORLD.Recv(p1Object, 0, 1, MPI.OBJECT, 0, 0);
        MPI.COMM_WORLD.Recv(p2Object, 0, 1, MPI.OBJECT, 0, 0);
        MPI.COMM_WORLD.Recv(startCoefficient, 0, 1, MPI.INT, 0, 0);
        MPI.COMM_WORLD.Recv(endCoefficient, 0, 1, MPI.INT, 0, 0);

        Polynomial p1 = (Polynomial) p1Object[0];
        Polynomial p2 = (Polynomial) p2Object[0];

        System.out.println("Child with id " + id + " from " + startCoefficient[0] + " to " + endCoefficient[0]);

        for (int i = 0; i < startCoefficient[0]; i++) {
            p1.getCoefficients().set(i, 0);
        }
        for (int j = endCoefficient[0] + 1; j < p1.getCoefficients().size(); j++) {
            p1.getCoefficients().set(j, 0);
        }

        Polynomial result = Polynomial.KaratsubaSequential(p1, p2);
        MPI.COMM_WORLD.Send(new Object[]{result}, 0, 1, MPI.OBJECT, 0, 0);

        System.out.println("Child with id " + id + " done " + result);
    }
}