package Model.Stmt;

import Exceptions.ContainerException;
import Exceptions.ExpException;
import Exceptions.StmtException;
import Model.Containers.MyIDictionary;
import Model.PrgState;
import Model.Type.Type;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IStmt {

    PrgState execute(PrgState state) throws ExpException, StmtException, IOException;
    MyIDictionary<String,Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ExpException;
}
