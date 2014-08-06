package expression.item.operator;

import java.util.Stack;

import expression.Item;
import expression.item.Operator;

public class Div extends Operator {

	@Override
	public double value(Stack<Item> args) {
		Item div2 = args.pop();
		Item div1 = args.pop();
		return div1.value( args ) / div2.value( args );
	}

	@Override
	public int level() {
		return Operator.LEVEL.LDIV;
	}

}
