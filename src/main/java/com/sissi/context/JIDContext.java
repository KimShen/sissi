package com.sissi.context;

import com.sissi.protocol.Element;

/**
 * @author kim 2013-10-27
 */
public interface JIDContext {

	public Long getIndex();

	public JIDContext setJid(JID jid);

	public JID getJid();

	public JIDContext setAuth(Boolean canAccess);

	public Boolean isAuth();

	public JIDContext setBinding(Boolean isBinding);

	public Boolean isBinding();

	public JIDContext setPriority(Integer priority);

	public Integer getPriority();
	
	public JIDContext setLang(String lang);
	
	public String getLang();

	public Status getStatus();

	public JIDContext starttls();
	
	public Boolean isTls();
	
	public JIDContext reset();
	
	public Boolean close();
	
	public Boolean closePrepare();

	public JIDContext write(Element element);
}
