package Server.models.network;


import Server.enums.QueryRequests;

import java.util.HashMap;

public class Request {
    private final QueryRequests queryRequest;
    private final HashMap<String, String> params;

    public Request(QueryRequests queryRequest, HashMap<String, String> params) {
        this.queryRequest = queryRequest;
        this.params = params;
    }

    public QueryRequests getQueryRequest() {
        return this.queryRequest;
    }

    public void addParam(String key, String value) {
        this.params.put(key, value);
    }

    public HashMap<String, String> getParams() {
        return this.params;
    }
}
