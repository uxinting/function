package expression.item;

import java.util.Stack;

import expression.Item;

public class OperandVariable implements Item {

	@Override
	public int level() {
		return 0;
	}

	@Override
	public TYPE type() {
		return Item.TYPE.VARIABLE;
	}

	@Override
	public double value(Stack<Item> args) {
		return args.pop().value( args );
	}

}
