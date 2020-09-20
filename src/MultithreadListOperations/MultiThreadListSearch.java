package MultithreadListOperations;


class PartitionThread implements Runnable{
    static int counter = 0;
    int partitionNumber;
    int partitionLength;
    int[] a;
    int k;


    PartitionThread(int[] a,int partitionNumber,int k, int partitionLength){
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


public class MultiThreadListSearch {




    public static void main(String[] args) throws InterruptedException {
        int[] a = { 1, 5, 7, 10, 12, 14, 15,
                18, 20, 22, 25, 27, 30,
                64, 110, 220 };

         int num_of_threads = 4;
         int partitionLength = a.length/num_of_threads;
        //ExecutorService executorService = Executors.newFixedThreadPool(5);

        Thread t1= new Thread(new PartitionThread(a,1,27,partitionLength),"Thread-for-partition1");
        Thread t2= new Thread(new PartitionThread(a,2,27,partitionLength),"Thread-for-partition2");
        Thread t3= new Thread(new PartitionThread(a,3,27,partitionLength),"Thread-for-partition3");
        Thread t4= new Thread(new PartitionThread(a,4,27,partitionLength),"Thread-for-partition4");



        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        System.out.println("Exiting main");

    }
}
