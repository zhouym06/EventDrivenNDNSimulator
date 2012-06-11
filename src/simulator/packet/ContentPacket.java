package simulator.packet;

public class ContentPacket extends Packet{
	public int size;
	public ContentPacket(String contentName, int size)
	{
		super(contentName);
		this.size = size;
	}
	/**/
	public ContentPacket(ContentPacket cPacket)
	{
		super(cPacket);
		this.size = cPacket.size;
	}
}
