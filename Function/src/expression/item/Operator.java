package expression.item;

import expression.Item;

abstract public class Operator implements Item {
	
	protected static class LEVEL {
		public final static int LLPARENT = -1;
		public final static int LRPARENT = -1;
		
		public final static int LADD = 0;
		public final static int LMINUS = 0;
		
		public final static int LMULTI = 1;
		public final static int LDIV = 1;
		
		public final static int LSIN = 2;
		public final static int LCOS = 2;
		public final static int LTAN = 2;
		public final static int LCOT = 2;
		public final static int LASIN = 2;
		public final static int LACOS = 2;
		public final static int LATAN = 2;
		public final static int LACOT = 2;
		
		public final static int LPOW = 3;
		public final static int LROOT = 3;
		public final static int LLN = 3;
		public final static int LLOG = 3;
		public final static int LEXP = 3;
		
		public final static int LNAGETIVE = 4;
		public final static int LANGLE = 4;
		public final static int LFACTORIAL = 4;
		
		public final static int LE = 5;
		public final static int LPI = 5;
		public final static int LX = 5;
		public final static int LY = 5;
	};
	
	protected String name = "";
	protected String description = "";
	protected int level = Integer.MIN_VALUE;
	
	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TYPE type() {
		return Item.TYPE.OPERAT;
	}
	
	public String toString() {
		return getName();
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public int level() {
		return getLevel();
	}
}
