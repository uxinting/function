package expression;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import expression.item.Operand;
import expression.item.OperandVariable;
import expression.item.OperatorFactory;
import log.Log;

public class Expression {
	
	private String exp = null;
	private boolean bCompiled = false;
	private Queue<Item> inItems = new LinkedList<Item>();
	private Queue<Item> postItems = new LinkedList<Item>();
	
	private Log log = Log.instance();
	
	private boolean isNumber( char c ) {
		return '0' <= c && c <= '9';
	}
	
	public Expression(String exp) {
		this.exp = exp;
	}
	
	public boolean isFunc() {
		return exp.contains( "x" );
	}
	
	public boolean compile() {
		bCompiled = false;
		
		prepare();
		
		Stack<Item> operators = new Stack<Item>();
		for ( Item item : inItems ) {
			if ( null == item ) break;
			switch ( item.type() ) {
			case OPERAN:
			case VARIABLE:
				postItems.add( item );
				break;
			case OPERAT:
			{
				while ( !operators.empty() && item.level() <= operators.peek().level() ) {
					postItems.add( operators.pop() );
				}
				operators.push( item );
				break;
			}	
			case LPARENT:
				operators.push( item );
				break;
			case RPARENT:
			{
				while ( !operators.empty() && operators.peek().type() != Item.TYPE.LPARENT ) {
					postItems.add( operators.pop() );
				}
				operators.pop();
				break;
			}
			case NONE:
			default:
				log.error( "Unkown item ", item.toString() );
				return false;
			}
		}
		while ( !operators.empty() ) {
			postItems.add( operators.pop() );
		}
		
		bCompiled = true;
		return true;
	}
	
	public boolean prepare() {
		log.debug( "Prepare.." );
		inItems.clear();
		
		int cur = 0;
		while ( cur < exp.length() ) {
			
			if ( exp.charAt( cur ) == 'x' ) {
				inItems.add( new OperandVariable() );
				cur++; continue;
			}
			
			if ( exp.charAt( cur ) == '(' ) {
				inItems.add( OperatorFactory.getOperat( "(" ) );
				cur++; continue;
			}
			
			//Number
			String item = "";
			for ( ; cur < exp.length(); cur++ ) {
				char c = exp.charAt(cur);
				if ( !isNumber( c ) ) break;
				item += c;
			}
			log.debug( "Get " + item );
			if ( !item.isEmpty() )
				inItems.add( new Operand( item ) );
			
			//Operator
			item = "";
			for ( ; cur < exp.length(); cur++ ) {
				char c = exp.charAt( cur );
				if ( c > '0' && c < '9' ) break;
				if ( c == '(' || c == 'x' ) { break; }
				item += c;
				if ( c == ')' ) { cur++; break; }
			}
			if ( !item.isEmpty() )
				inItems.add( OperatorFactory.getOperat( item ) );
		}
		return true;
	}
	
	public Double result() {
		if ( !bCompiled ) {
			log.warn( "You didn't compile yet." );
			return 0.0;
		}
		Stack<Item> rss = new Stack<Item>();

		for ( Item item : postItems ) {
			if ( item.type() == Item.TYPE.OPERAN ) {
				rss.push( item );
			} else {
				rss.push( new Operand( item.value( rss ) ) );
			}
		}
		return rss.pop().value(null);
	}

	public Double result( double x ) {
		if ( !bCompiled ) {
			log.warn( "You didn't compile yet." );
			return 0.0;
		}
		Stack<Item> rss = new Stack<Item>();

		for ( Item item : postItems ) {
			if ( item.type() == Item.TYPE.OPERAN ) {
				rss.push( item );
			} else if ( item.type() == Item.TYPE.VARIABLE ) {
				rss.push( new Operand( x ) );
				rss.push( item );
			} else {
				rss.push( new Operand( item.value( rss ) ) );
			}
		}
		return rss.pop().value(rss);
	}
}
