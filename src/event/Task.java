package event;

import logger.Logger;

public class Task {
	TimeLine timeline;
	double time;
	public Task(double time, TimeLine timeline)
	{
		this.time = time;
		this.timeline = timeline;
	}
	public void execute()
	{	
	}
	public void display() {
		Logger.log(System.out, String.valueOf(time), 3);
	}
	public TimeLine getTimeLine()
	{
		return timeline;
	}
	public double getTime()
	{
		return time;
	}
}
