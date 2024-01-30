package com.example.demo.garbageCollector;

/*
Forms of GC:
    Do nothing
    Reference Counting: When the count is 0, object is not being reference
    Mark and Sweep:
        Mark: identifies the objects that are still in use
        Sweep: remove unused objects
        compact: phase to compact the memory. We change physical reference in memory
    Copying: After the sweep phase all the memory left is copiedfrom one buffer into another
    Generational: If an object survives GC, GC will not look for it for a while
    Incremental: Does look memory all the time doing the GC

Circular references:

 */
public class GC {
}
