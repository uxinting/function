package expression.item.operator;

import java.util.Stack;

import expression.Item;
import expression.item.Operator;

public class Div extends Operator {

	@Override
	public double value(Stack<Item> args) {
		double div2 = args.pop().value(args);
		double div1 = args.pop().value(args);
		if ( div2 == 0 ) return Double.NaN;
		return div1 / div2;
	}

	@Override
	public int level() {
		return Operator.LEVEL.LDIV;
	}

}
