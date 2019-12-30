package ca.utoronto.ece419.tester.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {
    private static SimpleDateFormat sdf = new SimpleDateFormat("d.M.y H:m:s.S z");
    private Date date = null;

    public LogFormatter() {
        this.date = new Date();
    }

    @Override
    public String format(LogRecord logRecord) {
        StringBuffer buf = new StringBuffer();

        this.date.setTime(logRecord.getMillis());
        buf.append("[");
        buf.append(sdf.format(this.date));
        buf.append("] ");

        buf.append(logRecord.getLevel());
        buf.append(": ");

        buf.append(logRecord.getLoggerName());
        buf.append(" - ");

        buf.append(logRecord.getMessage());

        buf.append("\n");

        return buf.toString();
    }
}
