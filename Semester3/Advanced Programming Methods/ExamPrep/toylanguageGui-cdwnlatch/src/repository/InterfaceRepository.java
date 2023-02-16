package repository;

import model.exceptions.ADTException;
import model.exceptions.MyException;
import model.PrgState;

import java.io.IOException;
import java.util.List;

public interface InterfaceRepository {
    public void add(PrgState state);
    List<PrgState> getProgramList();
    void setProgramList(List<PrgState> states);
    public void logProgramStateExec(PrgState programState) throws MyException, IOException, ADTException;

}
