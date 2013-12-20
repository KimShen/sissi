import org.apache.commons.codec.digest.DigestUtils;

public class Test {

	public static void main(String[] args) {
		String sid = "sid_7A48EB18";
		String ijid = "b@3ti.us";
		String tjid = "proxy.127.0.0.1";
//		48d9dd25d0b4a005784dc3e2453490282647240f,0
//		1a741f24b1493ebabe524d084c14c0a8ec9a5472,0
		System.out.println(DigestUtils.sha1Hex(sid + ijid + tjid));
	}
}
