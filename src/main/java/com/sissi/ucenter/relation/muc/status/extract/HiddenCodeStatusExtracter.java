package com.sissi.ucenter.relation.muc.status.extract;

import com.sissi.field.Fields;
import com.sissi.protocol.iq.data.XField;
import com.sissi.ucenter.relation.muc.room.RoomConfig;
import com.sissi.ucenter.relation.muc.status.CodeStatus;
import com.sissi.ucenter.relation.muc.status.CodeStatusExtracter;

/**
 * WHOIS
 * 
 * @author kim 2014年3月27日
 */
public class HiddenCodeStatusExtracter implements CodeStatusExtracter {

	/*
	 * 匿名房间174, 非匿名房间172
	 * 
	 * @see com.sissi.ucenter.relation.muc.status.CodeStatusExtracter#extract(com.sissi.field.Fields, com.sissi.ucenter.relation.muc.status.CodeStatus)
	 */
	@Override
	public CodeStatus extract(Fields fields, CodeStatus status) {
		XField hidden = fields.findField(RoomConfig.WHOIS.toString(), XField.class);
		return hidden != null && hidden.getValue() != null && Boolean.valueOf(hidden.getValue().toString()) ? status.add("174") : status.add("172");
	}
}
