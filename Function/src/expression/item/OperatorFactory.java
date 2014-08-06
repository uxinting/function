package expression.item;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import android.content.Context;
import android.content.res.AssetManager;
import log.Log;
import expression.item.operator.Add;
import expression.item.operator.Div;
import expression.item.operator.LParent;
import expression.item.operator.Minus;
import expression.item.operator.Multi;
import expression.item.operator.RParent;
import expression.item.operator.UnkownOperator;


public class OperatorFactory {
	
	static private Log log = Log.instance();
	static private String operatorsName = "operators.xml";
	static private HashMap<String, Operator> operators = null;
	
	static public void initialize(AssetManager am) {
		if (operators!=null) return;
		operators = new HashMap<String, Operator>();
		
		try {
			Document doc = new SAXReader().read(am.open(operatorsName));
			for (Iterator<Element> i = doc.getRootElement().elementIterator(); i.hasNext();) {
				Element e = i.next();
				
				operators.put(e.attributeValue("name"), (Operator) Class.forName(e.getStringValue()).newInstance());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static public Operator getOperat( String operat ) {
		
		log.debug( "Operator : " + operat );
		Operator operator = operators.get(operat);
		if (operator == null)
			return new UnkownOperator( operat );
		else
			return operator;
		/*if ( operat.equals("+") ) {
			return new Add();
		} else if ( operat.equals("-") ) {
			return new Minus();
		} else if ( operat.equals("*") ) {
			return new Multi();
		} else if ( operat.equals("/") ) {
			return new Div();
		} else if ( operat.equals("(") ) {
			return new LParent();
		} else if ( operat.equals(")") ) {
			return new RParent();
		} else {
			return new UnkownOperator( operat );
		}*/
	}

}
