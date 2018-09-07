package team.hdt.blockadia.game_engine_old.client.resourceProcessing;

import java.util.ArrayList;
import java.util.List;

public class RequestQueue {

    private List<ResourceRequest> requestQueue = new ArrayList<ResourceRequest>();

    public synchronized void addRequest(ResourceRequest request) {
        requestQueue.add(request);
    }

    public synchronized ResourceRequest acceptNextRequest() {
        return requestQueue.remove(0);
    }

    public synchronized boolean hasRequests() {
        return !requestQueue.isEmpty();
    }

}