package simulator.packet;

public abstract class Packet {
	public int timeLived = 0;
	public String contentName;		//or uri, or prefix for announce
	
	public Packet(String contentName)
	{
		this.contentName = contentName;
	}
	public Packet(Packet p)
	{
		this.contentName = p.contentName;
		//this.timeLived = p.timeLived;
		this.timeLived = p.timeLived + 1;
	}

}
