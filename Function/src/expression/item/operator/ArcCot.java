package expression.item.operator;

import java.util.Stack;

import expression.Item;
import expression.item.Operator;

public class ArcCot extends Operator {

	@Override
	public int level() {
		return Operator.LEVEL.LACOT;
	}

	@Override
	public double value(Stack<Item> args) {
		double arg = args.pop().value(args);
		return Math.PI/2-Math.atan(arg);
	}

}
