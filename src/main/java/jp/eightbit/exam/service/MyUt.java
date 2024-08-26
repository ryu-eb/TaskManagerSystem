package jp.eightbit.exam.service;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyUt {
	public static void print(String str) {
		System.out.println(str);
	}
	
	public static String dateToString(Date date) {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		return dateformat.format(date);
	}
}
