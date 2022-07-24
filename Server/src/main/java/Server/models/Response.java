package Server.models;

public class Response {
    private final String destinationSceneName;
    private final Object returnedValue;

    public Response(String destinationSceneName, Object returnedValue) {
        this.destinationSceneName = destinationSceneName;
        this.returnedValue = returnedValue;
    }

    public Object getReturnedValue() {
        return this.returnedValue;
    }

    public String getDestinationSceneName() {
        return destinationSceneName;
    }
}
