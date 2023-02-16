package controller;


import model.ADT.DictionaryClass;
import model.ADT.InterfaceDictionary;
import model.exceptions.ADTException;
import model.exceptions.MyException;
import model.PrgState;
import model.exceptions.StatementException;
import model.type.Type;
import model.values.RefValue;
import model.values.Value;
import repository.InterfaceRepository;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller{
    private InterfaceRepository repository;

    private ExecutorService executorService;

    private boolean displayFlag;

    public Controller(InterfaceRepository repository) {
        this.repository = repository;
        this.displayFlag=false;
    }

    public InterfaceRepository getRepository() {
        return repository;
    }

    public void setRepository(InterfaceRepository repository) {
        this.repository = repository;
    }

    public boolean isDisplayFlag() {
        return displayFlag;
    }

    public void setDisplayFlag(boolean displayFlag) {
        this.displayFlag = displayFlag;
    }

    public void add(PrgState newProgram)
    {
        this.repository.add(newProgram);
    }

    Map<Integer,Value> safeGarbageCollector(List<Integer> symTableAddr,List<Integer> heapAddress, Map<Integer, Value> heap){
        return heap.entrySet().stream()
                .filter(e->(symTableAddr.contains(e.getKey()) || heapAddress.contains(e.getKey()))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));}

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }
    List<Integer> getAddrFromHeap(Collection<Value> heapValues){
        return heapValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    List<PrgState> removeCompletedProgram(List<PrgState> programStateList)
    {
        return programStateList.stream().filter(p -> p.isNotCompleted()).collect(Collectors.toList());
    }

    public void oneStepForAllPrograms(List<PrgState> programList) throws InterruptedException {
//        programList.forEach(prg -> {
//            try
//            {
//                repository.logProgramStateExec(prg);
//                display();
//            }
//            catch (MyException | IOException | ADTException e) {
//                System.out.println(e.getMessage());
//                System.exit(1);
//            }
//        });
        List<Callable<PrgState>> callList = programList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(p::oneStep))
                .collect(Collectors.toList());

        List<PrgState> newProgramList = null;
        try {
            newProgramList = executorService.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (ExecutionException | InterruptedException e) {
                            System.out.println(e.getMessage());
                            System.exit(1);
                            return null;
                        }
                    })
                    .filter(p -> p != null)
                    .collect(Collectors.toList());
        }catch (InterruptedException e){
            e.printStackTrace();
            System.exit(1);
        }

        programList.addAll(newProgramList);

        programList.forEach(prg -> {
            try {
                repository.logProgramStateExec(prg);
                display(prg);
            } catch (MyException | IOException | ADTException e) {
                System.out.println(e.getMessage());
            }
        });

        repository.setProgramList(programList);
    }

    public void conservativeGarbageCollector(List<PrgState> programStates)
    {
        List<Integer> symbolTableAddresses = Objects.requireNonNull(programStates.stream()
                .map(p ->getAddrFromSymTable(p.getSymbolTable().values()))
                .map(Collection::stream)
                .reduce(Stream::concat).orElse(null))
                .collect(Collectors.toList());
        programStates.forEach(p -> {
            p.getHeap().setContent((HashMap<Integer, Value>) safeGarbageCollector(symbolTableAddresses,getAddrFromHeap(p.getHeap().getContent().values()), p.getHeap().getContent()));
        });
    }

    public void allStep() throws InterruptedException, MyException, ADTException, StatementException {
        executorService = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<PrgState> programList = removeCompletedProgram(repository.getProgramList());
        while(programList.size()>0)
        {
            conservativeGarbageCollector(programList);
            oneStepForAllPrograms(programList);
            programList = removeCompletedProgram(repository.getProgramList());
        }
        executorService.shutdown();
        //HERE the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrograms calls the method
        //setProgramList of repository in order to change the repository
        repository.setProgramList(programList);
    }

    public void display(PrgState programState) throws MyException, IOException, ADTException {
        if(this.displayFlag)
            System.out.println(programState.programStateToString());
    }

}
