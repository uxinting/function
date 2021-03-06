package expression.item.operator;

import java.util.Stack;

import expression.Item;
import expression.item.Operator;

public class LParent extends Operator {

	@Override
	public int level() {
		return Operator.LEVEL.LLPARENT;
	}

	@Override
	public double value(Stack<Item> args) {
		return 0;
	}
	
	public TYPE type() {
		return Item.TYPE.LPARENT;
	}

}
