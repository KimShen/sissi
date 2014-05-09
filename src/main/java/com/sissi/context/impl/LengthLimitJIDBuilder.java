package com.sissi.context.impl;

import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;

/**
 * @author kim 2013年12月23日
 */
public class LengthLimitJIDBuilder implements JIDBuilder {

	private final String connectAt = "@";

	private final String connectResource = "/";

	private final int limit;

	private final String group;

	private final String domain;

	/**
	 * @param limit JID长度限制
	 * @param group MUC域
	 * @param domain 服务器域
	 */
	public LengthLimitJIDBuilder(int limit, String group, String domain) {
		super();
		this.limit = limit;
		this.group = group;
		this.domain = domain;
	}

	@Override
	public JID build(String jid) {
		String trim = (jid != null ? jid.trim() : null);
		// 如果无法解析则使用OFFLINE
		return (trim != null && !trim.isEmpty()) ? new User(trim) : OfflineJID.OFFLINE;
	}

	public JID build(String username, String resource) {
		return new User(username, resource);
	}

	private class User implements JID {

		private boolean valid = Boolean.TRUE;

		private String user;

		private String domain;

		private String resource;

		private String stringWithBare;

		private User(String jid) {
			super();
			try {
				if (jid.length() > LengthLimitJIDBuilder.this.limit) {
					this.valid = false;
					return;
				}
				StringBuffer buffer = new StringBuffer(jid);
				int startMain = buffer.indexOf(LengthLimitJIDBuilder.this.connectAt);
				this.user = startMain == -1 ? null : buffer.substring(0, startMain);
				int startResource = buffer.indexOf(LengthLimitJIDBuilder.this.connectResource);
				this.domain = (startResource == -1 ? buffer.substring(startMain != -1 ? startMain + 1 : 0) : buffer.substring(startMain + 1, startResource));
				this.resource = startResource == -1 ? null : buffer.substring(startResource + 1);
			} catch (Exception e) {
				this.valid = false;
			}
		}

		private User(String user, String resource) {
			super();
			this.user = user;
			this.resource = resource;
		}

		private User copy4Bare() {
			return new User(this.user, null).domain(this.domain());
		}

		public String user() {
			return this.user;
		}

		public String domain() {
			return this.domain != null ? this.domain : LengthLimitJIDBuilder.this.domain;
		}

		public User domain(String domain) {
			this.domain = domain;
			return this;
		}

		/*
		 * Not allowed null and empty
		 * 
		 * @see com.sissi.context.JID#resource()
		 */
		public String resource() {
			return this.resource != null && !this.resource.isEmpty() ? this.resource : null;
		}

		@Override
		public JID resource(String resource) {
			this.resource = resource;
			return this;
		}

		public JID bare() {
			return this.copy4Bare();
		}

		public JID clone() {
			return this.copy4Bare().resource(this.resource());
		}

		public boolean isBare() {
			return this.resource() == null;
		}

		public boolean isGroup() {
			return LengthLimitJIDBuilder.this.group.equals(this.domain());
		}

		public boolean same(JID jid) {
			return this.same(jid.asString());
		}

		public boolean same(String jid) {
			return this.asString().equals(jid);
		}

		public boolean like(JID jid) {
			return this.like(jid.asStringWithBare());
		}

		public boolean like(String jid) {
			return this.asStringWithBare().equals(jid);
		}

		public boolean valid() {
			return this.valid(true);
		}

		/*
		 * 解析正确,长度正确,指定部分不含错误关键字
		 * 
		 * @see com.sissi.context.JID#valid(boolean)
		 */
		public boolean valid(boolean excludeDomain) {
			return this.valid && this.validKeyword(this.user(), true) && this.validKeyword(this.domain(), excludeDomain);
		}

		private boolean validKeyword(String part, boolean allowNull) {
			return (part != null && !part.isEmpty()) ? !part.contains(LengthLimitJIDBuilder.this.connectAt) && !part.contains(LengthLimitJIDBuilder.this.connectResource) : (false || allowNull);
		}

		public String asString() {
			return this.asStringWithBare() + (this.isBare() ? "" : LengthLimitJIDBuilder.this.connectResource + this.resource());
		}

		public String asStringWithBare() {
			return this.stringWithBare != null ? this.stringWithBare : (this.domain() != null ? this.stringWithBare(true) : this.stringWithBare(false));
		}

		/**
		 * 是否缓存JID
		 * 
		 * @param cached
		 * @return
		 */
		private String stringWithBare(boolean cached) {
			String stringWithBare = (this.user() != null ? this.user() + LengthLimitJIDBuilder.this.connectAt : "") + this.domain();
			return cached ? (this.stringWithBare = stringWithBare) : stringWithBare;
		}

		public String asString(boolean bare) {
			return bare ? this.asStringWithBare() : this.asString();
		}
	}
}
