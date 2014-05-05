package com.sissi.pipeline.in.iq.disco;

import java.util.Set;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.CheckRelationProcessor;
import com.sissi.protocol.Protocol;

/**
 * 如果非服务器域或特定域则关系校验
 * 
 * @author kim 2014年1月26日
 */
public class DiscoCheckRelationProcessor extends CheckRelationProcessor {

	private final Set<String> domains;

	/**
	 * @param domains 需要忽略的域
	 * @param free
	 */
	public DiscoCheckRelationProcessor(Set<String> domains, boolean shortcut) {
		super(shortcut);
		this.domains = domains;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return !protocol.to() || protocol.to(this.domains) || super.ourRelation(context, protocol) ? true : super.writeAndReturn(context, protocol);
	}
}
