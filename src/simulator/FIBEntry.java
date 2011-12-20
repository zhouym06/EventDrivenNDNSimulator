package simulator;

import topology.Router;

public class FIBEntry {
	String prefix;
	Router nextRouter;
	public int fitLength(String uri)				//最长前缀匹配
	{
		int l = 0;
		if(uri == null || prefix == null)
			return 0;
		int l1 = prefix.length();
		int l2 = uri.length();
		while(l <= l1 && l <=l2)
		{
			if(uri.charAt(l) == prefix.charAt(l))
				l++;
			else
				return l;							//第一个不相等的字符
		}
		return l+1;
	}
}
