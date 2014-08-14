package expression.item;

import java.io.File;
import java.io.InputStream;
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
	static private HashMap<String, Operator> operators = null;
	
	static public void initialize(InputStream xml) {
		if (operators!=null) return;
		operators = new HashMap<String, Operator>();
		
		try {
			Document doc = new SAXReader().read(xml);
			for (Iterator<Element> i = doc.getRootElement().elementIterator(); i.hasNext();) {
				Element e = i.next();
				log.debug(e.getStringValue());
				log.debug(e.attributeValue("name"));
				log.debug(e.attributeValue("description"));
				
				Operator op = (Operator) Class.forName(e.getStringValue()).newInstance();
				op.setName(e.attributeValue("name"));
				op.setDescription(e.attributeValue("description"));
				try {
					int level = Integer.valueOf(e.attributeValue("level"));
					op.setLevel(level);
				} catch (Exception e1) {
					log.warn(e1.toString());
				}
				
				operators.put(op.getName(), op);
			}
		} catch (Exception e) {
			log.warn(e.toString());
		}
	}
	
	static public Operator getOperat( String operat ) {
		
		log.debug( "Operator : " + operat );
		
		operat = operat.trim();
		
		Operator operator = operators.get(operat);
		if (operator == null)
			return new UnkownOperator( operat );
		else
			return operator;
	}

	public static HashMap<String, Operator> getOperators() {
		return operators;
	}
	
}
