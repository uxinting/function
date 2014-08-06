package expression.item.operator;

import java.util.Stack;

import expression.Item;
import expression.item.Operator;

public class Add extends Operator {
	
	public double value(Stack<Item> args) {
		Item add2 = args.pop();
		Item add1 = args.pop();
		return add1.value( args ) + add2.value( args );
	}

	@Override
	public int level() {
		return Operator.LEVEL.LADD;
	}

}
