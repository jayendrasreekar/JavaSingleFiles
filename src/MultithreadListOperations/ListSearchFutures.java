package MultithreadListOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


class FutureImpl implements Callable<List<Integer>>{
    int pos;
    int partitionLength;
    int[] a;
    int key;

    FutureImpl(int pos,int partitionLength,int a[],int key){
        this.pos = pos;
        this.partitionLength = partitionLength;
        this.a = a;
        this.key = key;
    }

    @Override
    public List<Integer> call() throws Exception {
        List<Integer> ls = new ArrayList<>();
        for(int i=pos*partitionLength; i<(pos+1)*partitionLength;i++){
            if(a[i] == key){
                ls.add(i);
            }
        }
        return ls;
    }
}

public class ListSearchFutures {

     public static void main(String[] args) {
         int[] a = {7, 5, 7, 10, 12, 14, 15,
                 18, 20, 22, 25, 27, 30,
                 64, 7, 220};

         int num_of_threads = 4;
         final int partitionLength = a.length / num_of_threads;
         Future<List<Integer>>[]  futures = new Future[num_of_threads];

         Scanner sc =  new Scanner(System.in);

         int key = sc.nextInt();
         ExecutorService executorService = Executors.newFixedThreadPool(5);

         for (int i = 0; i < num_of_threads; i++) {
             FutureImpl fml = new FutureImpl(i,partitionLength,a,key);
             futures[i] = executorService.submit(fml);
         }

         for(int i=0;i < num_of_threads;i++ ){
             try {
                 List<Integer> k = futures[i].get();
                 for(Integer in:k){
                     System.out.println("Found at index  -->" + in);
                 }
             }
             catch (InterruptedException e) {
                 e.printStackTrace();
             }
             catch (ExecutionException e) {
                 e.printStackTrace();
             }
         }
     }
}
