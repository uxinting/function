package test;

import static org.junit.Assert.*;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import android.test.AndroidTestCase;
import expression.Expression;
import expression.item.OperatorFactory;

public class TestCases extends AndroidTestCase {

	@Test
	public void test() {
		Expression expr = new Expression("c");
		assert( expr.compile() == false );
	}
	
	@Test
	public void testOperatorFactoryInitialize() {
	}
	
	@Test
	public void testDom4jxml() {
		SAXReader reader = new SAXReader();
		File f = new File("assets/operators.xml");
		try {
			Document doc = reader.read(new File("assets/operators.xml"));
			Element root = doc.getRootElement();
			System.out.println(root.getName());
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

}
