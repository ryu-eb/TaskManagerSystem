package jp.eightbit.exam.service;

public class VoidUtil {
	public static String genName(String username) {
		String tail = null;
		if (username.length() > 5) {
			tail = username.substring(0, 5) + "." + randomize(username);
		}else {
			tail = username;
		}
		return "VOID_" + tail;
	}
	
	private static String randomize(String nm) {
		int hash = nm.hashCode();
		
		return "_" + Integer.bitCount(hash);
		
	}
}
