package SynchronizeThreads;

class MyCounter{
    private volatile boolean flag;

    public synchronized void printEven(int number){

        while(!flag){
            try{
                wait();
            }catch(InterruptedException e){
               Thread.currentThread().interrupt();
            }
        }
        System.out.println(Thread.currentThread().getName() + ":" + number);
        flag = false;
        notify();
    }


    public synchronized void printOdd(int number){
        while(flag){
            try{
                wait();
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(Thread.currentThread().getName() + ":" + number);
        flag = true;
        notify();
    }
}


class EvenRunnable implements Runnable{
    MyCounter counter;
    boolean b;
    int number;
    EvenRunnable(MyCounter counter, boolean b){
        this.counter = counter;
        this.b = b;
    }


    @Override
    public synchronized void run() {
        if(b){
            number =2;
        }
        while(number<20) {
            counter.printEven(number);
            number += 2;
        }
    }
}

class OddRunnable implements Runnable {
    MyCounter counter;
    boolean   c;
    int number;
    OddRunnable(MyCounter counter, boolean c) {
        this.counter = counter;
        this.c = c;
    }


    @Override
    public synchronized void run() {
        if(c){
            number = 1;
        }

        while (number < 20) {
            counter.printOdd(number);
            number = number +2 ;
        }
    }
}

// Synchronize two threads of different thread implementation. Wait() & notify
public class SynchronizeDifferentThreads {
    public static void main(String[] args) throws InterruptedException {
        MyCounter counter = new MyCounter();

        Thread t1 = new Thread(new EvenRunnable(counter,true),"Even Thread");
        Thread t2 = new Thread(new OddRunnable(counter,true),"Odd Thread");

        t1.start();
        t2.start();

    }

}
