package limiter;

//import response.RateLimitResponse;

import java.time.Duration;
import java.time.Instant;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindowRateLimiter {
    //limit window size in time duration.
    private final int windowCountLimit;
    private final int windowSizeInSeconds;
    private final Map<String , Deque<Instant>> store = new ConcurrentHashMap<>();

    public SlidingWindowRateLimiter(int count , int time){
        this.windowCountLimit =count;
        this.windowSizeInSeconds = time;
    }

    public SlidingWindowRateLimiter(){
        this(5,60);
    }

    public RateLimitResponse allowRequest(String clientId){
        Instant currTime = Instant.now();
        Deque<Instant> queue = store.computeIfAbsent(clientId, k -> new LinkedList<>());


        //get window info for clientId.
        synchronized (queue){
            // if client exists, then check for window limit. and remove the expired Instant of queue.
            while (!queue.isEmpty() && Duration.between(queue.peekFirst(), currTime)
                    .compareTo(Duration.ofSeconds(windowSizeInSeconds)) >= 0) {
                queue.pollFirst();
            }

            //check for room in window.
            if (queue.size() >= windowCountLimit) {
                // return rejected.
                return new RateLimitResponse(false,0);
            }
            // push time instant in queue.
            queue.addLast(currTime);
            //return allow.
            return new RateLimitResponse(true, windowCountLimit - queue.size());
        }
    }
}
