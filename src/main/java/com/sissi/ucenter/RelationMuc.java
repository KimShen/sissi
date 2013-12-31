package com.sissi.ucenter;

/**
 * @author kim 2013年12月30日
 */
public interface RelationMuc extends Relation {

	public final static String KEY_ROOM = "room";

	public final static String KEY_ROLE = "role";
	
	public final static String KEY_MEMBER = "member";

	public final static String KEY_SERVICE = "service";

	public final static String KEY_AFFILICATION = "affiliation";
	
	public RelationMuc set(String role, String affiliation);
	
	public String getRoom();

	public String getRole();
	
	public String getMember();

	public String getService();

	public String getAffiliation();
}
