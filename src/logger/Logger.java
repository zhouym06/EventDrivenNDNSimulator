package logger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	static final String logPath = "D:/log.txt";
	static PrintStream logStream = null;
	
	public static final int VERT_DETAIL = 0;
	public static final int DETAIL = 1;
	public static final int DEBUG = 2;
	public static final int INFO = 3;
	public static final int WARNING = 4;
	public static final int ERROR = 5;
	public static final int NONE = 6;
	
	public static int currentLevel = 0;
	
	public static void log(String s, int level)
	{
		try {
			if (logStream == null)
				logStream = new PrintStream(new FileOutputStream(logPath, true));
			log(logStream, s, level);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static void log(PrintStream out, String s, int level)
	{
		if(level < currentLevel)
			return;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	//设置日期格式
		out.print(df.format(new Date()));									//获取当前系统时间
		out.println(s);
	}
	static void clear()
	{
		try {
			if (logStream != null)
				logStream.close();
			logStream = new PrintStream(new FileOutputStream(logPath, false));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
