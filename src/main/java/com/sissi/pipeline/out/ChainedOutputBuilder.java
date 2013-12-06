package com.sissi.pipeline.out;

import java.util.ArrayList;
import java.util.List;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Output;
import com.sissi.pipeline.Output.OutputBuilder;
import com.sissi.protocol.Element;
import com.sissi.write.Writer.Transfer;

/**
 * @author kim 2013年12月6日
 */
public class ChainedOutputBuilder implements OutputBuilder {

	private List<OutputBuilder> builders;

	public ChainedOutputBuilder(List<OutputBuilder> builders) {
		super();
		this.builders = builders;
	}

	@Override
	public Output build(Transfer writeable) {
		ChainedOutput output = new ChainedOutput();
		for (OutputBuilder builder : this.builders) {
			output.add(builder.build(writeable));
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
		public Boolean output(JIDContext context, Element node) {
			for (Output output : this.outputs) {
				Boolean canNext = output.output(context, node);
				if (!canNext) {
					return canNext;
				}
			}
			return true;
		}

		@Override
		public void close() {
			for (Output output : this.outputs) {
				output.close();
			}
		}
	}
}
