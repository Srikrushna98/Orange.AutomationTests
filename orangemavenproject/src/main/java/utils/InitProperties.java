package utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * The type Init properties.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InitProperties {

    /**
     * The constant RUN_TYPE.
     * Get mode of run
     */
    public static final String RUN_TYPE =System.getProperty("run.type");
    /**
     * The constant REMOTE_URL.
     * The URL of remote web drivers
     */
    public static final String REMOTE_URL = System.getProperty("remote.url");
    /**
     * The constant ENVIRONMENT.
     * Get environment when we want run tests
     */
    public static final String ENVIRONMENT = System.getProperty("environment");

}