package simulator.packet;

public class ContentPacket extends Packet{
	public ContentPacket(String contentName)
	{
		super(contentName);
	}
	public ContentPacket(ContentPacket cPacket)
	{
		super(cPacket);
	}
}
