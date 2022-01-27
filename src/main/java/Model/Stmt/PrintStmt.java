package Model.Stmt;

import Exceptions.ExpException;
import Exceptions.StmtException;
import Model.Containers.IHeap;
import Model.Containers.MyIDictionary;
import Model.Containers.MyIList;
import Model.Exp.Exp;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.Value;

public class PrintStmt implements IStmt {
    Exp exp;

    public PrintStmt(Exp exp){
        this.exp=exp;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpException {
        MyIList<Value> out = state.getOut();
        IHeap<Integer, Value> heap = state.getHeap();
        MyIDictionary<String,Value> symTbl = state.getSymTable();
        out.add(exp.eval(symTbl,heap));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ExpException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString(){ return "print(" +exp.toString()+");";}
}
