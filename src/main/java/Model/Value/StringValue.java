package Model.Value;

import Model.Type.StringType;
import Model.Type.Type;

public class StringValue implements Value{

    private String val;

    public StringValue(){
        this.val = "";
    }

    public StringValue(String val){
        this.val = val;
    }

    public String getVal(){
        return this.val;
    }

    @Override
    public boolean equals(Object another){
        if (!(another instanceof  StringValue))
            return false;
        return ((StringValue)another).val.equals(this.val);
    }

    @Override
    public int hashCode() {
        return val.hashCode();
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public String toString(){
        return this.val;
    }
}
