package com.sissi.pipeline.in.iq.vcard;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.vcard.VCard;
import com.sissi.ucenter.VCardContext;

/**
 * @author kim 2013年12月10日
 */
public class VCardGet2FansProcessor extends UtilProcessor {

	private VCardContext vcardContext;

	public VCardGet2FansProcessor(VCardContext vcardContext) {
		super();
		this.vcardContext = vcardContext;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		// "dUJFSjV1aHVFYVY1cHprbEJySDIvUDdSNDd5RmVXeU1ET3NDUDNwVGdNazBwVUFrV0xDSCt4UWlreEFhS3N0MVJaVndDa3lzL0J1dVRKelRZcVZLNnBsQXNxRFFhVW5CZXBSbHNaUzhHS05aMDZsMG9KRU90Q2s2bEhqTWNOT0pKdG54d1I3SDRvdllSZU1nZ3hZTElDVmlwVzJKZlpKVmRFS1RKTnpvdkFUczNVS1FxZk9Tb09zdXpPSXVPb055cmNuemdEc2RGbXhiL3RGWEJNNVhCWDZXdEsyNTYvc2RCR01vejZSRTh3NS9SanAxelBBaU5JRFFsQjNTZm1tREdoYlFoT2hXK1I3empTbzQ5dTBnaXBMVkZ5enlhQ0c4eTFncGVmK2EyZi84ZlFNdDRuazNSSFcxQ1dLTGNJd0ZhdVhhUWI2dVQ1ckI5Z0ExdHJsR25yZGdOOHpic3B6RkhBd09ETU9Mbk9BSEdBT2F2SHB4N1JtQ2dpdzBWd0RkbXBJdE1ESFdjQlhVbzdjcjZJMTYxb2NPRWZyTU45L0Fnb0ltWVNjOVRWTkJua2lPNzlGbFpQbFVIcitERm9FSTUzenJnTUM1VEpLYWRkQ3VHRUJnaHhjTzZncFI5aXFNUjZ6b3dsTWdEWWM5NnJibEQyeTRFamIxVnUxbWNCaU53cjRMVzYvSTJaQ25xcmpYa3BnQVcyRHFKaVIxSG8wcVk2NnhrbXAyZlRELy9NekxQY0M3MVVKM0hrajBLQWFpQ0VlVXgxUkowRkpXN2hZNWluelZoeTMvSDBZNTh5RUNYRkROeWxDdHdxeDAydktWS21abG5xeVFNcWpiUXVESk5PcjdISzcvYXE0SlRuT2F3ZVUzVnJ5OWQzN0dvV01lQ29LVUpxK0F6NkJLNHJLeG5lU01vTVNpUzdsbmYvVGhGR0pmUTI0c3cxQUNXQXBxV2tYOUYyZGZ6aWxhU2J4a0dHRHBheG15NG00WlkvMW9sNFNONHZ3QzA1VVNabEdFUGtGQUNnVzUxcGl5ZHVjOUxjcC9nQzU0bklhREFwVDFsWW9FdGtWcjJwRlZTcUhqN2JocUIwWmxLcENnOWpKc0JabExaam9ycU52a0ZLZzVacDRUZ1NZcHV4V1dla0U3dDYrM3BheXFyaXRoUmVZakFVSXdkM0lZTC9HKzZ4T2g5S2RSTjFBQUFBQUVsRlRrU3VRbUND";
		//byte[] bytes = Base64.decodeBase64(code);
		//VCard.class.cast(protocol).setPhoto(new Photo("image/jpeg", bytes));
		//context.write(protocol.getParent().reply().setTo(context.getJid().asStringWithBare()).setType(Type.RESULT));
		IQ iq = (IQ)this.vcardContext.pull(super.build(protocol.getParent().getTo()), VCard.class.cast(protocol)).getParent().reply().setTo(context.getJid().asStringWithBare()).setType(Type.RESULT);
		context.write(iq);
		return true;
	}

	public static void main(String[] args) throws IOException {
		String code = "uBEJ5uhuEaV5pzklBrH2/P7R47yFeWyMDOsCP3pTgMk0pUAkWLCH+xQikxAaKst1RZVwCkys/BuuTJzTYqVK6plAsqDQaUnBepRlsZS8GKNZ06l0oJEOtCk6lHjMcNOJJtnxwR7H4ovYReMggxYLICVipW2JfZJVdEKTJNzovATs3UKQqfOSoOsuzOIuOoNyrcnzgDsdFmxb/tFXBM5XBX6WtK256/sdBGMoz6RE8w5/Rjp1zPAiNIDQlB3SfmmDGhbQhOhW+R7zjSo49u0gipLVFyzyaCG8y1gpef+a2f/8fQMt4nk3RHW1CWKLcIwFauXaQb6uT5rB9gA1trlGnrdgN8zbspzFHAwODMOLnOAHGAOavHpx7RmCgiw0VwDdmpItMDHWcBXUo7cr6I161ocOEfrMN9/AgoImYSc9TVNBnkiO79FlZPlUHr+DFoEI53zrgMC5TJKaddCuGEBghxcO6gpR9iqMR6zowlMgDYc96rblD2y4Ejb1Vu1mcBiNwr4LW6/I2ZCnqrjXkpgAW2DqJiR1Ho0qY66xkmp2fTD//MzLPcC71UJ3Hkj0KAaiCEeUx1RJ0FJW7hY5inzVhy3/H0Y58yECXFDNylCtwqx02vKVKmZlnqyQMqjbQuDJNOr7HK7/aq4JTnOaweU3Vry9d37GoWMeCoKUJq+Az6BK4rKxneSMoMSiS7lnf/ThFGJfQ24sw1ACWApqWkX9F2dfzilaSbxkGGDpaxmy4m4ZY/1ol4SN4vwC05USZlGEPkFACgW51piyduc9Lcp/gC54nIaDApT1lYoEtkVr2pFVSqHj7bhqB0ZlKpCg9jJsBZlLZjorqNvkFKg5Zp4TgSYpuxWWekE7t6+3payqrithReYjAUIwd3IYL/G+6xOh9KdRN1AAAAAElFTkSuQmCC";
		ByteArrayInputStream bytes = new ByteArrayInputStream(Base64.decodeBase64(code));
		FileOutputStream out = new FileOutputStream("result.jpg");
		IOUtils.copy(bytes, out);
		out.close();
	}
}
