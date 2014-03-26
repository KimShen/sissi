package com.sissi.ucenter.muc;

import com.sissi.ucenter.Relation;

/**
 * @author kim 2014年2月11日
 */
public interface RelationMuc extends Relation {

	public String role();

	public RelationMuc role(String role);

	public RelationMuc role(String role, boolean force);

	public String affiliation();

	public RelationMuc affiliation(String affiliation);

	public String resource();

	public boolean outcast();

	public boolean name(String name, boolean allowNull);

	public RelationMuc noneRole();
}
