package game.timemanagement;

/**
 * Service locator for managing a global instance of TimeProvider.
 * Ensures a single TimeProvider is used throughout the game.
 *
 * @author Adji Ilhamhafiz Sarie Hakim
 */
public class ServiceLocator {

    /** The singleton TimeProvider registered via registerTimeProvider(TimeProvider) */
    private static TimeProvider timeProvider;

    /**
     * Registers a TimeProvider if one is not already registered.
     *
     * @param provider the TimeProvider to register
     * @throws IllegalStateException if a TimeProvider has already been registered
     */
    public static void registerTimeProvider(TimeProvider provider) {
        if (timeProvider != null) {
            throw new IllegalStateException("TimeProvider already registered");
        }
        timeProvider = provider;
    }

    /**
     * Returns the registered TimeProvider.
     *
     * @return the TimeProvider instance
     */
    public static TimeProvider getTimeProvider() {
        return timeProvider;
    }
}
