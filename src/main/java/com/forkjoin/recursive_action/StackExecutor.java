package com.forkjoin.recursive_action;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
/**
 * Executes Deque with tasks.
 * If stack size is mores that constant threshold divides stack to perform in sub-executors.
 * 
 * @author OlexiySergiyovich
 *
 */
public class StackExecutor extends RecursiveAction{
	private static final long serialVersionUID = 1L;

	private Deque<Task> _tasks;
	
	private final int THRESHOLD = 3;
	
	public StackExecutor(Collection<Task> tasks) {
		_tasks = tasks.stream().collect(Collectors.toCollection(ArrayDeque::new));
	}
	public StackExecutor(Deque<Task> tasks) {
		_tasks = tasks;
	}
	public StackExecutor() {
		_tasks = new ArrayDeque<>();
	}

	public void takeTask(Task task) {
		_tasks.push(task);
	}
	public Task giveTask() {
		return _tasks.pop();
	}
 /**
  * Inherited from RecursiveAction.
  * Shares and executes tasks. 
  * If task failed to execute it will be places back to stack to make another attempt.
  * Maximum number of total attempts (stack size * 3).
  * When task executed successfully it will be removed from stack.
  */
	@Override
	protected void compute() {
		shareTask();
		
		final int MAX_ATTEMPTS = _tasks.size() * 3;
		int attempts = 0;
		
		while(_tasks.size() > 0 && MAX_ATTEMPTS >= attempts) {
			Task currentTask = this.giveTask();
			Boolean taskResult = currentTask.execute();
			if (taskResult == false) {
				this.takeTask( currentTask );
			}
			attempts++;
		}
	}

/**
 * If stack with tasks is larger that fixed threshold will be created new executors to share tasks.
 * Each newly executor will have stack with maximum size of threshold.
 * Collection of executors with shared tasks will be invoked to available threads by ForkJoinTask
 */
	private void shareTask() {
		Collection<StackExecutor> executors = new ArrayList<>();
		while(_tasks.size() > THRESHOLD) {
			StackExecutor executor = new StackExecutor();
			share(executor, THRESHOLD);
			executors.add( executor );
		}
		ForkJoinTask.invokeAll(executors);
	}
/**
 * Pops task from current stack to new executor.
 * 
 * @param that executor to share tasks
 * @param size number of tasks that will be poped
 */
	private void share(StackExecutor that, int size) {
		while(_tasks.size() > 0 && size > 0) {
			that.takeTask( this.giveTask() );
			size--;
		}
	}
}
