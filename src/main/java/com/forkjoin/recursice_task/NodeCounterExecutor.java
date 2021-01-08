package com.forkjoin.recursice_task;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
/**
 * Holds NodeTask (root). Calculate sum off all connected nodes by recursively joining them to available threads
 * @author OlexiySergiyovich
 *
 */
public class NodeCounterExecutor extends RecursiveTask<Integer>{
	private static final long serialVersionUID = 1L;
	private final NodeTask node;
    
    public NodeCounterExecutor(NodeTask node) {
        this.node = node;
    }
/**
 * Inherited from RecursiveTask<?>.
 * Summarizes values from task nodes.
 * Creates new executor for each child node and invokes all.
 */
    @Override
    protected Integer compute() {
        return node.getValue() + ForkJoinTask.invokeAll( 
														node.getChildren()
														.stream()
														.map(child ->  new NodeCounterExecutor(child))
														.collect(Collectors.toList())
														).stream()
															.mapToInt(ForkJoinTask::join)
															.sum();
    }
    
}