package Client.controllers;

import Client.enums.QueryRequests;
import Client.models.tiles.Coordination;
import Client.models.tiles.Hex;

import java.util.HashMap;
import java.util.Objects;

public class HexController {
    public static int width;
    public static int height;
    private static Hex[][] hexes;

    public static void generateHexes(int wantedWidth, int wantedHeight) {
        width = wantedWidth;
        height = wantedHeight;
        hexes = new Hex[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                hexes[i][j] = new Hex(i, j);
            }
        }
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static void updateHex(int i, int j) {
        hexes[i][j].updateHex(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.UPDATE_HEX, new HashMap<>(){{
            put("x", String.valueOf(i));
            put("y", String.valueOf(j));
        }})).getTile());
    }

    public static Hex[][] getHexes() {
        return hexes;
    }


    public static Hex getHexOfTheGivenCoordination(int i, int j) {
        return hexes[i][j];
    }

    public static Hex getHexOfTheGivenCoordination(Coordination coordination) {
        return hexes[coordination.getX()][coordination.getY()];
    }
}
