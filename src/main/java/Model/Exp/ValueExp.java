package Model.Exp;

import Exceptions.ExpException;
import Model.Containers.IHeap;
import Model.Containers.MyIDictionary;
import Model.Type.Type;
import Model.Value.Value;

public class ValueExp implements Exp {

    Value val;

    public ValueExp(Value val) {
        this.val = val;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap<Integer, Value> hp)  throws ExpException {
        return val;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws ExpException{
        return val.getType();
    }

    @Override
    public String toString() {
        return val.toString();
    }
}
