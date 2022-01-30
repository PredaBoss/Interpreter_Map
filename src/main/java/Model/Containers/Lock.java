package Model.Containers;

import java.util.HashMap;
import java.util.Map;

public class Lock<T1,T2> implements ILock<T1,T2>{

    Map<T1,T2> dictionary;
    private Integer nextFreeLocation;

    public Lock(){
        dictionary = new HashMap<T1,T2>();
        nextFreeLocation = 1;
    }

    @Override
    public void put(T1 key, T2 value) {
        dictionary.put(key,value);
        nextFreeLocation++;
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

    public int getNextFreeLocation(){
        return this.nextFreeLocation;
    }

    @Override
    public void setContent(Map<T1, T2> new_lock) {
        this.dictionary = new_lock;
    }

    @Override
    public Map<T1, T2> getContent() {
        return dictionary;
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
