package com.forkjoin.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.forkjoin.recursice_task.NodeCounterExecutor;
import com.forkjoin.recursice_task.NodeTask;


public class NodeCounterExecutorTest {
	private NodeTask		_root ;
	private ForkJoinPool 	_forkJoinPool;
	private Logger 			_logger;
	/**
	 * Creates ForkJoinPool with pool size of available processors.
	 * Creates java.util.logging.Logger.
	 * Creates simple graph with NodeTask instances
	 */
	@Before
	public void init() {
		_forkJoinPool = new ForkJoinPool( Runtime.getRuntime().availableProcessors() );
		_logger = Logger.getAnonymousLogger();
		
		_root = new NodeTask(1);
		_root.add(new NodeTask(1));
		NodeTask _lvl11 = new NodeTask(1);
		_root.add(_lvl11);
		_lvl11.add(new NodeTask(1));
		NodeTask _lvl1 = new NodeTask(1);
		_root.add(_lvl1);
		_lvl1.add(new NodeTask(1));
		_lvl1.add(new NodeTask(1));
		NodeTask _lvl2 = new NodeTask(1);
		_lvl1.add(_lvl2);
		_lvl2.add(new NodeTask(1));
		_lvl2.add(new NodeTask(1));

		_logger.info("\n [ Node Executor TEST ] : [ INITIALIZED ]");
	}
	/**
	 * Creates Executor for root Task node and invoke executor (RacursiveTask<Integer>).
	 * Test passes if summary value from nodes equals to constant .
	 */
	@Test
	public void nodeCounterExecutor_sumResultTest() {
		_logger.info("\n [ Node Executor TEST] : [ nodeCounterExecutor_sumResultTest RUNNING . . . ]");
		int sum = _forkJoinPool.invoke(new NodeCounterExecutor(_root));
		assertEquals(10, sum);
	}
	/**
	 * Shut down fork join pool
	 */
	@After
	public void close() {
		_forkJoinPool.shutdown();
		_logger.info("\n [ Node Executor TEST ] : [ CLOSED ]");
	}
}
