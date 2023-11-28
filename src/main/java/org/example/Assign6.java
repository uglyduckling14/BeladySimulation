package org.example;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Assign6 {
    final static int MAX_PAGE_REFERENCE =250;
    static int numOfThreads = Runtime.getRuntime().availableProcessors();
    static ExecutorService pool = Executors.newFixedThreadPool(numOfThreads);
    static int[] pageFaultsFIFO = new int[101];
    static int[] pageFaultsLRU = new int[101];
    static int[] pageFaultsMRU = new int[101];

    static int minFIFO = 0;
    static int minMRU = 0;
    static int minLRU = 0;
    static AnomalyReport fifoReport = new AnomalyReport();
    static AnomalyReport mruReport = new AnomalyReport();
    static AnomalyReport lruReport = new AnomalyReport();
    public static void main(String[] args) {
//        testLRU();
//        testMRU();
        long initialTime = System.currentTimeMillis();
        for (int i=1; i< 10; i++) {
            int[] pageRef = generatePageRef();
            createTasks(pageRef, i);
            System.out.println(Arrays.toString(pageFaultsMRU));
//            System.out.println(Arrays.toString(pageFaultsLRU));
//            System.out.println(Arrays.toString(pageFaultsFIFO));
            pageFaultsFIFO = new int[101];
            pageFaultsLRU = new int[101];
            pageFaultsMRU = new int[101];
        }

        System.out.println("Simulation took "+ (System.currentTimeMillis()-initialTime) + " ms");

        System.out.println("FIFO min PF: " + minFIFO);
        System.out.println("LRU min PF: " + minLRU);
        System.out.println("MRU min PF: " + minMRU);

        System.out.println();
        System.out.println("Belady's Anomaly Report for FIFO");
        //fifoReport.report();

        System.out.println();
        System.out.println("Belady's Anomaly Report for LRU");
        lruReport.report();

        System.out.println();
        System.out.println("Belady's Anomaly Report for MRU");
        mruReport.report();

        pool.shutdown();
    }

    private static int[] generatePageRef(){
        int[] pageRef = {11, 2, 5, 1, 11, 2, 10, 3, 5, 0, 13, 2, 10, 5, 9, 11, 7, 7, 2, 14, 5, 7, 12, 10, 9};
//        int[] pageRef = new int[1000];
//        Random rand = new Random();
//        for (int i = 0; i < 10; i++) {
//            pageRef[i] = rand.nextInt(250)+1;
//        }
        return pageRef;
    }
    public static void testMRU() {
        int[] sequence1 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] sequence2 = {1, 2, 1, 3, 2, 1, 2, 3, 4};
        int[] pageFaults = new int[4];  // 4 because maxMemoryFrames is 3

        // Replacement should be: 1, 2, 3, 4, 5, 6, 7, 8
        // Page Faults should be 9
        Runnable taskMRU = (new TaskMRU(sequence1, 1, MAX_PAGE_REFERENCE, pageFaults));
        pool.submit(taskMRU);
        Future<?> futureMRU = pool.submit(taskMRU);

        try {
            // Wait for the task to complete
            futureMRU.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(); // Handle exceptions
        }
        System.out.printf("Page Faults: %s\n", Arrays.toString(pageFaults));

//        // Replacement should be: 1, 2, 1, 3
//        // Page Faults should be 6
        taskMRU = (new TaskMRU(sequence2, 2, MAX_PAGE_REFERENCE, pageFaults));
        pool.submit(taskMRU);
        futureMRU = pool.submit(taskMRU);

        try {
            // Wait for the task to complete
            futureMRU.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(); // Handle exceptions
        }
        System.out.printf("Page Faults: %d\n", pageFaults[2]);
//
//        // Replacement should be: 3
//        // Page Faults should be 4
        taskMRU = (new TaskMRU(sequence2, 3, MAX_PAGE_REFERENCE, pageFaults));
        pool.submit(taskMRU);
        futureMRU = pool.submit(taskMRU);

        try {
            // Wait for the task to complete
            futureMRU.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(); // Handle exceptions
        }
        System.out.printf("Page Faults: %s\n", Arrays.toString(pageFaults));
    }
    public static void testLRU() {
        int[] sequence1 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] sequence2 = {1, 2, 1, 3, 2, 1, 2, 3, 4};
        int[] pageFaults = new int[4];  // 4 because maxMemoryFrames is 3

//        // Replacement should be: 1, 2, 3, 4, 5, 6, 7, 8
//        // Page Faults should be 9
//        Runnable taskLRU = new TaskLRU(sequence1, 1, MAX_PAGE_REFERENCE, pageFaults);
//        pool.submit(taskLRU);
//        System.out.printf("Page Faults: %d\n", pageFaults[1]);
//
        // Replacement should be: 2, 1, 3, 1, 2
        // Page Faults should be 7
        Runnable taskLRU = (new TaskLRU(sequence2, 2, MAX_PAGE_REFERENCE, pageFaults));
        pool.submit(taskLRU);
        Future<?> futureLRU = pool.submit(taskLRU);

        try {
            // Wait for the task to complete
            futureLRU.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(); // Handle exceptions
        }
        System.out.printf("Page Faults: %d\n", pageFaults[2]);
//
//        // Replacement should be: 1
//        // Page Faults should be 4
//        (new TaskLRU(sequence2, 3, MAX_PAGE_REFERENCE, pageFaults)).run();
//        System.out.printf("Page Faults: %d\n", pageFaults[3]);
    }
    private static void createTasks(int[] sequence, int sim){
        for (int i = 1; i < 101; i++) {
            Runnable fifo = new TaskFIFO(sequence,i,250, pageFaultsFIFO);
            Runnable lru = new TaskLRU(sequence,i,250, pageFaultsLRU);
            Runnable mru = new TaskMRU(sequence,i,250, pageFaultsMRU);

            Future<?> futureLRU = pool.submit(lru);
            Future<?> futureFIFO = pool.submit(fifo);
            Future<?> futureMRU = pool.submit(mru);

            try {
                // Wait for the task to complete
                futureLRU.get();
                futureFIFO.get();
                futureMRU.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace(); // Handle exceptions
            }
            addMin(findMin(i));
        }
        fifoReport.detection(sim, pageFaultsFIFO);
        lruReport.detection(sim, pageFaultsLRU);
        mruReport.detection(sim, pageFaultsMRU);
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

        if(fifo == lru && mru>fifo){
            //FIFO and LRU are same
            return 2;
        }else
        if(fifo == mru && lru>fifo){
            //FIFO and MRU are same
            return -2;
        }else
        if(mru == lru && fifo>mru){
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
class AnomalyReport{
    private int total;
    private int delta;
    private String reportString;

    public AnomalyReport(){
        total = 0;
        delta = 0;
        reportString="";
    }
    public void report(){
        System.out.println(reportString);
        System.out.println("Anomaly detected " + total + " times in 10000 simulations with a max delta of " + delta);
    }

    public void detection(int sim, int[] pageFaults){
        for(int i=1; i< pageFaults.length-1; i++){
            if(pageFaults[i] < pageFaults[i+1] && pageFaults[i] !=0){
                int curDelta = pageFaults[i+1] - pageFaults[i];
                reportString += String.format("Anomaly detected simulation #%03d - %d PF's @ %d frames vs. %d PF's @ %d frames (Δ%d)\n",
                        sim, pageFaults[i], i, pageFaults[i+1], i+1, curDelta);
                if(delta<curDelta){
                    delta = curDelta;
                }
                total++;
            }
        }
    }
}
