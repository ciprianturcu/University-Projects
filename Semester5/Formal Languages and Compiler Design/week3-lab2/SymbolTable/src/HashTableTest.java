import org.junit.Test;

public class HashTableTest {

    @Test
    public void testInsertNormalBehaviour() {
        HashTable<String> hashTable = new HashTable<>(100);
        hashTable.insertNode("a");
        hashTable.insertNode("b");
        hashTable.insertNode("c");
        hashTable.insertNode("d");

        assert(hashTable.getPosition("a") == 97);
        assert(hashTable.getPosition("b") == 98);
        assert(hashTable.getPosition("c") == 99);
        assert(hashTable.getPosition("d") == 0);
    }

    @Test
    public void testInsertExceedingCapacity() {
        HashTable<Integer> hashTable = new HashTable<>(100);
        for(int i=0;i<50;i++) {
            hashTable.insertNode(i);
        }
        assert hashTable.getCapacity() == 100;
        hashTable.insertNode(51);
        assert hashTable.getCapacity() == 200;
        assert hashTable.getPosition(51) == 51;
    }

    @Test
    public void testInsertNormalBehaviourWithCloseKeysAndInsertSameElement() {
        HashTable<String> hashTable = new HashTable<>(100);
        hashTable.insertNode("ab");
        hashTable.insertNode("ba");
        hashTable.insertNode("ab");

        assert hashTable.getPosition("ab") != hashTable.getPosition("ba");
        assert hashTable.getSize() == 2;
    }



}
