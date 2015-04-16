package expression.item.operator;

import java.util.Stack;

import expression.Item;
import expression.item.Operator;

public class ArcCos extends Operator {

	@Override
	public int level() {
		return Operator.LEVEL.LACOS;
	}

	@Override
	public double value(Stack<Item> args) {
		return Math.acos(args.pop().value(args));
	}

}
