package event;

import logger.Logger;

public class Task {
	public TimeLine timeline;
	public double time;
	public Task(double time, TimeLine timeline)
	{
		this.time = time;
		this.timeline = timeline;
	}
	public void execute(TimeLine timeLine)
	{	
	}
	public void display() {
		Logger.log(System.out, String.valueOf(time), 3);
	}
	
}
