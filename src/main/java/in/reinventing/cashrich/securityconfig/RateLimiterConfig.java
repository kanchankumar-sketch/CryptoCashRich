package in.reinventing.cashrich.securityconfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimiterConfig {
    
    @Value("${rateLimit.intervalInMillis}")
    private long rateLimitIntervalInMillis;

    @Value("${rateLimit.maxRequestsPerInterval}")
    private int maxRequestsPerInterval;

    @Bean
    public RateLimiter rateLimiter() {
        return new RateLimiter(rateLimitIntervalInMillis, maxRequestsPerInterval);
    }
}
