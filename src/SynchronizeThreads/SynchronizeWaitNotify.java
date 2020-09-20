package SynchronizeThreads;

class NewCounter{
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


class OddEvenRunnable implements Runnable {
    NewCounter counter;
    boolean   iseven;
    int number;

    OddEvenRunnable(NewCounter counter, boolean c) {
        this.counter = counter;
        this.iseven = c;
    }


    @Override
    public synchronized void run() {

        if(iseven == true){
            number = 2;
        }else{
            number = 1;
        }

        while (number < 20) {

          if(iseven){
             counter.printEven(number);
          }else {
              counter.printOdd(number);
          }
          number = number +2 ;
        }
    }
}

// Synchronize two threads of different thread implementation. Wait() & notify
public class SynchronizeWaitNotify {
    public static void main(String[] args) throws InterruptedException {
        NewCounter counter = new NewCounter();

        Thread t1 = new Thread(new OddEvenRunnable(counter,true),"Even Thread");
        Thread t2 = new Thread(new OddEvenRunnable(counter,false),"Odd Thread");

        t1.start();
        t2.start();

    }

}
