package org.sp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * author sp
 * 20190921
 */
public class ParallelStreamTest {
    private static ThreadLocal<String> TlNames= new ThreadLocal<String>();
    private static ThreadPoolExecutor executors = new ThreadPoolExecutor(40, 40,
            60L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());
    public static void main(String[] args){
     new Thread(()->{
         int k =0;
         while(k<100){
             executors.execute(()->{
                 TlNames.set(Thread.currentThread().getName());
                 System.out.println("--------work Thread name is :"+TlNames.get());
             });
             try {
                 byte[] zeroB = new byte[0];
                 synchronized (zeroB) {
                     zeroB.wait(100);
                 }
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
             k++;
         }
     }).start();
        try {
            byte[] zeroB = new byte[0];
            synchronized (zeroB) {
                zeroB.wait(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executors.submit(()->{
            List<String> list = new ArrayList<String>();
            for(int i =0;i<20;i++){
                list.add(String.valueOf(i));
            }
            list.parallelStream().forEach(name->{

             System.out.println(name+"：：：："+Thread.currentThread().getName());

                try {
                    byte[] zeroB = new byte[0];
                    synchronized (zeroB) {
                        zeroB.wait(500);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            System.out.println(Thread.currentThread().getName()+" work finished !");
        });
        System.out.println("main do it !");
        //executors.shutdown();

    }
}
