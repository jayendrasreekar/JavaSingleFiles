import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class Pair<U,V>{
    public U first;
    public V second;

    public Pair(U first, V second){
        this.first = first;
        this.second = second;
    }
}


class Bucket {
    private List<Pair<Integer,Integer>> bucket;

    public Bucket(){
        this.bucket = new LinkedList<>();
    }

    public Integer get(Integer key){
        for(Pair<Integer,Integer> pair: this.bucket){
            if(pair.first.equals(key)){
                return pair.second;
            }
        }
        return -1;
    }

    public void update(Integer key,Integer value){
        boolean found = false;
        for(Pair<Integer,Integer> pair : this.bucket){
            if(pair.first.equals(key)){
                pair.second = value;
                found = true;
            }
        }if(!found){
            this.bucket.add(new Pair<>(key, value));
        }

    }

    public void remove(Integer key){
        for(Pair<Integer,Integer> pair: this.bucket){
            if(pair.first.equals(key)){
                this.bucket.remove(pair);
                break;
            }
        }
    }

}






class MyConcurrentHashMap {
    private int key_space;
    private List<Bucket> hash_table;
    public final Segment[] segments;

    protected static final class Segment{
        protected int count;
        protected synchronized int getCount() {
            return this.count;
        }
        protected synchronized void synch() {}
    }


    public MyConcurrentHashMap(){
        this.key_space = 2069;
        this.hash_table = new ArrayList<>();
        for(int i=0;i<this.key_space;i++){
            this.hash_table.add(new Bucket());
        }

        segments = new Segment[32];
        for(int i=0; i<segments.length; i++){
            segments[i] = new Segment();
        }
    }



    public void put(int key, int value){
        int hash_key = key%(this.key_space);
        System.out.println(hash_key);
        Segment seg = segments[hash_key%32];
        synchronized (seg) {
            this.hash_table.get(hash_key).update(key, value);
        }
    }

    public int get(int key){
        int hash_key = key%(this.key_space);
        return this.hash_table.get(hash_key).get(key);
    }

    public void remove(int key){
        int hash_key = key%(this.key_space);
        Segment seg = segments[hash_key%32];
        synchronized (seg) {
            this.hash_table.get(hash_key).remove(key);
        }
    }



}


public class BuildConcurrentHashMap{

    public static void main(String[] args) {
        MyConcurrentHashMap hm = new MyConcurrentHashMap();
        hm.put(2,3);
        hm.put(4,5);
        hm.put(2071,4098);
        System.out.println(hm.get(2));
        System.out.println(hm.get(4));
        System.out.println(hm.get(2071));

    }
}
