package event;

import java.util.LinkedList;

import logger.Logger;

public class Requests {
	public LinkedList<Task> tasks = null;
	public Requests()
	{
		tasks = new LinkedList<Task>();
	}
	public void add(Task newTask)
	{
		if(tasks == null)
		{
			tasks = new LinkedList<Task>();
			tasks.add(newTask);
			Logger.log("TimeLine:" + newTask.toString() + " added()", Logger.DEBUG - 1);
			return;
		}
		if(tasks.size() == 0)
		{
			tasks.add(newTask);
			Logger.log("TimeLine:" + newTask.toString() + " added()", Logger.DEBUG - 1);
			return;
		}
		int prev = 0;
		for(Task t:tasks)
		{
			if(t.time > newTask.time)
			{
				tasks.add(prev, newTask);
				Logger.log("TimeLine:" + newTask.toString() + " added()", Logger.DEBUG - 1);
				return;
			}
			else
				prev++;
		}
		tasks.addLast(newTask);
		Logger.log("TimeLine:" + newTask.toString() + " added()", Logger.DEBUG - 1);
		
	}
	
}
