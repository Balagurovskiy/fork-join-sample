package com.forkjoin.recursice_task;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class NodeTask {
	private int					_value;
	private List<NodeTask> 		_children;
	private Logger 				_logger;

	private static int _count = 0;
	private int _id;
	
	public NodeTask(int value) {
		_id = _count;
		_value = value;
		_children = new ArrayList<NodeTask>();
		_logger = Logger.getAnonymousLogger();
		
		_logger.info(" [ NodeTask CREATED | id : " + _id + " ] : [ value : " + _value + " ]");
		_count++;
	}
	public void add(NodeTask child) {
		_children.add(child);
	}
    public List<NodeTask> getChildren(){
    	_logger.info(" [ NodeTask access to children | id : " + _id + " ] : [ size : " + _children.size() + " ]");
    	return _children;
    }
    
    public int getValue() {
    	_logger.info(" [ NodeTask access to value | id : " + _id + " ] : [ value : " + _value + " ]");
    	return _value;
    }
    
}
