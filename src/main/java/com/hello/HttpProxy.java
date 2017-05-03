package com.hello;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Created by eugene.levenetc on 11/04/2017.
 */
public interface HttpProxy {
    void handle(HttpMethod httpMethod, String query, Map<String, String> headers, String body, BufferedWriter out) throws IOException;
}
