package util;

import logger.Logger;

public class URI {
	public static int getContentNo(String uri)
	{
		String[] strs = uri.split("-");
		if(strs.length == 3)
		{
			return Integer.valueOf(strs[1]);
		}
		else if(strs.length == 2)
		{
			return Integer.valueOf(strs[1]);
		}
		else
		{
			Logger.log("URI:ContentName fomat error", Logger.ERROR);
			return -1;
		}
	}
	public static String getContentPrefix(String uri)
	{
		String[] strs = uri.split("-");
		if(strs.length == 3)
		{
			return strs[0] + "-" + strs[1];
		}
		else if(strs.length == 2)
		{
			return uri;
		}
		else
		{
			Logger.log("URI:ContentName fomat error", Logger.ERROR);
			return null;
		}
	}
	public static boolean checkStr(String uri)
	{
		String[] strs = uri.split("-");
		if(strs.length != 3 && strs.length != 2)
			return false;
		return true;
	}

}
