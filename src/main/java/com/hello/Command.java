package com.hello;

import java.util.Map;

/**
 * Created by eugene.levenetc on 18/04/2017.
 */
class Command {

    int data;
    String result;
    Map<String, String> params;

    public Command(String result, Map<String, String> params) {
        this.result = result;
        this.params = params;
    }

    @Override
    public String toString() {
        return "Command{" +
                "data=" + data +
                ", result='" + result + '\'' +
                ", params=" + params +
                '}';
    }
}
