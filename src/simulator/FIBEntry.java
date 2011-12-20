package simulator;

import topology.Router;

public class FIBEntry {
	String prefix;
	Router nextRouter;
	public int fitLength(String uri)				//�ǰ׺ƥ��
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
				return l;							//��һ������ȵ��ַ�
		}
		return l+1;
	}
}
