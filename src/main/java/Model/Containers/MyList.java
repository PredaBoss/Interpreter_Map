package Model.Containers;

import Exceptions.ContainerException;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class MyList<T> implements MyIList<T> {
    List<T> lst;

    public MyList(){
        lst = new Vector<T>();
    }

    @Override
    public void add(T e) {
        lst.add(e);
    }

    @Override
    public T get(int index) throws ContainerException {
        if(lst.size()<=index)
            throw new ContainerException("The list does not have the index "+Integer.toString(index));
        return lst.get(index);
    }

    @Override
    public List<String> getList() {
        return lst.stream().map(T::toString).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder builder =  new StringBuilder();
        for(T element:lst){
            builder.append(element).append("\n");
        }
        return builder.toString();
    }
}
