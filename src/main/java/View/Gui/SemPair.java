package View.Gui;

import Model.Value.Value;
import javafx.util.Pair;

import java.util.List;

public class SemPair {
    private final String index;
    private final String valueSem;
    private final String listSem;

    public SemPair(Integer index, Pair<Integer,List<Integer> > value) {
        this.index = Integer.toString(index);
        this.valueSem = Integer.toString(value.getKey());
        this.listSem = value.getValue().toString();
    }

    public String getIndex() {
        return index;
    }

    public String getValueSem() {
        return valueSem;
    }

    public String getListSem(){
        return listSem;
    }
}
