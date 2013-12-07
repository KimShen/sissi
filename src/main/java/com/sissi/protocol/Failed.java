package com.sissi.protocol;

import java.util.List;

/**
 * @author kim 2013年12月8日
 */
public interface Failed extends Element {

	public String getCode();

	public List<Detail> getDetails();
	
	public interface Detail {

		public String getXmlns();
	}
}
