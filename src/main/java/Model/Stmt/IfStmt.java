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

public class IfStmt implements IStmt {
    Exp exp;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(Exp e, IStmt t, IStmt el) {
        exp=e;
        thenS=t;
        elseS=el;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpException, StmtException {
        MyIStack<IStmt> stk = state.getStk();
        IHeap<Integer, Value> heap = state.getHeap();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        if (!exp.eval(symTbl,heap).getType().equals(new BoolType()))
            throw new ExpException("conditional expr is not a boolean!\n");
        else if (((BoolValue) exp.eval(symTbl,heap)).getVal()) {
            stk.push(thenS);
        } else {
            stk.push(elseS);
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ExpException {
        Type typexp=exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenS.typecheck(typeEnv.deepcopy());
            elseS.typecheck(typeEnv.deepcopy());
            return typeEnv;
        }
        else
            throw new StmtException("The condition of IF has not the type bool");
    }

    @Override
    public String toString(){
        return "(IF("+ exp.toString()+") THEN(" +thenS.toString()  +")ELSE("+elseS.toString()+"))";
    }
}
