public class Externals{
	static final int MAIN_MEMORY_SIZE = 2500;
	static final int Reserve_size = 128;

	public static void invalidUsageExit(){
		System.err.println("USAGE: java Main <input-file> { noncontig | first | best | next | worst }");
		System.exit(1);
	}
	
	public static void outOfMemoryExit(){
		System.err.println("OUT OF MEMORY... exiting!");
		System.exit(1);
	}
	
	public static void malformedInput(String line){
		System.err.println("Malformed input file with line '" + line + "'");
		System.exit(1);
	}
	
	public static void inputTimeError(String line){
		System.err.println("Times not strictly increasing on line '" + line + "'");
		System.exit(1);
	}
}