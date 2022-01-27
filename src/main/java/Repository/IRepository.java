package Repository;

import Exceptions.ContainerException;
import Model.PrgState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    List<PrgState> getPrgList();
    void logPrgStateExec(PrgState prgState) throws IOException;
}
