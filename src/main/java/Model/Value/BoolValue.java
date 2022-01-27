package Model.Value;

import Model.Type.BoolType;
import Model.Type.StringType;
import Model.Type.Type;

public class BoolValue implements Value {

    boolean val;

    public BoolValue() {
        val = false;
    }
    public BoolValue(boolean val){
        this.val=val;
    }

    public boolean getVal(){
        return val;
    }

    @Override
    public String toString() {
        if (val)
            return "True";
        return "False";
    }

    @Override
    public boolean equals(Object another){
        if (another instanceof BoolValue)
            return true;
        else
            return false;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }
}
