package team.hdt.blockadia.game_engine_old.client.glRequestProcessing;

public class GlRequestProcessor {

    private static final float MAX_TIME_MILLIS = 8f;

    private static GlRequestQueue requestQueue = new GlRequestQueue();

    public static void sendRequest(GlRequest request) {
        requestQueue.addRequest(request);
    }

    public static void dealWithTopRequests() {
        float remainingTime = MAX_TIME_MILLIS * 1000000;
        long start = System.nanoTime();
        while (requestQueue.hasRequests()) {
            requestQueue.acceptNextRequest().executeGlRequest();
            long end = System.nanoTime();
            long timeTaken = end - start;
            remainingTime -= timeTaken;
            start = end;
            if (remainingTime < 0) {
                break;
            }
        }
    }

    public static void completeAllRequests() {
        while (requestQueue.hasRequests()) {
            requestQueue.acceptNextRequest().executeGlRequest();
        }
    }


}
