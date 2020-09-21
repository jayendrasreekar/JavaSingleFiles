package MultithreadListOperations;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Partition implements Runnable{
    int partitionNumber;
    int partitionLength;
    int[] a;
    int k;


    Partition(int[] a,int partitionNumber,int k, int partitionLength){
        this.partitionNumber = partitionNumber;
        this.partitionLength = partitionLength;
        this.a =a;
        this.k =k;
    }


    public void run(){
        //partitionNumber;
        for(int i =(partitionNumber-1) * partitionLength ; i < a.length && i<partitionNumber*partitionLength; i++){
            if(a[i] == k){
                System.out.println("Found ...."+k+ "....at"+Thread.currentThread().getName());
            }
        }
    }
}


public class ListSearchExecutors {




    public static void main(String[] args) throws InterruptedException {
        int[] a = { 1, 5, 7, 10, 12, 14, 15,
                18, 20, 22, 25, 27, 30,
                64, 110, 220 };

        int num_of_threads = 4;
        int partitionLength = a.length/num_of_threads;

        int val = 110;
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Partition t1= new Partition(a,1,val,partitionLength);
        Partition t2= new Partition(a,2,val,partitionLength);
        Partition t3= new Partition(a,3,val,partitionLength);
        Partition t4= new Partition(a,4,val,partitionLength);



        executorService.execute(t1);
        executorService.execute(t2);
        executorService.execute(t3);
        executorService.execute(t4);



        executorService.shutdown();
        System.out.println("Exiting main");

    }
}
