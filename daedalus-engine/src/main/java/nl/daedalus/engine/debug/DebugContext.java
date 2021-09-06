package nl.daedalus.engine.debug;

import nl.daedalus.engine.core.Constants;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DebugContext {

    public static StringBuilder builder = new StringBuilder();

    static {
        builder.append("{ \"otherData\":{}, \"traceEvents\":[");
    }

    public static void add(String function, long start, long end) {
        if (Constants.DEBUG) {
            builder.append("{\"cat\":\"function\",");
            builder.append("\"dur\":");
            builder.append((end / 1000) - (start / 1000));
            builder.append(",");
            builder.append("\"name\":\"");
            builder.append(function);
            builder.append("\",");
            builder.append("\"ph\":\"X\",");
            builder.append("\"pid\":0,");
            builder.append("\"tid\":");
            builder.append(1);
            builder.append(",");
            builder.append("\"ts\":");
            builder.append(start / 1000);
            builder.append("}");
            builder.append(",\n");
        }
    }

    public static void end() {
        if (Constants.DEBUG) {
            builder.replace(builder.length()-2, builder.length(), "");
            builder.append("]}");
            File file = new File("profile.json");
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(file));
                writer.write(builder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
