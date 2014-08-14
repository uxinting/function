package com.calc.function;

public class CurlMonitor {
	
	static private CurlMonitor monitor = null;
	
	static public enum Switch { ON, OFF };
	
	private Switch moveable = Switch.ON;
	
	private CurlMonitor() {
		setMoveable(Switch.ON);
	}
	
	static public CurlMonitor instance() {
		if ( monitor == null ) monitor = new CurlMonitor();
		return monitor;
	}

	public Switch getMoveable() {
		return moveable;
	}

	public void setMoveable(Switch moveable) {
		this.moveable = moveable;
	}
	
	public void toggleMoveable() {
		if ( getMoveable() == Switch.ON )
			setMoveable( Switch.OFF );
		else
			setMoveable( Switch.ON );
	}
	
	public void toggleRootResult() {
		
	}

}
