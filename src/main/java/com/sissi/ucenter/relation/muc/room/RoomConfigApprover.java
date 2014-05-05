package com.sissi.ucenter.relation.muc.room;

/**
 * 房间配置审批者
 * 
 * @author kim 2014年3月5日
 */
public interface RoomConfigApprover {

	/**
	 * 是否同意
	 * 
	 * @param param
	 * @param request
	 * @return
	 */
	public boolean approve(RoomConfigParam param, Object request);

	public RoomConfig support();
}
