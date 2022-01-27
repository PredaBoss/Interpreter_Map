package View.Gui;

import Model.Value.Value;

public class SymPair {
    String variable;
    String valueSym;

    public SymPair(String variable, Value valueSym) {
        this.variable = variable;
        this.valueSym = valueSym.toString();
    }

    public String getVariable() {
        return variable;
    }

    public String getValueSym() {
        return valueSym;
    }
}