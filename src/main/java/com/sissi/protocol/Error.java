package com.sissi.protocol;

import java.util.List;

/**
 * @author kim 2013年12月8日
 */
public interface Error extends Element{

	public String getType();

	public String getCode();
	
	public ErrorText getText();

	public List<ErrorDetail> getDetails();
}
