package Model.Type;

import Model.Value.StringValue;
import Model.Value.Value;

public class StringType implements Type{

    @Override
    public boolean equals(Object another){
        if (another instanceof  StringType)
            return true;
        else
            return false;
    }

    @Override
    public Value default_value() {
        return new StringValue();
    }

    public String toString(){
        return "string";
    }
}
