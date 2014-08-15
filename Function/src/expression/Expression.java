package expression;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import expression.item.Operand;
import expression.item.OperandVariable;
import expression.item.OperatorFactory;
import expression.item.operator.Pow;
import expression.item.operator.Root;
import log.Log;

public class Expression {
	
	private String exp = null;
	private boolean bCompiled = false;
	
	private Queue<Item> inItems = new LinkedList<Item>();
	private Queue<Item> postItems = new LinkedList<Item>();
	private Set<String> operators = null;
	
	private String pattern = "";
	
	private Log log = Log.instance();
	
	private boolean prepare() {
		log.debug( "Prepare.." );
		inItems.clear();
		
		if ( Pattern.matches("["+pattern+"]+", exp) ) {
			Pattern p = Pattern.compile(pattern);
			Matcher matcher = p.matcher(exp);
			while ( matcher.find() ) {
				String e = matcher.group();
				try {
					inItems.add(new Operand(Double.valueOf(e)));
					log.debug("Operand : " + e);
				} catch (NumberFormatException e1) {
					inItems.add(OperatorFactory.getOperat(e));
					log.debug("Operator : " + e);
				}
			}
			return true;
		} else {
			log.warn("Illegal expression.");
			return false;
		}
	}
	
	/**
	 * 是否含偶数次根运算
	 * @return
	 */
	public boolean hasEvenRoot() {
		Pattern pat = Pattern.compile("(\\d*\\.?\\d+)√|\\^(\\d*\\.?\\d+)");
		Matcher matcher = pat.matcher(exp);
		while (matcher.find()) {
			String s = matcher.group();
			
			if (s.charAt(s.length()-1) == '√' &&
				Double.valueOf(s.substring(0, s.length()-1))%2 == 0)
				return true;
			
			//倒数是2的倍数
			if (s.charAt(0) == '^') {
				double e = Double.valueOf(s.substring(1));
				if (e < 1 && (1.0/e)%2 == 0)
				return true;
			}
		}
		return false;
	}
	
	public void toggleRootResult() {
		((Root) OperatorFactory.getOperat("√")).toggleResult();
		((Pow) OperatorFactory.getOperat("^")).toggleResult();
	}

	public Expression(String exp) {
		this.exp = exp;
		
		operators = OperatorFactory.getOperators().keySet();
		
		if ( operators != null ) {
			pattern = "(\\d*\\.?\\d+)";
			for ( String op : operators ) {
				if ( op.equals("*") 
					 || op.equals("+") 
					 || op.equals("-")
					 || op.equals("(")
					 || op.equals(")")
					 || op.equals("^"))
					op = "\\"+op;
				pattern += "|"+op;
			}
			log.debug(pattern);
		}
	}
	
	public boolean compile() {
		bCompiled = false;
		postItems.clear();
		
		if ( !prepare() ) {
			log.warn("Prepare fail.");
		}
		
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
