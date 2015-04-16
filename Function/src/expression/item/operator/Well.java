package expression.item.operator;

import java.util.Stack;

import expression.Item;
import expression.item.Operator;

public class Well extends Operator {

	@Override
	public int level() {
		return 0;
	}

	@Override
	public double value(Stack<Item> args) {
		return 0;
	}

}
