package Model.Containers;

import java.util.Set;

public interface IFileTable<T1,T2> {

    public void put(T1 key, T2 value);
    public T2 lookup(T1 key);

    public boolean isDefined(T1 id);

    public void remove(T1 id);

    public Set<T1> keySet();
}
