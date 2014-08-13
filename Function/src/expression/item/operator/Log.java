package expression.item.operator;

import java.util.Stack;

import expression.Item;
import expression.item.Operator;

public class Log extends Operator {

	@Override
	public int level() {
		return Operator.LEVEL.LLOG;
	}

	@Override
	public double value(Stack<Item> args) {
		return Math.log10(args.pop().value(args));
	}

}
