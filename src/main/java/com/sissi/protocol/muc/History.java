package com.sissi.protocol.muc;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.Trace;
import com.sissi.read.Metadata;
import com.sissi.ucenter.history.HistoryQuery;

/**
 * @author kim 2014年2月22日
 */
@Metadata(uri = XMuc.XMLNS, localName = History.NAME)
public class History implements HistoryQuery {

	private final static Log log = LogFactory.getLog(History.class);

	private final static String format = "yyyy-MM-dd'T'HH:mm:ss'Z'";

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

	@Override
	public long since(long limit, long def) {
		try {
			return Math.max(Math.max(this.since != null ? new SimpleDateFormat(format).parse(this.since).getTime() : System.currentTimeMillis(), System.currentTimeMillis() - (this.seconds != null ? Long.valueOf(this.seconds) : Long.MIN_VALUE) * 1000), def);
		} catch (ParseException e) {
			log.debug(e.toString());
			Trace.trace(log, e);
			return def;
		}
	}

	public boolean direction(HistoryDirection direction) {
		return HistoryDirection.parse(this.direction) == direction;
	}

	public enum HistoryDirection {

		UP, DOWN;

		public static HistoryDirection parse(String type) {
			try {
				return type == null ? DOWN : HistoryDirection.valueOf(type.toUpperCase());
			} catch (Exception e) {
				return DOWN;
			}
		}
	}
}
