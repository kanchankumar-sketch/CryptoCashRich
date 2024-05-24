package in.reinventing.cashrich.securityconfig;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.servlet.http.HttpServletRequest;

public class RateLimiter {
    private Map<String, Long> requestTimestamps = new ConcurrentHashMap<>();
    private final long rateLimitIntervalInMillis;
    private final int maxRequestsPerInterval;

    public RateLimiter(long rateLimitIntervalInMillis, int maxRequestsPerInterval) {
        this.rateLimitIntervalInMillis = rateLimitIntervalInMillis;
        this.maxRequestsPerInterval = maxRequestsPerInterval;
    }

    public synchronized boolean allowRequest(HttpServletRequest request) {
        String clientId = getClientId(request);
        if (clientId == null) {
            return true; // Unable to identify client, allow request
        }

        long currentTimeMillis = System.currentTimeMillis();
        long intervalStart = currentTimeMillis - rateLimitIntervalInMillis;
        int count = 0;

        // Count the number of requests made by the client within the interval
        for (long timestamp : requestTimestamps.values()) {
            if (timestamp >= intervalStart) {
                count++;
            }
        }

        // Check if the number of requests exceeds the limit
        if (count >= maxRequestsPerInterval) {
            return false; // Limit exceeded
        }

        // Store the timestamp of the current request
        requestTimestamps.put(clientId, currentTimeMillis);
        return true; // Request allowed
    }

    private String getClientId(HttpServletRequest request) {
        // Here you can extract a unique identifier for the client,
        // such as the IP address
        return request.getRemoteAddr();
    }
}
