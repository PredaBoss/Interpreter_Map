package Model.Stmt;

import Exceptions.ExpException;
import Exceptions.StmtException;
import Model.*;
import Model.Containers.IHeap;
import Model.Exp.Exp;
import Model.Containers.MyIDictionary;
import Model.Containers.MyIStack;
import Model.Type.Type;
import Model.Value.Value;

public class AssignStmt implements IStmt {
    String id;
    Exp exp;

    public AssignStmt(String id,Exp exp){
        this.id=id;
        this.exp=exp;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpException, StmtException {
            IHeap<Integer, Value> heap = state.getHeap();
            MyIDictionary<String, Value> symTbl= state.getSymTable();
            if (symTbl.isDefined(id))
            {
                Value val = exp.eval(symTbl,heap);
                Type typId = (symTbl.lookup(id)).getType();
                if ((val.getType()).equals(typId))
                    symTbl.update(id, val);
                else
                    throw new ExpException("declared type of variable" + id + " and type of  the assigned expression do not match");
            }
            else
                throw new ExpException("the used variable" + id + " was not declared before");

            return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws StmtException, ExpException {
        Type typevar = typeEnv.lookup(id);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new StmtException("Assignment: right hand side and left hand side have different types ");
    }

    @Override
    public String toString(){ return id+"="+ exp.toString()+";";}
}
