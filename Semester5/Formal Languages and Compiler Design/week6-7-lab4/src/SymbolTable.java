public class SymbolTable<K> {
    //1b - next
    private HashTable<K> hashTable;

    public SymbolTable() {
        this.hashTable = new HashTable<>(100);
    }

    public HashTable<K> getTable() {
        return hashTable;
    }
    public int add(K symbol) {
        return this.hashTable.insertNode(symbol);
    }
    public int getPosition(K symbol) {
        return this.hashTable.getPosition(symbol);
    }
    public K getByPosition(int position){
        return this.hashTable.getByPosition(position);
    }
}
