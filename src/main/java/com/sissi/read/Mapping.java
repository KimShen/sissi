package com.sissi.read;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author kim 2013-10-25
 */
public interface Mapping {

	public Object newInstance(String uri, String localName);

	public Boolean hasCached(String uri, String localName);

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.TYPE })
	@Inherited
	public @interface MappingMetadata {

		public String[] uri();

		public String localName();
	}
}
