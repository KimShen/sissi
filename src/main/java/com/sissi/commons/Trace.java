package com.sissi.commons;

import org.apache.commons.logging.Log;

/**
 * @author kim 2014年2月24日
 */
public class Trace {

	public static void trace(Log log, Exception e) {
		if (log.isTraceEnabled()) {
			e.printStackTrace();
		}
	}
}
