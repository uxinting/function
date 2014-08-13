package expression.item.operator;

import java.util.Stack;

import expression.Item;
import expression.item.Operator;

public class Factorial extends Operator {

	@Override
	public int level() {
		return Operator.LEVEL.LFACTORIAL;
	}

	@Override
	public double value(Stack<Item> args) {
		double n = args.pop().value(args);
		double rs = 1;
		for ( int i = 0; i < n; i++ ) {
			rs *= i;
		}
		return rs;
	}

}
