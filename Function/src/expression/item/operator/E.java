package expression.item.operator;

import java.util.Stack;

import expression.Item;
import expression.item.Operator;

public class E extends Operator {

	@Override
	public int level() {
		return Operator.LEVEL.LE;
	}

	@Override
	public double value(Stack<Item> args) {
		return Math.E;
	}

}
