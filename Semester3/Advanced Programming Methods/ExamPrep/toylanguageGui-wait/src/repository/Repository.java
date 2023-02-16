package repository;

import model.exceptions.ADTException;
import model.exceptions.MyException;
import model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements InterfaceRepository{

    private List<PrgState> states;
    private final String logFilePath ;

    public Repository(PrgState state, String logFilePath) throws IOException {
        this.logFilePath = logFilePath;
        this.states = new ArrayList<>();
        this.add(state);
        this.emptyLogFile();
    }
    @Override
    public void add(PrgState state) {
        this.states.add(state);
    }
    @Override
    public List<PrgState> getProgramList() {
        return states;
    }

    @Override
    public void setProgramList(List<PrgState> states) {
        this.states=states;
    }

    @Override
    public void logProgramStateExec(PrgState programState) throws MyException, IOException, ADTException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.println(programState.programStateToString());
        logFile.close();
    }

    private void emptyLogFile() throws IOException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)));
        logFile.close();
    }
}
