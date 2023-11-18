package org.example;

import java.util.HashMap;
import java.util.HashSet;

public class TaskLRU implements Runnable{

    int[] sequence;
    int maxMemoryFrames;
    int maxPageReference;
    int[] pageFaults;

    public TaskLRU(int[]sequence, int maxMemoryFrames, int maxPageReference, int[] pageFaults){
        this.sequence = sequence;
        this.maxMemoryFrames = maxMemoryFrames; // max number of pages held
        this.maxPageReference = maxPageReference;
        this.pageFaults = pageFaults;
    }
    @Override
    public void run() {
        int faults = 0;
        HashSet<Integer> currentPages = new HashSet<>(this.maxMemoryFrames);
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
                    int lru = this.maxPageReference;
                    int index = -1;
                    //should remove 2
                    for (int temp : currentPages) {
                        if (indexes.get(temp) < lru) {
                            lru = indexes.get(temp);
                            index = temp;
                        }
                    }
                    currentPages.remove(index);
                    indexes.remove(index);
                    currentPages.add(page);
                    faults++;
                }
                indexes.put(page, i);
            }
            i++;
        }
        pageFaults[maxMemoryFrames] = faults;
    }
}
