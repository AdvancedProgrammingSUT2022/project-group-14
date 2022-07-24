package Server.models.network;

import Server.enums.QueryResponses;

import java.util.HashMap;

public class Response {
    private final QueryResponses queryResponse;
    private final HashMap<String, String> params;

    public Response(QueryResponses queryResponse, HashMap<String, String> params) {
        this.queryResponse = queryResponse;
        this.params = params;
    }

    public QueryResponses getQueryResponse() {
        return queryResponse;
    }

    public void addParam(String key, String value) {
        this.params.put(key, value);
    }

    public HashMap<String, String> getParams() {
        return this.params;
    }
}
