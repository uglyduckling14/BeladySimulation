package org.example;

public class TaskMRU implements Runnable {

    public TaskMRU(int[] sequence, int maxMemoryFrames, int maxPageReference, int[] pageFaults){
        //sequence: randomly generated sequences of page references
        //maxMemoryFrames: number of frames of memory available
        //maxPageReference: max page reference possible in a sequence
        //pageFaults: outpu an array used to record the number of page faults that occur at each
        //simulation of some number of frames. i.e. pageFaults[maxMemoryFrames] = pageFaults
    }

    @Override
    public void run() {

    }
}
