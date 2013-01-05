package com.howie.framework.core;

/**
 * 
 * @author howieceo@163.com
 * @time Nov 9, 2012 10:18:28 AM
 */
public interface OperationInit {

	/**
	 * Do something about the initialization operation of view ,include some
	 * findViewById(),setOnClickListener(),and so on.
	 */
	void initViews();

	/**
	 * Do something about the initialization operation of data
	 */
	void initData();
}
