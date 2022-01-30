package Model.Containers;

import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public interface ISemaphore {
    public void put(Integer key, Integer value);

    public Pair<Integer, List<Integer> > lookup(Integer key);

    public void add(Integer key, Integer prg_id);

    public void remove(Integer key,Integer prg_id);

    public boolean isDefined(Integer key);

    public int getNextFreeLocation();

    public void setContent(Map<Integer,Pair<Integer,List<Integer> > > new_sem);

    public Map<Integer,Pair<Integer,List<Integer> > > getContent();
}
