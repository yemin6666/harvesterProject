package util;

public final class StringUtil {

	private StringUtil() {}
	
	public static boolean isEmpty(String... args){
		if(args==null)
			return true;
		for(String s : args){
			if(s==null||s.isEmpty())
				return true;
		}
		return false;
	}
}
