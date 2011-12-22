package simulator.packet;

public class AnnoucePacket extends Packet {
	
	public AnnoucePacket(String contentName)
	{
		super(contentName);
	}

	public AnnoucePacket(AnnoucePacket p) {
		super(p);
	}
}
