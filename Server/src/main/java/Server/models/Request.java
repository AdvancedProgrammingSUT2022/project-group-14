package Server.models;


import Server.enums.QueryCommands;

import java.util.HashMap;

public class Request {
    private final QueryCommands command;
    private final HashMap<String, Object> params;

    public Request(QueryCommands command, HashMap<String, Object> params) {
        this.command = command;
        this.params = params;
    }

    public QueryCommands getCommand() {
        return this.command;
    }

    public void addParam(String key, Object value) {
        this.params.put(key, value);
    }

    public HashMap<String, Object> getParams() {
        return this.params;
    }
}
