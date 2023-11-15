package org.example;

import java.util.ArrayList;
import java.util.Iterator;

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
        ArrayList<Integer> currentPages = new ArrayList<>();
        ArrayList<Integer> indexes = new ArrayList<>();
        int i =0;
        for (int page: this.sequence) {
            if(currentPages.size() < this.maxMemoryFrames){
                if(!currentPages.contains(page)){
                    currentPages.add(page);
                    faults++;
                }
                indexes.add(page, i);
            }else{
                if(!currentPages.contains(page)){
                    int lru = this.maxPageReference;
                    int index = 0;

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
            }
            i++;
        }
        pageFaults[maxMemoryFrames] = faults;
    }
}
