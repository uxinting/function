package expression.item.operator;

import java.util.Stack;

import expression.Item;
import expression.item.Operator;

public class UnkownOperator extends Operator {
	
	private String operator;
	public UnkownOperator( String operator ) {
		this.operator = operator;
	}

	@Override
	public int level() {
		return 0;
	}

	@Override
	public double value(Stack<Item> args) {
		return 0;
	}
	
	public TYPE type() {
		return Item.TYPE.NONE;
	}
	
	public String toString() {
		return operator;
	}

}
