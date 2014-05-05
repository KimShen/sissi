package com.sissi.protocol.offline;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.Trace;
import com.sissi.io.read.Metadata;
import com.sissi.persistent.RecoverDirection;
import com.sissi.persistent.RecoverQuery;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.muc.XMuc;

/**
 * @author kim 2014年2月22日
 */
@Metadata(uri = { Message.XMLNS, XMuc.XMLNS }, localName = History.NAME)
public class History implements RecoverQuery {

	private final static Log log = LogFactory.getLog(History.class);

	private final static String format = "yyyy-MM-dd'T'HH:mm:ssZ";

	public final static String NAME = "history";

	private String maxstanzas;

	private String direction;

	private String seconds;

	private String since;

	public History setMaxstanzas(String maxstanzas) {
		this.maxstanzas = maxstanzas;
		return this;
	}

	public History setDirection(String direction) {
		this.direction = direction;
		return this;
	}

	public History setSeconds(String seconds) {
		this.seconds = seconds;
		return this;
	}

	public History setSince(String since) {
		this.since = since;
		return this;
	}

	@Override
	public int limit(int limit, int def) {
		return this.maxstanzas != null ? Math.min(Integer.valueOf(this.maxstanzas), limit) : def;
	}

	/*
	 * 1,Seconds 2,Since 3,Def
	 * 
	 * @see com.sissi.persistent.RecoverQuery#since(long, long)
	 */
	@Override
	public long since(long limit, long def) {
		try {
			return this.seconds != null ? (System.currentTimeMillis() - Long.valueOf(this.seconds)) : this.since != null ? new SimpleDateFormat(format).parse(this.since).getTime() : def;
		} catch (ParseException e) {
			log.debug(e.toString());
			Trace.trace(log, e);
			return def;
		}
	}

	public boolean direction(RecoverDirection direction) {
		return RecoverDirection.parse(this.direction) == direction;
	}
}
