package expression.item.operator;

import java.util.Stack;

import expression.Item;
import expression.item.Operator;

public class Ln extends Operator {

	@Override
	public int level() {
		return Operator.LEVEL.LLN;
	}

	@Override
	public double value(Stack<Item> args) {
		return Math.log(args.pop().value(args));
	}

}
