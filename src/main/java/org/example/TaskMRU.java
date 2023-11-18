package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class TaskMRU implements Runnable {
    int[] sequence;
    int maxMemoryFrames;
    int maxPageReference;
    int[] pageFaults;
    public TaskMRU(int[] sequence, int maxMemoryFrames, int maxPageReference, int[] pageFaults){
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
        HashSet<Integer> currentPages = new HashSet<>();
        HashMap<Integer, Integer> indexes = new HashMap<>();
        int i =0;
        for (int page: this.sequence) {
            if(currentPages.size() < this.maxMemoryFrames){
                if(!currentPages.contains(page)){
                    currentPages.add(page);
                    faults++;
                }
                indexes.put(page, i);
            }else{
                if(!currentPages.contains(page)){
                    int mru = -1;
                    int index = -1;

                    for (int temp : currentPages) {
                        if (indexes.containsKey(temp) && indexes.get(temp) > mru) {
                            mru = indexes.get(temp);
                            index = temp;
                        }
                    }
                    currentPages.remove(index);
                    indexes.remove(index);
                    currentPages.add(page);
                    faults++;
                }
            }
            i++;
        }
        pageFaults[maxMemoryFrames] = faults;
    }
}
