package team.hdt.blockadia.game_engine.client.glRequestProcessing;

import java.util.ArrayList;
import java.util.List;

public class GlRequestQueue {
	
	private List<GlRequest> requestQueue = new ArrayList<GlRequest>();
	
	public synchronized void addRequest(GlRequest request){
		requestQueue.add(request);
	}
	
	public synchronized GlRequest acceptNextRequest(){
		return requestQueue.remove(0);
	}
	
	public synchronized boolean hasRequests(){
		return !requestQueue.isEmpty();
	}

}
