package View.Gui;

import Model.Value.Value;

public class HeapPair {
    private final String address;
    private final String valueHeap;

    public HeapPair(Integer first, Value second) {
        this.address = Integer.toString(first);
        this.valueHeap =second.toString();
    }

    public String getAddress() {
        return address;
    }

    public String getValueHeap() {
        return valueHeap;
    }
}