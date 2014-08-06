package log;

public class Log {
	
	private static Log log = null;
	
	private Log() {
		
	}
	
	static public Log instance() {
		if ( null == log ) {
			log = new Log();
		}
		return log;
	}
	
	public void debug(String... args) {
		System.out.print( "[DEBUG:] " );
		print( args );
	}
	
	public void info(String... args) {
		System.out.print( "[INFO:] " );
		print( args );
	}
	
	public void warn(String... args) {
		System.out.print( "[WARN:] " );
		print( args );
	}
	
	public void error(String... args) {
		System.out.print( "[ERROR:] " );
		print( args );
	}
	
	private void print(String... args) {
		for ( String s : args ) {
			System.out.print( s );
		}
		System.out.println();
	}
}
