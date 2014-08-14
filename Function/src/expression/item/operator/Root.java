package expression.item.operator;

import java.util.Stack;

import expression.Item;
import expression.item.Operator;

public class Root extends Operator {

	@Override
	public int level() {
		return Operator.LEVEL.LROOT;
	}

	@Override
	public double value(Stack<Item> args) {
		double b = args.pop().value(args);
		double ex = args.pop().value(args);
		return Math.pow(b, (1.0/ex));
	}

}
