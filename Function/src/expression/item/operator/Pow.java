package expression.item.operator;

import java.util.Stack;

import expression.Item;
import expression.item.Operator;

public class Pow extends Operator {

	@Override
	public int level() {
		return Operator.LEVEL.LPOW;
	}

	@Override
	public double value(Stack<Item> args) {
		double arg2 = args.pop().value(args);
		double arg1 = args.pop().value(args);
		return Math.pow(arg1, arg2);
	}

}
