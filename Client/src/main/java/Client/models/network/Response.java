package Client.models.network;

import Client.enums.QueryResponses;
import Client.models.tiles.Tile;

import java.util.HashMap;

public class Response {
    private final QueryResponses queryResponse;
    private HashMap<String, String> params;
    private Tile tile;

    public Response(QueryResponses queryResponse, HashMap<String, String> params) {
        this.queryResponse = queryResponse;
        this.params = params;
    }
    public Response(QueryResponses queryResponse, Tile tile) {
        this.queryResponse = queryResponse;
        this.tile = tile;
    }

    public QueryResponses getQueryResponse() {
        return queryResponse;
    }

    public Tile getTile() {
        return this.tile;
    }

    public void addParam(String key, String value) {
        this.params.put(key, value);
    }

    public HashMap<String, String> getParams() {
        return this.params;
    }
}
