package model.ADT;
import java.util.LinkedList;
import java.util.List;

public class ListClass<T> implements InterfaceList<T> {

    private List<T> list;

    @Override
    public void add(T v) {
        synchronized (this)
        {
            list.add(v);
        }
    }

    @Override
    public List<T> getList() {
        synchronized (this) {
            return list;
        }
    }
    public ListClass() {
        this.list = new LinkedList<>();
    }

    public String toString() {
        return list.toString();
    }
}
