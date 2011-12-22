package topology;

import logger.Logger;

public class Sink extends Router {
	Router linkedTo;
	public Sink(Router linkedTo)
	{
		super(-2, 0);
		this.linkedTo = linkedTo;
	}
	
	public void  display()
	{
		Logger.log("Sink" + routerID + " has " + interfaces.size() + " edges", Logger.INFO);
		for(Edge e: interfaces)
		{
			e.display();
		}
	}

}
