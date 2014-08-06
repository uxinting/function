package expression.item.operator;

import java.util.Stack;

import expression.Item;
import expression.item.Operator;

public class Minus extends Operator {

	@Override
	public double value(Stack<Item> args) {
		Item minus2 = args.pop();
		Item minus1 = args.pop();
		return minus1.value( args ) - minus2.value( args );
	}

	@Override
	public int level() {
		return Operator.LEVEL.LMINUS;
	}

}
