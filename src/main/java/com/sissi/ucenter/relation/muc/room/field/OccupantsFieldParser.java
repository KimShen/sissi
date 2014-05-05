package com.sissi.ucenter.relation.muc.room.field;

import java.util.Map;

import com.sissi.config.Dictionary;
import com.sissi.field.Field;
import com.sissi.field.FieldParser;
import com.sissi.protocol.iq.data.XInput;
import com.sissi.protocol.muc.MucDomain;
import com.sissi.ucenter.relation.muc.room.RoomInfo;

/**
 * 房间在线人数
 * 
 * @author kim 2013年12月12日
 */
public class OccupantsFieldParser implements FieldParser<Map<String, Object>> {

	private final XInput empty = new XInput(null, Dictionary.FIELD_MAXUSERS, RoomInfo.OCCUPANTS.toString().toString(), "0");

	@Override
	public Field<?> read(Map<String, Object> element) {
		Object count = element.get(Dictionary.FIELD_MAXUSERS);
		return count == null ? this.empty : new XInput(null, Dictionary.FIELD_MAXUSERS, MucDomain.ROOMINFO_OCCUPANTS.toString(), count.toString());
	}

	public String support() {
		return Dictionary.FIELD_MAXUSERS;
	}
}
