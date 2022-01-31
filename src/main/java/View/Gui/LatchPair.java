package View.Gui;

import Model.Value.Value;

public class LatchPair {
    private final String location;
    private final String valueLatch;

    public LatchPair(Integer first, Integer second) {
        this.location = Integer.toString(first);
        this.valueLatch =Integer.toString(second);
    }

    public String getLocation() {
        return location;
    }

    public String getValueLatch() {
        return valueLatch;
    }
}