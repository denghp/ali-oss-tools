package org.mvnsearch.ali.oss.spring.shell.converters;

import java.util.Arrays;
import java.util.List;

/**
 * http header
 *
 * @author denghp
 */
public class HttpHeader {
    public static final List<String> DEFAULT_HEADERS = Arrays.asList("Cache-Control", "Content-Type", "Expires",
            "Access-Control-Allow-Origin", "Content-MD5", "Content-Disposition",
            "Pragma", "Content-Encoding", "Set-Cookie");;
    /**
     * name
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * get default http header names
     *
     * @return http header names
     */
    public static List<String> getDefaultNames() {
        return DEFAULT_HEADERS;
    }

}
