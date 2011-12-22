package util;

import java.util.Random;

import logger.Logger;

public class MyRandom {
	static Random random = null;
	public static double nextDouble()
	{
		if(random == null)
			random = new Random(System.currentTimeMillis());
		return random.nextDouble();
	}
	
	public static double nextPoisson(double lamda) {
		double x = 0, b = 1, c = Math.exp(-lamda), u;
		do {
			u = nextDouble();
			b *= u;
			if (b >= c)
				x++;
		} while (b >= c);
		// Logger.log("MyRandom:nextPoisson(" + lamda + "):" + x, Logger.VERY_DETAIL);
		if (x == 0)
			return 0.01;
		return x;
	}

	public static double nextExp(double lamda) {
		double z = nextDouble();
		double x = -(1 / lamda) * Math.log(z);
		//Logger.log("MyRandom:nextExp(" + lamda + "):" + x, Logger.VERY_DETAIL);
		return x;
	}
}
