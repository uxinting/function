package expression.item.operator;

import java.util.Stack;

import expression.Item;
import expression.item.Operator;

public class ArcSin extends Operator {

	@Override
	public int level() {
		return Operator.LEVEL.LASIN;
	}

	@Override
	public double value(Stack<Item> args) {
		return Math.asin(args.pop().value(args));
	}

}
