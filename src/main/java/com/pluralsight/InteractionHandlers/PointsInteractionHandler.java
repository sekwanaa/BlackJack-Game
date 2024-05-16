package com.pluralsight.InteractionHandlers;

import com.pluralsight.Models.Player;

public class PointsInteractionHandler {
    private PointsInteractionHandler() {}

    //Methods

    public static void processStartingPoints(Player player, int wageredPoints) {
        player.setPoints(wageredPoints);
    }
}
