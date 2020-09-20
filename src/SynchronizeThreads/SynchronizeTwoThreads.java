package SynchronizeThreads;

class Counter{
    int count =0;

    public synchronized int increment(){
        return count++;
    }


}


class MyRunnable implements Runnable{
    Counter counter;
    MyRunnable(Counter counter){
        this.counter = counter;
    }


    @Override
    public void run() {
        while(counter.count<20) {
            System.out.println("Thread ==> " + Thread.currentThread().getName() + "  and SynchronizeThreads.Counter is " + counter.increment());
        }
    }
}


// Synchronize two threads of same Thread implementation
public class SynchronizeTwoThreads {
     public static void main(String[] args){
           Counter counter = new Counter();
           Thread t1 = new Thread(new MyRunnable(counter));
           Thread t2 = new Thread(new MyRunnable(counter));

           t1.start();
           t2.start();
     }

}
