package Model.Stmt;

import Exceptions.ContainerException;
import Exceptions.ExpException;
import Exceptions.StmtException;
import Model.Containers.MyIDictionary;
import Model.Containers.MyIStack;
import Model.PrgState;
import Model.Type.Type;

public class CompStmt implements IStmt {
    IStmt first;
    IStmt snd;

    public CompStmt(IStmt first,IStmt snd){
        this.first=first;
        this.snd=snd;
    }

    @Override
    public PrgState execute(PrgState state) {
        MyIStack<IStmt> stk=state.getStk();
        stk.push(snd);
        stk.push(first);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ExpException {
        return snd.typecheck(first.typecheck(typeEnv));
    }

    @Override
    public String toString() {
        return first.toString() + "\n" + snd.toString();
    }
}
