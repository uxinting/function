package expression.item.operator;

import java.util.Stack;

import expression.Item;
import expression.item.Operator;

public class Multi extends Operator {

	@Override
	public double value(Stack<Item> args) {
		Item multi2 = args.pop();
		Item multi1 = args.pop();
		return multi1.value( args ) * multi2.value( args );
	}

	@Override
	public int level() {
		return Operator.LEVEL.LMULTI;
	}

}
