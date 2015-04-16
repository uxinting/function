package expression.item.operator;

import java.util.Stack;

import expression.Item;
import expression.item.Operator;

public class Cos extends Operator {

	@Override
	public int level() {
		return Operator.LEVEL.LCOS;
	}

	@Override
	public double value(Stack<Item> args) {
		return Math.cos(args.pop().value(args));
	}

}
