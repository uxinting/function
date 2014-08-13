package expression.item.operator;

import java.util.Stack;

import expression.Item;
import expression.item.Operator;

public class Angle extends Operator {

	@Override
	public int level() {
		return Operator.LEVEL.LANGLE;
	}

	@Override
	public double value(Stack<Item> args) {
		return Math.toRadians(args.pop().value(args));
	}

}
