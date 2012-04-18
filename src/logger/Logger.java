package logger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import config.Config;

public class Logger {
	static final String logPath = Config.logPath;
	static PrintStream logStream = null;
	
	public static final int VERY_DETAIL = 0;
	public static final int DETAIL = 10;
	public static final int ROUTER = 15;
	public static final int DEBUG = 20;
	public static final int INFO = 30;
	public static final int WARNING = 40;
	public static final int ERROR = 50;
	public static final int NONE = 60;
	
	//public static final String[] levelName = {"VDETAIL", "DETAIL", "DEBUG", "INFO", "WARNING", "ERROR", "NONE"};
	public static int currentLevel = 20;
	
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
		out.print("\t" + levelName(level) + "\t");
		for(int i = VERY_DETAIL; level + i < DEBUG; i++)
			out.print("\t");
		out.println(s);
	}
	public static void clear()
	{
		try {
			if (logStream != null)
				logStream.close();
			logStream = new PrintStream(new FileOutputStream(logPath, false));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static String levelName(int level)
	{
		switch(level)
		{
		case VERY_DETAIL:
			return "VERY_DETAIL";
		case DETAIL:
			return "DETAIL";
		case ROUTER:
			return "ROUTER";
		case DEBUG:
			return "DEBUG";
		case INFO:
			return "INFO";
		case ERROR:
			return "ERROR";
		case NONE:
			return "NONE";
		default:
			return "UNKNOWN LEVEL:"+level;
		}
	}

	public static void setFile(String string) {
		try {
			String logPath =  Config.logRoot + string;
			logStream = new PrintStream(new FileOutputStream(logPath, true));
			log(logPath, INFO);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
