import org.apache.commons.codec.digest.DigestUtils;


public class Main {

	public static void main(String[] args) {
		String sid = "sid_10BAD9D3";
		String from = "a@sissi.pw/kim的MacBook Air";
		String to = "b@sissi.pw/";
		System.out.println(DigestUtils.sha1Hex(sid + from + to));
	}

}
