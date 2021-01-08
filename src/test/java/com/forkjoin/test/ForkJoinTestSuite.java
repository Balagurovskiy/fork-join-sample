package com.forkjoin.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	StackExecutorTest.class,
	NodeCounterExecutorTest.class,
	SingleExecutorTest.class
})

public class ForkJoinTestSuite {

	public ForkJoinTestSuite() {
//	    Result result = JUnitCore.runClasses(JunitTestSuite.class);
//		    for (Failure failure : result.getFailures()) {
//		       System.out.println(failure.toString());
//		    }
//				
//		    System.out.println(result.wasSuccessful());
	}
}