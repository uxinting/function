package expression.item;

import java.util.Stack;

import log.Log;
import expression.Item;

public class Operand implements Item {
	
	private Log log = Log.instance();
	
	private double data = 0;

	public Operand( String operan ) {
		data = Double.valueOf( operan );
		log.debug( "Operand : " + operan );
	}
	
	public Operand( double operan ) {
		data = operan;
	}
	
	public TYPE type() {
		return Item.TYPE.OPERAN;
	}

	public double value(Stack<Item> args) {
		return data;
	}

	@Override
	public int level() {
		return 0;
	}
	
	public String toString () {
		return String.valueOf( data );
	}
}
