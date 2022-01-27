package Model.Exp;

import Exceptions.ExpException;
import Model.Containers.IHeap;
import Model.Containers.MyIDictionary;
import Model.Type.Type;
import Model.Value.Value;

public class VarExp implements Exp {
    String id;

    public VarExp(String id) {
        this.id = id;
    }

    public Value eval(MyIDictionary<String, Value> tbl, IHeap<Integer, Value> hp)  throws ExpException
    {
        if(!tbl.isDefined(id))
            throw new ExpException("Symbol "+id+" is not defined\n");
        return tbl.lookup(id);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws ExpException{
        return typeEnv.lookup(id);
    }

    @Override
    public String toString() {
        return  id;
    }
}
