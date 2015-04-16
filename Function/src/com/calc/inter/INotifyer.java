package com.calc.inter;

public interface INotifyer {
	
	static enum Event {
		ONDRAW
	}
	
	void register( Event e, IResponser responser );
}
