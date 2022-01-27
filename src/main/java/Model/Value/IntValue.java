package Model.Value;

import Model.Type.IntType;
import Model.Type.Type;

public class IntValue implements Value {

    int val;

    public IntValue(){
        val=0;
    }
    public IntValue(int v) {
        val = v;
    }

    public int getVal() {
        return val;
    }

    @Override
    public boolean equals(Object another){
        if (another instanceof IntValue)
            return true;
        else
            return false;
    }

    @Override
    public String toString() {
        return Integer.toString(val);
    }

    @Override
    public Type getType() {
        return new IntType();
    }
}