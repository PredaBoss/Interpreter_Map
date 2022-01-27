package Model.Stmt;

import Exceptions.ExpException;
import Exceptions.StmtException;
import Model.Containers.IHeap;
import Model.Containers.MyIDictionary;
import Model.Containers.MyIStack;
import Model.Containers.MyStack;
import Model.Exp.Exp;
import Model.PrgState;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.Value;
import Model.Value.BoolValue;

import java.io.IOException;

public class WhileStmt implements IStmt{

    private Exp exp;
    private IStmt stmt;

    public WhileStmt(Exp exp, IStmt stmt){
        this.exp = exp;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpException, StmtException, IOException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        IHeap<Integer, Value> heap = state.getHeap();
        MyIStack<IStmt> stk = state.getStk();

        if(!(exp.eval(symTable,heap).getType().equals(new BoolType())))
            throw new ExpException("The value returned by evaluation function needs to be a BoolValue!");

        if(((BoolValue)exp.eval(symTable,heap)).getVal())
        {
            stk.push(this);
            stk.push(stmt);
        }

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ExpException {
        Type typexp = exp.typecheck(typeEnv);
        if(!(typexp instanceof BoolType))
            throw new StmtException("While: a boolean type is required");
        stmt.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString(){
        return "(\nwhile("+exp.toString()+")\n" + stmt.toString() + "\n)";
    }
}
