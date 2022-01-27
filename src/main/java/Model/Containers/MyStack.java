package Model.Containers;

import Model.Stmt.IStmt;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    Stack<T> stack;

    public MyStack() {
        stack=new Stack<T>();
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public List<T> getContent() {
        List<T> elems = new ArrayList<>();
        int hm = 0;
        while(!stack.empty()){
            hm++;
            elems.add(stack.pop());
        }
        for(int i=hm-1;i>=0;i--)
            stack.push(elems.get(i));
        return elems;
    }

    @Override
    public String toString() {
        Stack<T> tempStack = new Stack<T>();
        StringBuilder builder = new StringBuilder();
        while (!stack.isEmpty()){
            T element = stack.pop();
            tempStack.push(element);
            builder.append(element).append("\n");
        }
        while (!tempStack.isEmpty()){
            T element = tempStack.pop();
            stack.push(element);
        }
        return builder.toString();
    }
}
