package Model.Containers;

import Exceptions.ContainerException;
import javafx.collections.ObservableList;

import java.util.List;

public interface MyIList<T> {
    public void add(T e);

    T get(int index) throws ContainerException;

    List<String> getList();
}
