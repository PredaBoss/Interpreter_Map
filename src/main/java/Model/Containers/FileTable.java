package Model.Containers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FileTable<T1,T2> implements IFileTable<T1,T2>{

    Map<T1,T2> dictionary;

    public FileTable(){
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
    public boolean isDefined(T1 id) {
        return dictionary.containsKey(id);
    }

    @Override
    public void remove(T1 id) {
        this.dictionary.remove(id);
    }

    public Set<T1> keySet(){
        return dictionary.keySet();
    }

    @Override
    public String toString() {
        Set<T1> keys = this.keySet();
        StringBuilder stringAns= new StringBuilder();
        for(T1 key: keys){
            stringAns.append(key.toString()).append("\n");
        }
        return stringAns.toString();
    }
}
