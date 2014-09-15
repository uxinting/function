package expression.item.operator;

import java.util.Stack;

import expression.Item;
import expression.item.Operator;

public class Root extends Operator {
	
	private NP result = NP.POSITIVE;
	
	@Override
	public int level() {
		return Operator.LEVEL.LROOT;
	}

	@Override
	public double value(Stack<Item> args) {
		boolean nagBase = false;
		
		double base = args.pop().value(args);
		double ex = args.empty() ? 2 : args.pop().value(args);
		
		if ( base < 0 && ex % 2 != 0 ) {
			base = -base;
			nagBase = true;
		}
		double rs = Math.pow(base, (1.0/ex));
		if ( ex % 2 == 0 && NP.NAGETIVE == result || nagBase ) {
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
