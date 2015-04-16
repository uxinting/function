package expression.item.operator;

import java.util.Stack;

import expression.Item;
import expression.item.Operator;

public class Minus extends Operator {

	@Override
	public double value(Stack<Item> args) {
		double minus2 = args.pop().value(args);
		double minus1 = args.pop().value(args);
		return minus1 - minus2;
	}

	@Override
	public int level() {
		return Operator.LEVEL.LMINUS;
	}

}
