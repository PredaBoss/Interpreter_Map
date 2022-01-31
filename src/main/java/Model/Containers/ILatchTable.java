package Model.Containers;

import java.util.Map;

public interface ILatchTable<T1,T2> {

    public void put(T1 key, T2 value);
    public T2 get(T1 key);

    public void remove(T1 key);

    void update(T1 id, T2 val);

    public boolean isDefined(T1 id);

    public Map<T1,T2> getContent();

    int getNextFreeLocation();
}