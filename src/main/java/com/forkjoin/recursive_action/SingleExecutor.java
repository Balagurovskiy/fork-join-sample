package com.forkjoin.recursive_action;

import java.util.concurrent.RecursiveAction;
import java.util.logging.Logger;
/**
 * Executor for a single Task.
 * Have to be invoked throw collection of executors.
 * @author OlexiySergiyovich
 *
 */
public class SingleExecutor extends RecursiveAction{
	private static final long serialVersionUID = 1L;
	private Task _tasks;
	private Logger _logger;
	
	public SingleExecutor(Task task) {
		_tasks = task;
		_logger = Logger.getAnonymousLogger();
	}
 /**
  * Inherited from RecursiveAction.
  * Executes single task with fixed number of attempts.
  */
	@Override
	protected void compute() {
		
		final int 	MAX_ATTEMPTS = 10;
		int 		attempts = 0;
		
		if (_tasks == null) {
			_logger.info("\t [Executor] [ NOPE, EXECUTOR WILL NOT MAKE AN EMPTY TASK !] ");
			return ;
		}
		
		Boolean workOnTask = true;
		
		while(workOnTask) {
			if(MAX_ATTEMPTS <= attempts) {
				_logger.info("\t [Executor] [ SORRY, TOO MANY ATTEMPTS TO MAKE THIS TASK ! IT'S TOUGH ! ] ");
				break ;
			}
			workOnTask = ! _tasks.execute();
			attempts++;
		}
	}
}
