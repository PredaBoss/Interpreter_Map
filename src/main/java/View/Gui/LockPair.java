package View.Gui;

public class LockPair {
    private final String locationLock;
    private final String valueLock;

    public LockPair(Integer first, Integer second) {
        this.locationLock = Integer.toString(first);
        this.valueLock =Integer.toString(second);
    }

    public String getLocationLock() {
        return locationLock;
    }

    public String getValueLock() {
        return valueLock;
    }

}
