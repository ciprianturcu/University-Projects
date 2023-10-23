import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

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
        for(int i=0;i<100;i++) {
            hashTable.insertNode(i);
        }
        hashTable.insertNode(100);
        assert hashTable.getPosition(100) == 100;
    }

    @Test
    public void testInsertNormalBehaviourWithCloseKeys() {
        HashTable<String> hashTable = new HashTable<>(100);
        hashTable.insertNode("ab");
        hashTable.insertNode("ba");

        assert hashTable.getPosition("ab") != hashTable.getPosition("ba");
    }

}
