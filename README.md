# Assn6

## Description:

Simulate FIFO, LRU and MRU. Report page faults and Belady's Anomaly (should only occur in FIFO).

## Requirements:

Your simulation must meet these specifications:

    Use a thread pool (Executors.newFixedThreadPool(...)) to execute individual simulation tasks.  Create as 
    many workers as there are processors available on the system.
        https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/util/concurrent/ExecutorService.html 

        Links to an external site.
        We did this in class a few weeks ago as well.
    Create three classes that implement the Runnable interface, each representing a page replacement algorithm
        TaskFIFO
        TaskLRU
        TaskMRU
    The constructors for each of these classes look like...
        public TaskFIFO(int[] sequence, int maxMemoryFrames, int maxPageReference, int[] pageFaults)
            sequence : (input) a randomly generated sequence of page references
            maxMemoryFrames : (input) the number of frames of memory available
            maxPageReference : (input) the maximum page reference possible in the sequence
            pageFaults : (output) an array used to record the number of page faults that occur each simulation 
    of some number of frames.  Each call to the 'run' method of a task results in storing the number of page faults 
    for the task using something like: pageFaults[maxMemoryFrames] = pageFaults (where pageFaults is the number of page faults your code detects).

The program runs 1,000 simulations.  For each simulation the program will generate a randomized page reference
sequence of 1,000 items, where a page reference is an integer in the interval [1, 250].  For this page reference sequence, the 
program should try each page-replacement algorithm against different sizes of memory going from 1 frame up to and 
including 100 frames

### Note: Error margin +-2

## Pseudocode:

    For each simulation (from 1 to 1000)
        Generate a randomized page reference sequence p of length 1000 with page references drawn from the interval [1, 250].
        Some page references will be repeated while some page references might not occur.
        For each count of main memory frames f (from 1 to 100)
            Create a FIFO simulation task, e.g. Runnable fifo = new TaskFIFO(p, f, 250, pageFaults)
            Create a LRU simulation task, e.g. Runnable lru = new TaskLRU(p, f, 250, pageFaults)
            Create a MRU simulation task, e.g. Runnable mru = new TaskMRU(p, f, 250, pageFaults)
            Add these Runnable objects to the thread pool for execution
    Wait for the tasks to complete
    Report the total simulation time (in milliseconds)
    Summarize the results
        Report how many times each algorithm had the lowest number of page faults for a particular page reference 
        sequence and frame size.
        There can be ties; if all three algorithms perform equally well, then count all 3 as reaching the minimum.
        Count the number of times Belady's Anomaly occurred, reporting as shown in the example below.

## Test Cases:
Simulation took 1795 ms

FIFO min PF: 30950
LRU min PF : 32373
MRU min PF : 48546

Belady's Anomaly Report for FIFO
Anomaly detected in simulation #016 - 653 PF's @  92 frames vs. 654 PF's @  93 frames (Δ1)
Anomaly detected in simulation #030 - 664 PF's @  94 frames vs. 665 PF's @  95 frames (Δ1)
Anomaly detected in simulation #040 - 685 PF's @  79 frames vs. 686 PF's @  80 frames (Δ1)
Anomaly detected in simulation #057 - 652 PF's @  91 frames vs. 654 PF's @  92 frames (Δ2)
Anomaly detected in simulation #114 - 615 PF's @  97 frames vs. 616 PF's @  98 frames (Δ1)
Anomaly detected in simulation #117 - 618 PF's @  98 frames vs. 619 PF's @  99 frames (Δ1)
Anomaly detected in simulation #154 - 661 PF's @  91 frames vs. 662 PF's @  92 frames (Δ1)
Anomaly detected in simulation #191 - 754 PF's @  72 frames vs. 755 PF's @  73 frames (Δ1)
Anomaly detected in simulation #261 - 740 PF's @  67 frames vs. 741 PF's @  68 frames (Δ1)
Anomaly detected in simulation #271 - 688 PF's @  81 frames vs. 691 PF's @  82 frames (Δ3)
Anomaly detected in simulation #352 - 640 PF's @  96 frames vs. 641 PF's @  97 frames (Δ1)
Anomaly detected in simulation #358 - 694 PF's @  79 frames vs. 695 PF's @  80 frames (Δ1)
Anomaly detected in simulation #407 - 680 PF's @  79 frames vs. 682 PF's @  80 frames (Δ2)
Anomaly detected in simulation #425 - 632 PF's @  92 frames vs. 633 PF's @  93 frames (Δ1)
Anomaly detected in simulation #436 - 669 PF's @  87 frames vs. 671 PF's @  88 frames (Δ2)
Anomaly detected in simulation #451 - 694 PF's @  80 frames vs. 695 PF's @  81 frames (Δ1)
Anomaly detected in simulation #470 - 649 PF's @  88 frames vs. 652 PF's @  89 frames (Δ3)
Anomaly detected in simulation #483 - 690 PF's @  78 frames vs. 691 PF's @  79 frames (Δ1)
Anomaly detected in simulation #493 - 688 PF's @  86 frames vs. 689 PF's @  87 frames (Δ1)
Anomaly detected in simulation #519 - 611 PF's @  99 frames vs. 613 PF's @ 100 frames (Δ2)
Anomaly detected in simulation #542 - 660 PF's @  90 frames vs. 661 PF's @  91 frames (Δ1)
Anomaly detected in simulation #550 - 664 PF's @  89 frames vs. 665 PF's @  90 frames (Δ1)
Anomaly detected in simulation #570 - 744 PF's @  67 frames vs. 745 PF's @  68 frames (Δ1)
Anomaly detected in simulation #606 - 623 PF's @  99 frames vs. 625 PF's @ 100 frames (Δ2)
Anomaly detected in simulation #609 - 772 PF's @  58 frames vs. 773 PF's @  59 frames (Δ1)
Anomaly detected in simulation #625 - 626 PF's @  97 frames vs. 627 PF's @  98 frames (Δ1)
Anomaly detected in simulation #676 - 645 PF's @  92 frames vs. 646 PF's @  93 frames (Δ1)
Anomaly detected in simulation #705 - 614 PF's @  99 frames vs. 620 PF's @ 100 frames (Δ6)
Anomaly detected in simulation #713 - 723 PF's @  71 frames vs. 724 PF's @  72 frames (Δ1)
Anomaly detected in simulation #719 - 648 PF's @  84 frames vs. 649 PF's @  85 frames (Δ1)
Anomaly detected in simulation #719 - 609 PF's @  95 frames vs. 613 PF's @  96 frames (Δ4)
Anomaly detected in simulation #775 - 656 PF's @  94 frames vs. 657 PF's @  95 frames (Δ1)
Anomaly detected in simulation #777 - 658 PF's @  88 frames vs. 659 PF's @  89 frames (Δ1)
Anomaly detected in simulation #784 - 622 PF's @  97 frames vs. 623 PF's @  98 frames (Δ1)
Anomaly detected in simulation #788 - 654 PF's @  86 frames vs. 655 PF's @  87 frames (Δ1)
Anomaly detected in simulation #823 - 638 PF's @  99 frames vs. 639 PF's @ 100 frames (Δ1)
Anomaly detected in simulation #824 - 616 PF's @  99 frames vs. 617 PF's @ 100 frames (Δ1)
Anomaly detected in simulation #842 - 676 PF's @  83 frames vs. 677 PF's @  84 frames (Δ1)
Anomaly detected in simulation #844 - 743 PF's @  64 frames vs. 745 PF's @  65 frames (Δ2)
Anomaly detected in simulation #849 - 647 PF's @  95 frames vs. 648 PF's @  96 frames (Δ1)
Anomaly detected in simulation #852 - 620 PF's @  99 frames vs. 622 PF's @ 100 frames (Δ2)
Anomaly detected in simulation #862 - 641 PF's @  94 frames vs. 642 PF's @  95 frames (Δ1)
Anomaly detected in simulation #871 - 637 PF's @  91 frames vs. 638 PF's @  92 frames (Δ1)
Anomaly detected in simulation #887 - 791 PF's @  60 frames vs. 792 PF's @  61 frames (Δ1)
Anomaly detected in simulation #889 - 670 PF's @  85 frames vs. 671 PF's @  86 frames (Δ1)
Anomaly detected in simulation #893 - 636 PF's @  94 frames vs. 637 PF's @  95 frames (Δ1)
Anomaly detected in simulation #912 - 744 PF's @  68 frames vs. 747 PF's @  69 frames (Δ3)
Anomaly detected in simulation #933 - 614 PF's @  94 frames vs. 615 PF's @  95 frames (Δ1)
Anomaly detected in simulation #957 - 633 PF's @  95 frames vs. 635 PF's @  96 frames (Δ2)
Anomaly detected in simulation #960 - 687 PF's @  83 frames vs. 688 PF's @  84 frames (Δ1)
Anomaly detected in simulation #963 - 686 PF's @  85 frames vs. 688 PF's @  86 frames (Δ2)
Anomaly detected in simulation #965 - 672 PF's @  88 frames vs. 674 PF's @  89 frames (Δ2)
Anomaly detected in simulation #984 - 641 PF's @  98 frames vs. 642 PF's @  99 frames (Δ1)
Anomaly detected 53 times in 1000 simulations with a max delta of 6

Belady's Anomaly Report for LRU
Anomaly detected 0 times in 1000 simulations with a max delta of 0

Belady's Anomaly Report for MRU
Anomaly detected 0 times in 1000 simulations with a max delta of 0



## Belady's Anomaly:

In computer storage, Bélády's anomaly is the phenomenon in which increasing the number of page frames results in an 
increase in the number of page faults for certain memory access patterns.