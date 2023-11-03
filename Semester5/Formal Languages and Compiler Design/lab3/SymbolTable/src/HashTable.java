import java.util.ArrayList;

public class HashTable<K> {

    private ArrayList<HashNode<K>> table;
    private int size;
    private int capacity;

    public HashTable(int initialSize) {
        this.table = new ArrayList<>(initialSize);
        for (int i = 0; i < initialSize; i++) {
            table.add(null);
        }
        this.size = 0;
        this.capacity = initialSize;
    }

    public int hashFunction(K key) {
        return key.hashCode() % this.capacity;
    }

    private void rehashTable() {
        ArrayList<HashNode<K>> oldTable = table;
        table = new ArrayList<>(capacity*2);
        for(int i=0;i<capacity*2;i++)
        {
            table.add(null);
        }
        this.size=0;
        this.capacity*=2;
        for(HashNode<K> hashNode : oldTable) {
            if(hashNode!=null)
                insertNode(hashNode.key);
        }
    }

    public int insertNode(K key) {
        if (size+1 > capacity / 2)
            rehashTable();
        HashNode<K> temp = new HashNode<>(key);
        int hashIndex = hashFunction(key);
        while (this.table.get(hashIndex) != null && this.table.get(hashIndex).key != key) {
            hashIndex++;
            hashIndex %= capacity;
        }
        if (this.table.get(hashIndex) == null)
            this.size++;
        this.table.set(hashIndex, temp);
        return hashIndex;
    }

    public int getPosition(K key) {
        int hashIndex = hashFunction(key);
        while (this.table.get(hashIndex).key != key) {
            hashIndex++;
            hashIndex %= this.capacity;
        }
        return hashIndex;
    }

    public K getByPosition(int position) {
        return this.table.get(position).key;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSize() {
        return size;
    }
}
