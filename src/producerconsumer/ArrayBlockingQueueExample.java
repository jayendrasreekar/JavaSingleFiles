package producerconsumer;


import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


class MyProducer implements Runnable{
    private ArrayBlockingQueue<String> buffer;

    public MyProducer(ArrayBlockingQueue<String> buffer){
        this.buffer = buffer;
    }
    public void run(){
        Random random = new Random();
        String[] nums = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14"};
        for(String num: nums){
            try{
                System.out.println("Adding" + num);
                buffer.put(num);
                Thread.sleep(random.nextInt(1000));
            }catch (InterruptedException e){
                System.out.println("Producer Interrupted");
            }
        }

        try{
            buffer.put("EOF");
        }catch (InterruptedException e){
            System.out.println(e);
        }

    }
}

class MyConsumer implements Runnable{
    ArrayBlockingQueue<String> buffer;
    public MyConsumer(ArrayBlockingQueue<String> buffer){
        this.buffer = buffer;
    }

    public void run(){
           while(true){
               try {
                   System.out.println("Polled from Consumer from thread:"+ Thread.currentThread().getName()+" and value  "  + buffer.take());
               }
               catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
    }
}

public class ArrayBlockingQueueExample {
    public static final String EOF = "EOF";
    public static void main(String[] args){
        ArrayBlockingQueue<String> buffer = new ArrayBlockingQueue<>(10);


        ExecutorService executorService = Executors.newFixedThreadPool(5);

        MyProducer producer = new MyProducer(buffer);
        MyConsumer consumer1 = new MyConsumer(buffer);
        MyConsumer consumer2 = new MyConsumer(buffer);
        //MyConsumer consumer3 = new MyConsumer(buffer);
        executorService.execute(producer);
        executorService.execute(consumer1);
        executorService.execute(consumer2);
       //executorService.execute(consumer3);


        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                while(true){
                    System.out.println("Polled from future callable Consumer from thread:"+ Thread.currentThread().getName()+" and value  "  + buffer.take());
                }
            }
        });

        try {
            future.get();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }

}
