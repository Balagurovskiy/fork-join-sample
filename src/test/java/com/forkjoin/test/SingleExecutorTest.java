package com.forkjoin.test;

import static org.junit.Assert.assertFalse;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.forkjoin.recursive_action.SingleExecutor;
import com.forkjoin.recursive_action.Task;

public class SingleExecutorTest {
	private  Deque<Task> 		_tasks;
	private  ForkJoinPool 		_forkJoinPool;
	private  Logger 			_logger;

	private  boolean hasNotExecutedTasks(Collection<Task> tasks) {
		return tasks.stream().anyMatch(t -> t.wasExecuted() == false);
	}
	/**
	 * Creates ForkJoinPool with pool size of available processors.
	 * Creates java.util.logging.Logger.
	 * Initializes Deque<Task> with 25 Task instances
	 */
	@Before
	public void init() {
		_forkJoinPool = new ForkJoinPool( Runtime.getRuntime().availableProcessors() );
		_logger = Logger.getAnonymousLogger();
		
		_tasks = Stream
				.generate(Task::new)
				.limit(25)
				.collect( Collectors.toCollection(ArrayDeque::new) );

		_logger.info("\n [ Single Executor TEST ] : [ INITIALIZED ]");
	}
	/**
	 * Creates Executor for each Task and invoke all executors (RacursiveAction).
	 * Test passes if all tasks were executed.
	 */
	@Test
	public void singleExecutor_allTasksCompliteTest() {
		_logger.info("\n [ Single Executor TEST] : [ singleExecutor_allTasksCompliteTest RUNNING . . . ]");
		ForkJoinTask.invokeAll(
								_tasks.stream()
								.map(t -> new SingleExecutor( t ))
								.collect(Collectors.toList())
								);
		assertFalse( hasNotExecutedTasks(_tasks) );
	}
	/**
	 * Shut down fork join pool
	 */
	@After
	public void close() {
		_forkJoinPool.shutdown();
		_logger.info("\n [ Single Executor TEST ] : [ CLOSED ]");
	}
}
