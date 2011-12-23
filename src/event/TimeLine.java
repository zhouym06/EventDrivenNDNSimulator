package event;

import java.util.LinkedList;

import logger.Logger;

public class TimeLine {
	private static LinkedList<Task> tasks = null;
	private TimeLine()
	{
		tasks = new LinkedList<Task>();
	}
	public static void clear() {
		if(tasks == null)
		{
			tasks = new LinkedList<Task>();
			return;
		}
		tasks.clear();
	}
	
	public static void add(Task newTask)
	{
		if(tasks == null)
		{
			tasks = new LinkedList<Task>();
			tasks.add(newTask);
			Logger.log("TimeLine:" + newTask.toString() + " added()", Logger.DEBUG);
			return;
		}
		if(tasks.size() == 0)
		{
			tasks.add(newTask);
			Logger.log("TimeLine:" + newTask.toString() + " added()", Logger.DEBUG);
			return;
		}
		int prev = 0;
		for(Task t:tasks)
		{
			if(t.time > newTask.time)
			{
				tasks.add(prev, newTask);
				Logger.log("TimeLine:" + newTask.toString() + " added()", Logger.DEBUG);
				return;
			}
			else
				prev++;
		}
		tasks.addLast(newTask);
		Logger.log("TimeLine:" + newTask.toString() + " added()", Logger.DEBUG);
	}
	public static void execute()
	{
		Logger.log("TimeLine:" + "execute()" + tasks.size(), Logger.DEBUG);
		int count = 0;
		while(tasks.size() > 0)
		{
			Logger.log("\texecute():task" + count, Logger.VERY_DETAIL);
			Task t = tasks.removeFirst();
			t.execute();
			count++;
			if(count % 50 == 0)
				Logger.log(count + "tasks have been executed�� now remain" + tasks.size(), Logger.DEBUG);
		}
	}
	public static void display()
	{
		for(Task t:tasks)
		{
			t.display();
		}
	}
	

}
