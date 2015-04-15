package log;

public class Log {
	
	private static Log log = null;
	private int  level   = LEVEL.DEBUG;
	
	private Log() {
		
	}
	
	static public Log instance() {
		if ( null == log ) {
			log = new Log();
		}
		return log;
	}
	
	public void debug(String... args) {
		if ( level > LEVEL.DEBUG ) return;
		System.out.print( "[DEBUG:] " );
		print( args );
	}
	
	public void info(String... args) {
		if ( level > LEVEL.DEBUG ) return;
		System.out.print( "[INFO:] " );
		print( args );
	}
	
	public void warn(String... args) {
		if ( level > LEVEL.WARN ) return;
		System.out.print( "[WARN:] " );
		print( args );
	}
	
	public void error(String... args) {
		if ( level > LEVEL.ERROR ) return;
		System.out.print( "[ERROR:] " );
		print( args );
	}
	
	private void print(String... args) {
		for ( String s : args ) {
			System.out.print( s );
		}
		System.out.println();
	}
	
	public static class LEVEL {
		final public static int DEBUG = 0;
		final public static int INFO  = 1;
		final public static int WARN  = 2;
		final public static int ERROR = 3;
		final public static int FATAL = 4;
	}
}
