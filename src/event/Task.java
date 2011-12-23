package event;

import logger.Logger;

public class Task {
	double time;
	public Task(double time)
	{
		this.time = time;
	}
	public void execute()
	{	
		Logger.log(toString(), Logger.ERROR);
	}
	public void display() {
		Logger.log(System.out, String.valueOf(time), 3);
	}
	public double getTime()
	{
		return time;
	}
	public String toString()
	{
		return time + "Unkown Task!";
	}
}
