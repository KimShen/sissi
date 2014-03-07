package com.sissi.protocol.muc;

import java.sql.Date;
import java.text.ParseException;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.Trace;
import com.sissi.read.Metadata;
import com.sissi.ucenter.history.HistoryQuery;

/**
 * @author kim 2014年2月22日
 */
@Metadata(uri = XMuc.XMLNS, localName = XHistory.NAME)
public class XHistory implements HistoryQuery {

	private final static Log log = LogFactory.getLog(XHistory.class);

	public final static String NAME = "history";

	private String maxstanzas;

	private String seconds;

	private String since;

	public XHistory setMaxstanzas(String maxstanzas) {
		this.maxstanzas = maxstanzas;
		return this;
	}

	public XHistory setSeconds(String seconds) {
		this.seconds = seconds;
		return this;
	}

	public XHistory setSince(String since) {
		this.since = since;
		return this;
	}

	@Override
	public int limit(int limit, int def) {
		return this.maxstanzas != null ? Math.min(Integer.valueOf(this.maxstanzas), limit) : def;
	}

	@Override
	public long since(long limit, long def) {
		try {
			return Math.max(Math.max(this.since != null ? Date.class.cast(DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.parseObject(this.since)).getTime() : System.currentTimeMillis(), System.currentTimeMillis() - (this.seconds != null ? Long.valueOf(this.seconds) : Long.MIN_VALUE) * 1000), def);
		} catch (ParseException e) {
			log.debug(e.toString());
			Trace.trace(log, e);
			return def;
		}
	}
}
