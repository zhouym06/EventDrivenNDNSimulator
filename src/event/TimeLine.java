package event;

import java.util.LinkedList;

import logger.Logger;

public class TimeLine {
	LinkedList<Task> tasks = null;
	public void add(Task newTask)
	{
		if(tasks == null)
		{
			tasks = new LinkedList<Task>();
			tasks.add(newTask);
			return;
		}
		int prev = 0;
		for(Task t:tasks)
		{
			if(t.time > newTask.time)
			{
				tasks.add(prev, newTask);
				return;
			}
			else
				prev++;
		}
		tasks.addLast(newTask);
	}
	public void execute()
	{
		Logger.log("TimeLine:" + "execute()" + tasks.size(), Logger.DEBUG);
		int count = 0;
		while(tasks.size() > 0)
		{
			Logger.log("\texecute():task" + count, Logger.VERY_DETAIL);
			Task t = tasks.removeFirst();
			t.execute(this);
			count++;
			if(count % 50 == 0)
				Logger.log(count + "tasks have been executed", Logger.DEBUG);
		}
	}
	public void display()
	{
		for(Task t:tasks)
		{
			t.display();
		}
	}

}
