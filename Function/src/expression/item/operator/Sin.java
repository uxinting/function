package expression.item.operator;

import java.util.Stack;

import org.w3c.dom.ls.LSInput;

import expression.Item;
import expression.item.Operator;

public class Sin extends Operator {

	@Override
	public int level() {
		return Operator.LEVEL.LSIN;
	}

	@Override
	public double value(Stack<Item> args) {
		double arg = args.pop().value(args);
		return Math.sin(arg);
	}

}
