package event;

import logger.Logger;

public class Task {
	public double time;
	public void execute()
	{
		
	}
	public void display() {
		Logger.log(System.out, String.valueOf(time), 3);
		
	}
	
}
