package expression.item.operator;

import java.util.Stack;

import expression.Item;
import expression.item.Operator;

public class Tan extends Operator {

	@Override
	public int level() {
		return Operator.LEVEL.LTAN;
	}

	@Override
	public double value(Stack<Item> args) {
		return Math.tan(args.pop().value(args));
	}

}
