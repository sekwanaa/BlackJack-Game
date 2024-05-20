package com.pluralsight.InteractionHandlers;

import com.pluralsight.Models.Player;

import static org.junit.jupiter.api.Assertions.*;

class InsuranceAndBlackJackTest {

    @org.junit.jupiter.api.Test
    void processHouseBlackJack() {
        //Arrange
        Player player = new Player("Test");
        player.setPoints(300);
        player.setOriginalBetAmount(100);
        player.setInsuranceBetAmount(50);
        double expectedPoints = 300;
        double expectedInsuranceAmount = 0;
        double expectedOriginalBetAmount = 100;

        //Test
        PointsInteractionHandler.processHouseBlackJack(player);
        player.processLoss();
        //Assert
        assertEquals(expectedPoints, player.getPoints());
        assertEquals(expectedInsuranceAmount, player.getInsuranceBetAmount());
        assertEquals(expectedOriginalBetAmount, player.getBetAmount());
    }
}