package com.dublinbikes.common.logging;

import java.util.Enumeration;
import java.util.Properties;

/**
 * 
 * @author arun
 *
 */
public class NameValuePairLogMessage implements NameValuePairConstants {
	
    public static final char DELIMITER = ',';

    public static final char ESCAPER = '\\';

    public static final String DEFAULT_SPECIAL_CHARS = "\",";

    public static final int SUCCESS = 0;

    static String SPECIAL_CHARS = DEFAULT_SPECIAL_CHARS;

    private Properties p;

    private Object msg;

    public NameValuePairLogMessage(Object msg) {
        this(msg, null);
    }

    public NameValuePairLogMessage(Object msg, Properties p) {
        this(SUCCESS, msg, p);
    }

    public NameValuePairLogMessage(int error, Object msg) {
        this(error, msg, null);
    }

    public NameValuePairLogMessage(int error, Object msg, Properties p) {
        this.msg = msg;

        if (error != SUCCESS) {
            this.p = new Properties();
            this.p.setProperty(PROPERTY_NAME_ERROR_CODE, "" + error);
        }

        if (p != null) {
            if (this.p == null) {
                this.p = new Properties();
            }
            this.p.putAll(p);
        }
    }

    public void setProperty(String key, String value) {
        p.setProperty(key, value);
    }

    public static void setDefaultSpecialCharacters(String s) {
        SPECIAL_CHARS = s;
    }

    public static String getDefaultSpecialCharacters() {
        return SPECIAL_CHARS;
    }

    public String getMessage() {
        return msg.toString();
    }

    public static String encode(String s) {
        if (s == null) {
            return null;
        }

        StringBuffer sb = new StringBuffer(s);
        int insertCount = 0;

        for (int i = 0; i < s.length(); i++) {
            if (SPECIAL_CHARS.indexOf(s.charAt(i)) >= 0) {
                sb.insert(insertCount + i, ESCAPER);
                insertCount++;
            }
        }

        return new String(sb);
    }

    public static String decode(String s) {
        if (s == null) {
            return null;
        }

        StringBuffer sb = new StringBuffer(s);
        int deleteCount = 0;

        for (int i = 0; i < s.length(); i++) {
            if ((s.charAt(i) == ESCAPER) && (i < (s.length() - 1))
                && (SPECIAL_CHARS.indexOf(s.charAt(i + 1)) >= 0)) {
                sb.deleteCharAt(i - deleteCount);
                deleteCount++;
            }
        }

        return new String(sb);
    }

    public String formatMessage() {
        return formatMessage(msg, p);
    }

    public static String formatMessage(Properties p) {
        return formatMessage(null, p);
    }

    public static String formatMessage(Object msg, Properties p) {
        if ((msg == null) && (p == null)) {
            return null;
        }

        boolean prefixDelimiter = false;
        StringBuffer sb = new StringBuffer();

        if (msg != null) {
            sb.append(PROPERTY_NAME_MESSAGE + "=").append(encode(msg.toString()));
            prefixDelimiter = true;
        }

        if (p != null) {
            Enumeration e = p.keys();

            while (e.hasMoreElements()) {
                String n = (String) e.nextElement();
                String v = p.getProperty(n);

                if (prefixDelimiter) {
                    sb.append(DELIMITER);
                }
                sb.append(n).append("=").append(encode(v));
                prefixDelimiter = true;
            }
        }

        return sb.toString();
    }

    public static Properties parse(String s) {
        if (s == null) {
            return null;
        }

        String[] nvs = s.split("" + DELIMITER);
        int i = 0;
        Properties p = new Properties();

        if (nvs == null) {
            return p;
        }

        do {
            String nv = nvs[i];

            while (nv.endsWith("" + ESCAPER)) {
                i++;

                if (i < nvs.length) {
                    nv += ("," + nvs[i]);
                } else if (s.endsWith("" + ESCAPER + DELIMITER)) {
                    nv += ("" + DELIMITER);
                }
            }

            int ep = nv.indexOf("=");

            if (ep <= 0) {
                return null;
            }

            // throw new IllegalArgumentException(
            // "Cannot parse a non-name value pair input string.");
            String name = nv.substring(0, ep);
            String value = nv.substring(ep + 1);
            value = decode(value);
            p.setProperty(name, value);
            i++;
        } while (i < nvs.length);

        return p;
    }

    @Override
    public String toString() {
        return formatMessage();
    }
}