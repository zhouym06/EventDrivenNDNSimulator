package event;

import java.util.LinkedList;

public class TimeLine {
	LinkedList<Task> tasks = null; 
	public void add(Task newTask)
	{
		if(tasks == null)
			tasks = new LinkedList<Task>();
		int prev = 0;
		for(Task t:tasks)
		{
			if(t.time > newTask.time)
			{
				tasks.add(prev, newTask);
				break;
			}
			else
				prev++;
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
