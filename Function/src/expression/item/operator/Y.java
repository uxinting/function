package expression.item.operator;

import java.util.Stack;

import expression.Item;
import expression.item.Operator;

public class Y extends Operator {

	@Override
	public int level() {
		return Operator.LEVEL.LY;
	}

	@Override
	public double value(Stack<Item> args) {
		return args.pop().value(args);
	}

	@Override
	public TYPE type() {
		return Item.TYPE.VARIABLE;
	}

}
