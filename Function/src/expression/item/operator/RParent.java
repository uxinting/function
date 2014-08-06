package expression.item.operator;

import java.util.Stack;

import expression.Item;
import expression.item.Operator;

public class RParent extends Operator {

	@Override
	public int level() {
		return Operator.LEVEL.LRPARENT;
	}

	@Override
	public double value(Stack<Item> args) {
		return 0;
	}
	
	public TYPE type() {
		return Item.TYPE.RPARENT;
	}

}
