public class SymbolTable<K> {
    private HashTable<K> hashTable;

    public SymbolTable(HashTable<K> hashTable) {
        this.hashTable = hashTable;
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
