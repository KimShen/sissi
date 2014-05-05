package com.sissi.ucenter.relation.muc.status;

import com.sissi.field.Fields;

/**
 * 状态码提炼
 * 
 * @author kim 2014年3月27日
 */
public interface CodeStatusExtracter {

	public CodeStatus extract(Fields fields, CodeStatus status);
}
