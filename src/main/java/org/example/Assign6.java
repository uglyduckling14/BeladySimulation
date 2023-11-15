package org.example;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Assign6 {
    static int numOfThreads = Runtime.getRuntime().availableProcessors();
    static ExecutorService pool = Executors.newFixedThreadPool(numOfThreads);
    static int[] pageFaultsFIFO = new int[1000];
    static int[] pageFaultsLRU = new int[1000];
    static int[] pageFaultsMRU = new int[1000];

    static int minFIFO = 0;
    static int minMRU = 0;
    static int minLRU = 0;

    public static void main(String[] args) {
        long initialTime = System.currentTimeMillis();
        for (int i=0; i< 1000; i++) {
            int[] pageRef = generatePageRef();
            createTasks(pageRef);
            addMin(findMin(i));
        }

        System.out.println("Simulation took "+ (System.currentTimeMillis()-initialTime) + " ms");

        System.out.println("FIFO min PF: " + minFIFO);
        System.out.println("LRU min PF: " + minLRU);
        System.out.println("MRU min PF: " + minMRU);

        pool.shutdown();
    }

    private static int[] generatePageRef(){
        int[] pageRef = new int[1000];
        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            pageRef[i] = rand.nextInt(250)+1;
        }
        return pageRef;
    }

    private static void createTasks(int[] sequence){
        for (int i = 1; i < 101; i++) {
            Runnable fifo = new TaskFIFO(sequence,i,250, pageFaultsFIFO);
            Runnable lru = new TaskLRU(sequence,i,250, pageFaultsLRU);
            Runnable mru = new TaskMRU(sequence,i,250, pageFaultsMRU);

            pool.submit(fifo);
            pool.submit(lru);
            pool.submit(mru);
        }
    }

    private static void addMin(int num){
        switch (num) {
            case 3 -> {
                minFIFO++;
                minLRU++;
                minMRU++;
            }
            case 2 -> {
                minLRU++;
                minFIFO++;
            }
            case -2 -> {
                minMRU++;
                minFIFO++;
            }
            case -3 -> {
                minMRU++;
                minLRU++;
            }
            case 1 -> minFIFO++;
            case 0 -> minLRU++;
            case -1 -> minMRU++;
            default -> System.out.println("ERROR OCCURRED!!!! Case statements are messed up");
        }
    }

    private static int findMin(int index){
        int fifo = pageFaultsFIFO[index];
        int lru = pageFaultsLRU[index];
        int mru = pageFaultsMRU[index];

        if(fifo == lru && lru == mru){
            //All are same
            return 3;
        }

        if(fifo == lru){
            //FIFO and LRU are same
            return 2;
        }else
        if(fifo == mru){
            //FIFO and MRU are same
            return -2;
        }else
        if(mru == lru){
            //MRU and LRU are same
            return -3;
        }else
        if(fifo < lru && fifo < mru){
            //FIFO is smallest
            return 1;
        }else
        if(lru < fifo && lru < mru){
            //LRU is smallest
            return 0;
        }else
        if(mru < fifo && mru < lru){
            //MRU is smallest
            return -1;
        }
        return 10;
    }
}
