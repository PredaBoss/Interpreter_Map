package Model.Exp;

import Exceptions.ExpException;
import Model.Containers.IHeap;
import Model.Containers.MyIDictionary;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.Value;
import Model.Value.RefValue;

public class RHExp implements Exp{

    Exp exp;

    public RHExp(Exp exp){
        this.exp = exp;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap<Integer, Value> hp) throws ExpException {

        if((exp.eval(tbl,hp).getType()).getClass()!=RefType.class)
            throw new ExpException("The type of the expression after evaluation needs to be a RefValue");

        RefValue refValue = (RefValue) exp.eval(tbl,hp);
        Integer address =  refValue.getAddr();

        if(!hp.isDefined(address))
            throw new ExpException("The received address was not found in the Heap!");

        return hp.lookup(address);
    }

    @Override
    public Type typecheck(MyIDictionary<String,Type> typeEnv) throws ExpException{
        Type typ=exp.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft = (RefType) typ;
            return reft.getInner();
        }
        else
            throw new ExpException("the rH argument is not a Ref Type");
    }

    @Override
    public String toString(){
        return "rH("+exp.toString()+")";
    }
}
