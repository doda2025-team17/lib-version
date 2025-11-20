package nl.tudelft.doda;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class VersionUtil {

    private static final String VERSION;

    static {
        String v = "UNKNOWN";
        try (InputStream in = VersionUtil.class.getResourceAsStream(
                "/META-INF/maven/nl.tudelft.doda/lib-version/pom.properties")) {
            if (in != null) {
                Properties props = new Properties();
                props.load(in);
                v = props.getProperty("version", v);
            }
        } catch (IOException ignored) {
            // keep default "UNKNOWN"
        }
        VERSION = v;
    }

    /** Returns the version of the lib-version package at runtime */
    public static String getVersion() {
        return VERSION;
    }
}
