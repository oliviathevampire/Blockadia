package team.hdt.blockadia.game_engine.client.resourceProcessing;

public class RequestProcessor extends Thread {

    private static RequestProcessor PROCESSOR = new RequestProcessor();

    private RequestQueue requestQueue = new RequestQueue();
    private boolean running = true;

    private RequestProcessor() {
        this.start();
    }

    public static void sendRequest(ResourceRequest request) {
        PROCESSOR.addRequestToQueue(request);
    }

    public static void cleanUp() {
        PROCESSOR.kill();
    }

    public synchronized void run() {
        while (running || requestQueue.hasRequests()) {
            if (requestQueue.hasRequests()) {
                requestQueue.acceptNextRequest().doResourceRequest();
            } else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void kill() {
        running = false;
        indicateNewRequests();
    }

    private synchronized void indicateNewRequests() {
        notify();
    }

    private void addRequestToQueue(ResourceRequest request) {
        boolean isPaused = !requestQueue.hasRequests();
        requestQueue.addRequest(request);
        if (isPaused) {
            indicateNewRequests();
        }
    }

}
