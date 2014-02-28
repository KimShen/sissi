package com.threeti.relation;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.ObjectMapper;

import com.mongodb.BasicDBObject;
import com.sissi.config.MongoConfig;
import com.sissi.ucenter.user.AuthAccessor;

/**
 * @author kim 2014年1月3日
 */
public class RestAuthAccessor implements AuthAccessor {

	private URL url;

	private String params;

	private MongoConfig config;

	public RestAuthAccessor(URL url, String params, MongoConfig config) {
		super();
		this.url = url;
		this.params = params;
		this.config = config;
	}

	@Override
	public String access(String username) {
		try {
			HttpURLConnection huc = (HttpURLConnection) this.url.openConnection();
			huc.setDoOutput(true);
			huc.setRequestMethod("POST");
			DataOutputStream out = new DataOutputStream(huc.getOutputStream());
			out.writeBytes(this.params + "&username=" + username);
			out.flush();
			BufferedInputStream in = new BufferedInputStream(huc.getInputStream());
			String password = new ObjectMapper().readValue(in, Response.class).getResult().getUserpswd();
			if (password != null) {
				BasicDBObject user = new BasicDBObject("username", username);
				this.config.collection().update(user, user, true, false);
			}
			return password;
		} catch (Exception e) {

		}
		return null;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Response {

		private Result result;

		public Result getResult() {
			return result;
		}

		public void setResult(Result result) {
			this.result = result;
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Result {

		private String userpswd;

		public String getUserpswd() {
			return userpswd;
		}

		public void setUserpswd(String userpswd) {
			this.userpswd = userpswd;
		}
	}
}
