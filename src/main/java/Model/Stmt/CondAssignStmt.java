package Model.Stmt;

import Exceptions.ExpException;
import Exceptions.StmtException;
import Model.Containers.IHeap;
import Model.Containers.MyIDictionary;
import Model.Containers.MyIStack;
import Model.Exp.Exp;
import Model.PrgState;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

import java.io.IOException;

public class CondAssignStmt implements IStmt{

    String v;
    Exp exp1;
    Exp exp2;
    Exp exp3;

    public CondAssignStmt(String v, Exp exp1, Exp exp2, Exp exp3) {
        this.v = v;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpException, StmtException, IOException {
        MyIStack<IStmt> stk = state.getStk();
        IHeap<Integer, Value> heap = state.getHeap();
        MyIDictionary<String, Value> symTbl = state.getSymTable();

        if (((BoolValue) exp1.eval(symTbl,heap)).getVal()) {
            stk.push(new AssignStmt(v,exp2));
        } else {
            stk.push(new AssignStmt(v,exp3));
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ExpException {

        Type typexp1=exp1.typecheck(typeEnv);
        if (!typexp1.equals(new BoolType()))
            throw new StmtException("Conditional Assignment: exp1 needs to have boolean type!");

        Type typevar = typeEnv.lookup(v);
        Type typexp2 = exp2.typecheck(typeEnv);
        Type typexp3 = exp3.typecheck(typeEnv);
        if (typevar.equals(typexp2) && typevar.equals(typexp3))
            return typeEnv;
        else
            throw new StmtException("Conditional Assignment: v, exp2 and exp3 need to have the same type");
    }

    @Override
    public String toString(){
        return v + "=(" + exp1.toString() + ")?" + exp2.toString()  + ":" + exp3.toString()+";";
    }
}
