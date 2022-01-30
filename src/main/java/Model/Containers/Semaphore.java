package Model.Containers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;

public class Semaphore implements ISemaphore{

    Map<Integer,Pair<Integer, List<Integer> > > dictionary;
    private Integer nextFreeLocation;

    public Semaphore(){
        dictionary = new HashMap<>();
        nextFreeLocation = 1;
    }

    @Override
    public void put(Integer key, Integer value) {
        dictionary.put(key,new Pair<>(value,new ArrayList<>()));
        nextFreeLocation++;
    }

    @Override
    public Pair<Integer, List<Integer>> lookup(Integer key) {
        return dictionary.get(key);
    }

    @Override
    public void add(Integer key, Integer prg_id) {
        if(!inList(key,prg_id))
            dictionary.get(key).getValue().add(prg_id);
    }

    @Override
    public void remove(Integer key, Integer prg_id) {
        if(inList(key,prg_id))
            dictionary.get(key).getValue().remove(prg_id);
    }

    private boolean inList(Integer key,Integer prg_id){
        return dictionary.get(key).getValue().contains(prg_id);
    }

    @Override
    public boolean isDefined(Integer key) {
        return dictionary.containsKey(key);
    }

    @Override
    public int getNextFreeLocation() {
        return nextFreeLocation;
    }

    @Override
    public void setContent(Map<Integer, Pair<Integer, List<Integer>>> new_sem) {
        this.dictionary = new_sem;
    }

    @Override
    public Map<Integer, Pair<Integer, List<Integer>>> getContent() {
        return dictionary;
    }

    @Override
    public String toString() {
        StringBuilder builder =  new StringBuilder();
        for(Map.Entry<Integer,Pair<Integer,List<Integer> > > entry : dictionary.entrySet()) {
            builder.append(entry.getKey()).append(" --> (").append(entry.getValue().getKey()).append(") : ").append(entry.getValue().getValue().toString()).append("\n");
        }
        return builder.toString();
    }
}
