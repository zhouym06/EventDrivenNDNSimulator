package topology;

public class Sink extends Router {
	Router linkedTo;
	public Sink(Router linkedTo)
	{
		super(0, 0);
		this.linkedTo = linkedTo;
	}

}
