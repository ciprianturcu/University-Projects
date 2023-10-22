import javax.lang.model.type.ArrayType;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class SymbolTable {

    private ArrayList<HashNode> table;
    private int capacity;
    private int size;
    private HashNode dummyNode;

    public SymbolTable() {
        this.capacity = 100;
        this.table = new ArrayList<>(this.capacity);
        this.size = 0;
        this.dummyNode = new HashNode(-1,-1);
    }

    public int hashFunction(String key) {
        int asciiSum = key.chars().reduce(0, Integer::sum);
        return asciiSum % this.capacity;
    }

    public void insertNode(String value) {
        
    }

}

