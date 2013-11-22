package com.sissi.context;

/**
 * @author kim 2013-11-19
 */
public interface JIDContextParam {

	public static final JIDContextParam NOTHING = new NothingJIDContextParam();

	public <T> T find(String key, Class<T> clazz);

	public final class NothingJIDContextParam implements JIDContextParam {

		private NothingJIDContextParam() {

		}

		@Override
		public <T> T find(String key, Class<T> clazz) {
			return null;
		}
	}
}
