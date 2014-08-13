package expression.item.operator;

import java.util.Stack;

import expression.Item;
import expression.item.Operator;

public class Exp extends Operator {

	@Override
	public int level() {
		return Operator.LEVEL.LEXP;
	}

	@Override
	public double value(Stack<Item> args) {
		return Math.exp(args.pop().value(args));
	}

}
