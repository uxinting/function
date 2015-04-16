package expression;

import java.util.Stack;

public interface Item {
	
	public enum TYPE { VARIABLE, OPERAN, OPERAT, LPARENT, RPARENT, NONE };
	
	int level();
	TYPE type();
	double value( Stack<Item> args );
	
	String toString();
}
