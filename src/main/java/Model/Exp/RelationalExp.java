package Model.Exp;

import Exceptions.ExpException;
import Model.Containers.IHeap;
import Model.Containers.MyIDictionary;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.Value;
import Model.Value.IntValue;
import Model.Value.BoolValue;

public class RelationalExp implements Exp{

    private Exp exp1;
    private String operator;
    private Exp exp2;

    public RelationalExp(Exp exp1,String operator,Exp exp2){
        this.exp1 = exp1;
        this.operator =operator;
        this.exp2 = exp2;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap<Integer, Value> hp) throws ExpException {
        Value v1,v2;
        v1 = exp1.eval(tbl, hp);
        v2 = exp2.eval(tbl, hp);

        if(!(v1.getType().equals(new IntType()) && v2.getType().equals(new IntType())))
            throw new ExpException("Both operands need to be of int type!");

        IntValue i1 = (IntValue) v1;
        IntValue i2 = (IntValue) v2;

        switch (operator){
            case "<":
                return new BoolValue(i1.getVal() < i2.getVal());

            case "<=":
                return new BoolValue(i1.getVal() <= i2.getVal());

            case "==":
                return new BoolValue(i1.getVal() == i2.getVal());

            case "!=":
                return new BoolValue(i1.getVal() != i2.getVal());

            case ">":
                return new BoolValue(i1.getVal() > i2.getVal());

            case ">=":
                return new BoolValue(i1.getVal() >= i2.getVal());

            default:
                throw new ExpException("The relational operator is invalid!");
        }
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws ExpException {

        Type typ1, typ2;
        typ1=exp1.typecheck(typeEnv);
        typ2=exp2.typecheck(typeEnv);

        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new BoolType();
            }
            else
                throw new ExpException("second operand is not an int");
        }
        else
            throw new ExpException("first operand is not an int");

    }

    @Override
    public String toString(){
        return exp1 + " " + operator + " " + exp2;
    }
}
