package Model.Stmt;

import Exceptions.ContainerException;
import Exceptions.ExpException;
import Exceptions.StmtException;
import Model.Containers.MyIDictionary;
import Model.PrgState;
import Model.Stmt.IStmt;
import Model.Type.Type;

public class NopStmt implements IStmt {
    @Override
    public PrgState execute(PrgState state) {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ExpException {
        return typeEnv;
    }
}
