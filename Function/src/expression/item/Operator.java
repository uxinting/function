package expression.item;

import expression.Item;

abstract public class Operator implements Item {
	
	protected static class LEVEL {
		public final static int LADD = 0;
		public final static int LMINUS = 0;
		public final static int LMULTI = 1;
		public final static int LDIV = 1;
		public final static int LSIN = 2;
		public final static int LLPARENT = -1;
		public final static int LRPARENT = -1;
	};
	
	public TYPE type() {
		return Item.TYPE.OPERAT;
	}
}
