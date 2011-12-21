package simulator.packet;

public class Packet {
	public int timeLived = 0;
	public String contentName;		//or uri, or prefix for announce
	//int size;						//all packet are in size 1kb now
	public Packet(String contentName)
	{
		this.contentName = contentName;
	}

}
