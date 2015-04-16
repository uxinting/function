package expression.item.operator;

import java.util.Stack;

import expression.Item;
import expression.item.Operator;
import expression.item.operator.Root.NP;

public class Pow extends Operator {

	private NP result = NP.POSITIVE;
	
	@Override
	public int level() {
		return Operator.LEVEL.LPOW;
	}

	@Override
	public double value(Stack<Item> args) {
		double e = args.pop().value(args);
		double base = args.pop().value(args);
		double rs = Math.pow(base, e);
		if ( result == NP.NAGETIVE && e < 1 && (1.0/e)%2 == 0 ) {
			rs = -rs;
		}
		return rs;
	}
	
	/**
	 * 结果正负调换
	 */
	public void toggleResult() {
		if ( result == NP.POSITIVE ) {
			result = NP.NAGETIVE;
		} else {
			result = NP.POSITIVE;
		}
	}

	public enum NP { POSITIVE, NAGETIVE };
}
