package com.sissi.pipeline.process.auth.normalize;

import junit.framework.Assert;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import com.sissi.pipeline.process.auth.AuthCertificate;
import com.sissi.pipeline.process.auth.impl.PlainAuthNormalization;
import com.sissi.protocol.auth.Auth;

/**
 * @author kim 2013-11-12
 */
public class PlainAuthNormalizationTest {

	@Test
	public void testNormalize() {
		Auth auth = new Auth();
		auth.setMechanism(PlainAuthNormalization.MECHANISM);
		auth.setText(new String(new Base64().encode(new byte[] { 97, 98, 99, 0, 99, 98, 97 })));
		PlainAuthNormalization normalization = new PlainAuthNormalization();
		AuthCertificate user = normalization.normalize(auth);
		Assert.assertEquals("abc", user.getUser());
		Assert.assertEquals("cba", user.getPass());
	}
}
