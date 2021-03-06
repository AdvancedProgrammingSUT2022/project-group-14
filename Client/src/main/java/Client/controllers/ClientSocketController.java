package Client.controllers;

import Client.application.App;
import Client.enums.QueryRequests;
import Client.models.City;
import Client.models.User;
import Client.models.network.Request;
import Client.models.network.Response;
import Client.models.tiles.Tile;
import Client.models.units.CombatUnit;
import Client.views.*;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class ClientSocketController {
    private static Socket socket;
    private static Socket readerSocket;
    private static BufferedWriter bufferedWriter;
    private static BufferedReader bufferedReader;

    public static void startConnecting(int serverPort) {
        try {
            socket = new Socket("localhost", serverPort);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("You've successfully connected to the server.");
        } catch (IOException e) {
            System.err.print("Couldn't connect to the server!");
            System.exit(0);
        }
    }

    public static void startListener(int serverPort) throws IOException {
        readerSocket = new Socket("localhost", serverPort);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(readerSocket.getOutputStream()));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(readerSocket.getInputStream()));
        String requestJson = new Gson().toJson(new Request(QueryRequests.ADD_LISTENER, new HashMap<>() {{
            put("username", MainMenuController.loggedInUser.getUsername());
        }}));
        bufferedWriter.write(requestJson + "\n");
        bufferedWriter.flush();
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    System.out.println("Waiting for ServerUpdates.");
                    handleServerUpdate(new Gson().fromJson(bufferedReader.readLine(), Response.class));
                    System.out.println("ServerUpdate received.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public static Response sendRequestAndGetResponse(QueryRequests command, HashMap<String, String> params) {
        try {
            Request request = new Request(command, params);
            if (MainMenuController.loggedInUser != null)
                request.addParam("token", MainMenuController.loggedInUser.getToken());
            String requestJson = new Gson().toJson(request);
            bufferedWriter.write(requestJson + "\n");
            bufferedWriter.flush();
            return new Gson().fromJson(bufferedReader.readLine(), Response.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void handleServerUpdate(Response response) {
        System.out.println(response.getQueryResponse() + " : " + response.getParams());
        switch (response.getQueryResponse()) {
            case UPDATE_GIVEN_TILE ->
                    HexController.getHexOfTheGivenCoordination(response.getTile().getX(), response.getTile().getY()).updateHex(response.getTile());
            case CHANGE_SCENE -> {
                if (response.getParams().get("sceneName").equals("gamePage")) {
                    if (Boolean.parseBoolean(response.getParams().get("turn"))) {
                        GamePageController.isMyTurn = true;
                        HexController.generateHexes(Integer.parseInt(response.getParams().get("width")), Integer.parseInt(response.getParams().get("height")));
                    } else {
                        GamePageController.isMyTurn = false;
                    }
                    Platform.runLater(() -> App.changeScene(response.getParams().get("sceneName")));
                } else if (response.getParams().get("sceneName").equals("endGamePage")) {
                    GamePageController.stopTimeline = true;
                    if (Boolean.parseBoolean(response.getParams().get("winner")))
                        EndGamePageController.setWinnerCivilization(MainMenuController.loggedInUser.getUsername());
                    MainMenuController.loggedInUser = new Gson().fromJson(response.getParams().get("user"), User.class);
                    Platform.runLater(() -> App.changeScene(response.getParams().get("sceneName")));
                }
            }
            case CHANGE_HEX_INFO_TEXT ->
                    HexController.getHexOfTheGivenCoordination(Integer.parseInt(response.getParams().get("x")), Integer.parseInt(response.getParams().get("y")))
                            .setInfoText(response.getParams().get("info"), response.getParams().get("color").equals("green") ? Color.GREEN : Color.RED);
            case UPDATE_ALL_HEXES -> {
                Tile[][] map = new Gson().fromJson(response.getParams().get("tiles"), Tile[][].class);
                if (HexController.getHexes() != null) {
                    for (Tile[] tiles : map) {
                        for (Tile tile : tiles) {
                            HexController.getHexOfTheGivenCoordination(tile.getX(), tile.getY()).updateHex(tile);
                        }
                    }
                }
            }
            case UPDATE_CHAT -> {
                MainMenuController.loggedInUser = new Gson().fromJson(response.getParams().get("user"), User.class);
                ChatRoomPageController.updateChatroom = true;
            }
            case UPDATE_INVITATIONS -> {
                MainMenuController.loggedInUser = new Gson().fromJson(response.getParams().get("user"), User.class);
                StartGameMenuController.updateInvites = true;
            }
            case CHOOSE_CITY_OPTIONS ->
                    GamePageController.setCityOptions(new Gson().fromJson(response.getParams().get("city"), City.class), new Gson().fromJson(response.getParams().get("combatUnit"), CombatUnit.class));
            case CHOOSE_WAR_OPTIONS ->
                    GamePageController.setDeclareWarOptions(new Gson().fromJson(response.getParams().get("enemyName"), String.class));
        }
    }
}
