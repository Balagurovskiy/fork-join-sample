package com.forkjoin.recursive_action;
import java.util.Random;
import java.util.concurrent.RecursiveTask;
import java.util.logging.Logger;
/**
 * Example task.
 * To simulate work time uses Thread.sleep with random value from 500 to 2000 ms.
 * 'Execution' time is in dependency with fail ration of task (simulation of possible exception).
 * Fail rate = 100 * (randExecuteTime - MIN_SEED(500)) / (MAX_SEED(1500) - MIN_SEED(500))
 * @author OlexiySergiyovich
 *
 */
public class Task{
	
	private Logger _logger;
	
	private static int _count = 0;
	private int _id;
	
	private final int MAX_SEED = 1500;
	private final int MIN_SEED = 500;
	
	private int _timeToExecuteTask;
	private int _taskFailRate;
	
	private Boolean _currentStatus;
	
	public Task() {
		_logger = Logger.getAnonymousLogger();
		
		_id = _count;
		_timeToExecuteTask = new Random().nextInt(MAX_SEED - MIN_SEED) + MIN_SEED;	// 500 - 2000
		_taskFailRate = 100 * (_timeToExecuteTask - MIN_SEED) / MAX_SEED; //(MAX_SEED - MIN_SEED);
		_currentStatus = false;
		_count++;
		
		_logger.info("\t [ Task Created | id : " + _id + " ] : "
										+ "[ time to execute : " + _timeToExecuteTask + " | "
										+ "fail rate : "+ _taskFailRate + " ]");
	}
	
	private Boolean getExecutionRate() {
		return (new Random().nextInt(100) >= _taskFailRate);
	}
	
	protected Boolean execute() {
		
		_logger.info("\t [ Task Executing | id : " + _id + " ] : "
										+ "[ " + Thread.currentThread().getName() + " ]");
		
		try {
			Thread.sleep( _timeToExecuteTask );
		} catch (InterruptedException e) {
			_logger.info("\t [ Task Exception | id : " + _id + " ] : "
					+ "[ InterruptedException ]");
		}
		_currentStatus = getExecutionRate();
		
		_logger.info("\t [ Task Result | id : " + _id + " ] : "
										+ "[ " + _currentStatus + " ]");
		
		return _currentStatus;
	}

	public boolean wasExecuted() {
		return _currentStatus;
	}
}
