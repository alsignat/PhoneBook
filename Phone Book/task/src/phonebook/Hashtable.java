package phonebook;

import java.util.LinkedList;
import java.util.List;

public class Hashtable {

    private LinkedList<String>[] hashtable;
    private int capacity;

    public Hashtable(int capacity) {
        this.hashtable = new LinkedList[capacity];
        this.capacity = capacity;
    }

    public void add(String item) {
        int index = Math.abs(item.hashCode()) % capacity;
        if (this.hashtable[index] == null) {
            this.hashtable[index] = new LinkedList<>();
        }
        this.hashtable[index].add(item);
    }

    public boolean contains(String item) {
        int index = Math.abs(item.hashCode()) % capacity;
        List<String> bucket = this.hashtable[index];
        if (bucket == null) return false;
        else return bucket.contains(item);
    }

}
