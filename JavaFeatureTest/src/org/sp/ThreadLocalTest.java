package org.sp;

import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * author sp
 * 20190921
 */
public class ThreadLocalTest {
    private static ThreadLocal<String> TlNames= new ThreadLocal<String>();
    public static void main(String[] args){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 100,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
        for (int i=0;i<10;i++) {
            //增加注释23
            //增加注释1
            executor.submit(() -> {
                TlNames.set("" + Thread.currentThread().getName());
                System.out.println("GetName:" + TlNames.get());
                TlNames.set(null);
                if(TlNames.get()==null){
                    System.out.println("GetName after set null:" + TlNames.get());
                }
            });
        }
        executor.shutdown();
    }


}
