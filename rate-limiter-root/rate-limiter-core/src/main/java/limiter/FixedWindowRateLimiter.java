package limiter;

import model.WindowState;
import response.RateLimitResponse;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

public class FixedWindowRateLimiter {
    private final int limit;
    private final long windowSizeInSeconds;

    private final ConcurrentHashMap<String, WindowState> rateMap = new ConcurrentHashMap<>();

    public FixedWindowRateLimiter(int limit, long windowSizeInSeconds) {
        this.limit = limit;
        this.windowSizeInSeconds = windowSizeInSeconds;
    }

    public FixedWindowRateLimiter() {
        this(5, 60);
    }

    public RateLimitResponse allowRequest(String clientId) {

        Instant now = Instant.now();
        WindowState state = rateMap.get(clientId);
        //if clientId is not present.
        if (state == null) {
            //avoid race condition by using computeIfAbsent
            rateMap.computeIfAbsent(clientId, id -> new WindowState(now, 1));
            return new RateLimitResponse(true, limit - 1);
        }

        synchronized (state) {

            Duration elapsed = Duration.between(state.getTime(), now);
            //check if window needs to be reset
            if (elapsed.getSeconds() >= windowSizeInSeconds) {
                // reset window
                state.setTime(now);
                state.setCount(1);
                return new RateLimitResponse(true, limit - 1);
            }

            // same window
            //checking if updated count is greater than limit
            if (state.getCount() >= limit) {
                return new RateLimitResponse(false, 0);
            }
            state.setCount(state.getCount() + 1);
            return new RateLimitResponse(true, limit - state.getCount());
        }
    }
}
