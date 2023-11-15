package org.example;

import java.util.ArrayList;

public class TaskFIFO implements Runnable{

    int[] sequence;
    int maxMemoryFrames;
    int maxPageReference;
    int[] pageFaults;
    public TaskFIFO(int[]sequence, int maxMemoryFrames, int maxPageReference, int[] pageFaults){
        this.sequence = sequence;
        this.maxMemoryFrames = maxMemoryFrames; // max number of pages held
        this.maxPageReference = maxPageReference;
        this.pageFaults = pageFaults;
        //sequence: randomly generated sequences of page references
        //maxMemoryFrames: number of frames of memory available
        //maxPageReference: max page reference possible in a sequence
        //pageFaults: output an array used to record the number of page faults that occur at each
        //simulation of some number of frames. i.e. pageFaults[maxMemoryFrames] = pageFaults
    }

    @Override
    public void run() {
        int faults = 0;
        ArrayList<Integer> currentPages = new ArrayList<>();
        for (int page: this.sequence) {
            if(currentPages.size() < this.maxMemoryFrames){
                if(!currentPages.contains(page)){
                    currentPages.add(page);
                    faults++;
                }
            }else{
                if(!currentPages.contains(page)){
                    currentPages.remove(0);
                    currentPages.add(page);
                    faults++;
                }
            }
        }
        pageFaults[maxMemoryFrames] = faults;
    }
}
