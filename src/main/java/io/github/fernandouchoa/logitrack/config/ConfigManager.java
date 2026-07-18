package io.github.fernandouchoa.logitrack.config;

public final class ConfigManager {

    private ConfigManager() {
    }

    public static String getBaseUrl() {
        return read(
                "baseUrl",
                "LOGITRACK_BASE_URL",
                "https://logitrack.danieldiegosantana.me"
        );
    }

    public static String getEmail() {
        return read("email", "LOGITRACK_EMAIL", "");
    }

    public static String getPassword() {
        return read("password", "LOGITRACK_PASSWORD", "");
    }

    public static String getBrowser() {
        return read("browser", "LOGITRACK_BROWSER", "chromium");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(
                read("headless", "LOGITRACK_HEADLESS", "false")
        );
    }

    public static double getTimeout() {
        return Double.parseDouble(
                read("timeout", "LOGITRACK_TIMEOUT", "45000")
        );
    }

    public static boolean hasCredentials() {
        return !getEmail().isBlank() && !getPassword().isBlank();
    }

    private static String read(
            String systemProperty,
            String environmentVariable,
            String defaultValue
    ) {
        String systemValue = System.getProperty(systemProperty);

        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue.trim();
        }

        String environmentValue = System.getenv(environmentVariable);

        if (environmentValue != null && !environmentValue.isBlank()) {
            return environmentValue.trim();
        }

        return defaultValue;
    }
}