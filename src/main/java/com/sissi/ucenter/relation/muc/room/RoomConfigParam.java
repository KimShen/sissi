package com.sissi.ucenter.relation.muc.room;

import java.util.Map;

import com.sissi.context.JID;
import com.sissi.ucenter.relation.muc.MucRelation;

/**
 * 房间配置申请单
 * 
 * @author kim 2014年3月5日
 */
public interface RoomConfigParam {

	/**
	 * 申请人
	 * 
	 * @return
	 */
	public JID user();

	/**
	 * 申请人岗位是否符合房间岗位要求
	 * 
	 * @return
	 */
	public boolean affiliation();

	/**
	 * 申请人岗位是否符合指定岗位要求
	 * 
	 * @param affiliation
	 * @return
	 */
	public boolean affiliation(String affiliation);

	/**
	 * 是否为房间创建人
	 * 
	 * @return
	 */
	public boolean creator();

	/**
	 * 是否允许查看真实JID
	 * 
	 * @param compute 是否比较角色/岗位, 如果为False则仅以房间配置为准
	 * @return
	 */
	public boolean hidden(boolean compute);

	/**
	 * 是否已经激活
	 * 
	 * @param compute 是否比较角色/岗位, 如果为False则仅以房间配置为准
	 * @return
	 */
	public boolean activate(boolean compute);

	/**
	 * 获取订阅关系
	 * 
	 * @return
	 */
	public MucRelation relation();

	/**
	 * 获取全部配置
	 * 
	 * @return
	 */
	public Map<String, Object> configs();
}
