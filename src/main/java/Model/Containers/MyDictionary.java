package Model.Containers;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<T1,T2> implements MyIDictionary<T1,T2> {
    Map<T1,T2> dictionary;

    public MyDictionary(){
        dictionary = new HashMap<T1,T2>();
    }

    @Override
    public void put(T1 key, T2 value) {
        dictionary.put(key,value);
    }

    @Override
    public T2 lookup(T1 key) {
        return dictionary.get(key);
    }

    @Override
    public void update(T1 id, T2 val) {
        dictionary.replace(id,val);
    }

    @Override
    public boolean isDefined(T1 id) {
        return dictionary.containsKey(id);
    }

    @Override
    public Map<T1, T2> getContent() {
        return dictionary;
    }

    @Override
    public MyIDictionary<T1, T2> deepcopy() {
        MyIDictionary<T1,T2> deepCopy = new MyDictionary<>();
        for(T1 key:dictionary.keySet())
            deepCopy.put(key,dictionary.get(key));
        return deepCopy;
    }

    @Override
    public String toString() {
        StringBuilder builder =  new StringBuilder();
        for(Map.Entry<T1,T2> entry : dictionary.entrySet()) {
            builder.append(entry.getKey()).append(" --> ").append(entry.getValue()).append("\n");
        }
        return builder.toString();
    }
}
