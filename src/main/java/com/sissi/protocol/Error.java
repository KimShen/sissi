package com.sissi.protocol;

import java.util.List;

/**
 * @author kim 2013年12月8日
 */
public interface Error {

	public String getType();

	public String getCode();

	public List<ErrorDetail> getDetails();
}
