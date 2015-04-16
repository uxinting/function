package expression.item.operator;

import java.util.Stack;

import expression.Item;
import expression.item.Operator;

public class Nagetive extends Operator {

	@Override
	public int level() {
		return Operator.LEVEL.LNAGETIVE;
	}

	@Override
	public double value(Stack<Item> args) {
		return -args.pop().value(args);
	}

}
