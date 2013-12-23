package com.sissi.pipeline;

import com.sissi.write.Transfer;

/**
 * @author kim 2013年12月23日
 */
public interface OutputBuilder {

	public Output build(Transfer transfer);
}