package expression.item.operator;

import java.util.Stack;

import expression.Item;
import expression.item.Operator;

public class Cot extends Operator {

	@Override
	public int level() {
		return Operator.LEVEL.LCOT;
	}

	@Override
	public double value(Stack<Item> args) {
		double arg = Math.tan(args.pop().value(args));
		if ( arg == 0 ) return Double.NaN;
		return 1.0 / arg;
	}

}
