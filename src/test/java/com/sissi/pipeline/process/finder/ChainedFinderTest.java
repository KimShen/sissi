package com.sissi.pipeline.process.finder;

import junit.framework.Assert;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.sissi.pipeline.process.ChainedFinder;
import com.sissi.pipeline.process.ChainedFinder.Condition;
import com.sissi.pipeline.process.ClassMatcher;
import com.sissi.pipeline.process.iq.bind.BindProcessor;
import com.sissi.pipeline.process.stream.StreamProcessor;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.bind.Bind;
import com.sissi.protocol.stream.Stream;

/**
 * @author kim 2013-11-12
 */
public class ChainedFinderTest {

	@Test
	public void testFind() {
		StreamProcessor stream = new StreamProcessor();
		Condition processor1 = new Condition(new ClassMatcher(Stream.class), stream);
		BindProcessor bind = new BindProcessor(null, null);
		Condition processor2 = new Condition(new ClassMatcher(Bind.class), bind);
		ChainedFinder finder = new ChainedFinder(Lists.asList(processor1, new Condition[] { processor2 }));
		Assert.assertSame(stream, finder.find(new Stream()));
		Assert.assertSame(bind, finder.find(new Bind()));
		Assert.assertSame(null, finder.find(new IQ()));
	}
}
