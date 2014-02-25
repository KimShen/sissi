package com.sissi.pipeline.out;

import java.util.ArrayList;
import java.util.List;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Output;
import com.sissi.pipeline.OutputBuilder;
import com.sissi.protocol.Element;
import com.sissi.write.Transfer;

/**
 * @author kim 2013年12月6日
 */
public class ChainedOutputBuilder implements OutputBuilder {

	private final List<OutputBuilder> builders;

	public ChainedOutputBuilder(List<OutputBuilder> builders) {
		super();
		this.builders = builders;
	}

	@Override
	public Output build(Transfer transfer) {
		ChainedOutput output = new ChainedOutput();
		for (OutputBuilder builder : this.builders) {
			output.add(builder.build(transfer));
		}
		return output;
	}

	private class ChainedOutput implements Output {

		private List<Output> outputs;

		public ChainedOutput() {
			super();
		}

		public ChainedOutput add(Output output) {
			if (this.outputs == null) {
				this.outputs = new ArrayList<Output>();
			}
			this.outputs.add(output);
			return this;
		}

		@Override
		public boolean output(JIDContext context, Element node) {
			for (Output output : this.outputs) {
				boolean canNext = output.output(context, node);
				if (!canNext) {
					return canNext;
				}
			}
			return true;
		}

		@Override
		public Output close() {
			for (Output output : this.outputs) {
				output.close();
			}
			return this;
		}
	}
}
