package Server.models.resources;

import Server.enums.Improvements;
import Server.enums.resources.ResourceTypes;
import Server.enums.tiles.TileBaseTypes;
import Server.enums.tiles.TileFeatureTypes;

import java.util.HashSet;
import java.util.Random;

public class Resource {
    private final ResourceTypes type;
    private boolean hasBeenUsed;

    public Resource(ResourceTypes type) {
        this.type = type;
    }

    public static Resource generateRandomResource(TileBaseTypes type, TileFeatureTypes feature) {
        HashSet<ResourceTypes> allPossibleResources = type.getPossibleResources();
        allPossibleResources.addAll(feature.getPossibleResources());
        int possibleResourcesNumber = allPossibleResources.size();
        Random rand = new Random();
        if(possibleResourcesNumber == 0)
            return null;
        else if (rand.nextBoolean()) {
            ResourceTypes[] allPossibleResourcesArray = allPossibleResources.toArray(new ResourceTypes[possibleResourcesNumber]);
            int randomInt = rand.nextInt(possibleResourcesNumber);
            return new Resource(allPossibleResourcesArray[randomInt]);
        }
        return null;
    }

    public ResourceTypes getType() {
        return this.type;
    }

    public double getFood() {
        return this.type.getFood();
    }

    public double getProduction() {
        return this.type.getProduction();
    }

    public double getGold() {
        return this.type.getGold();
    }

    public String getName() {
        return this.type.getName();
    }

    public Improvements getRequiredImprovement() {
        return this.type.getRequiredImprovement();
    }

    public boolean hasBeenUsed() {
        return this.hasBeenUsed;
    }

    public void setHasBeenUsed(boolean hasBeenUsed) {
        this.hasBeenUsed = hasBeenUsed;
    }
}
