package Model.Containers;

import java.util.Map;

public interface MyIDictionary<T1,T2> {

    public void put(T1 key, T2 value);
    public T2 lookup(T1 key);

    public void update(T1 id, T2 val);

    public boolean isDefined(T1 id);

    public Map<T1,T2> getContent();

    public MyIDictionary<T1,T2> deepcopy();

}
