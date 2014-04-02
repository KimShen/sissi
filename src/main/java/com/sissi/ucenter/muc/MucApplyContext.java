package com.sissi.ucenter.muc;

import com.sissi.context.JID;
import com.sissi.ucenter.field.Fields;

/**
 * @author kim 2014年3月12日
 */
public interface MucApplyContext {

	public final static String MUC_JID = "muc#jid";

	public final static String MUC_ROLE = "muc#role";

	public final static String MUC_NICKNAME = "muc#roomnick";

	public final static String MUC_REQUEST_ALLOW = "muc#request_allow";

	public boolean apply(JID from, JID to, Fields fields);
}
