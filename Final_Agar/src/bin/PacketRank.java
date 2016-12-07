package bin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class PacketRank extends Packet{

	public Map<String, Integer> ranking;
	
	public PacketRank(byte[] data) {
		super(06);
		String[] dataArray = readData(data).split(",");
		ranking = new HashMap<String, Integer>();
		for(int i= 0; i < dataArray.length-1; i+=2){
			//System.out.println(dataArray[i]+","+dataArray[i+1]);
			ranking.put(dataArray[i], Integer.parseInt(dataArray[i+1]));
		}
	}

	public void writeData(GameClient client) {
		 client.sendData(getData());
	}

	public void writeData(GameServer server) {
		server.sendDataToAllClients(getData());
	}
	
	private String getRanking() {
		String rank = "06";
		int ctrl = 1;
		Iterator<Entry<String, Integer>> it = ranking.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>)it.next();
			if(ctrl==1){
				rank = rank+pair.getKey()+","+pair.getValue();
				ctrl+=1;
			}else{
				rank = ","+rank+","+pair.getKey()+","+pair.getValue();
			}
			it.remove();
		}
		return rank; 
	}

	public byte[] getData() {
		return getRanking().getBytes();
	}

	public int getX() {
		return 0;
	}

	public int getY() {
		return 0;
	}

	public int getH() {
		return 0;
	}

	public int getW() {
		return 0;
	}

	public String getUsername() {
		return null;
	}

	public ObjectId getId() {
		return null;
	}

	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 0;
	}
}
