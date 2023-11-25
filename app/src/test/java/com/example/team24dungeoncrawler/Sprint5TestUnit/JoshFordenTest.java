package com.example.team24dungeoncrawler.Sprint5TestUnit;

import static org.junit.Assert.assertEquals;

import android.view.KeyEvent;
import android.widget.GridLayout;

import com.example.team24dungeoncrawler.R;
import com.example.team24dungeoncrawler.model.ExitStrategy;
import com.example.team24dungeoncrawler.model.MoveRightStrategy;
import com.example.team24dungeoncrawler.model.MovementStrategy;
import com.example.team24dungeoncrawler.model.Player;
import com.example.team24dungeoncrawler.model.PlayerView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


public class JoshFordenTest {

    private Player player;
    private long visibleStartTime;
    private int currentScore;
    private int[][] tilemap;
    private PlayerView playerView;
    @Before
    public void setUp() {
        // Initialize a Player instance before each test
        player = Player.getInstance("TestPlayer1", "Medium");
        currentScore = 10;
        //playerView = new PlayerView(JoshFordenTest context); // Create a new PlayerView
        tilemap = new int[][]{
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4},
                {0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 2, 2, 2, 2, 2, 2, 4},
                {0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 2, 2, 2, 2, 2, 2, 4},
                {0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3},
                {0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3},
                {0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4},
                {0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4},
                {0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4},
                {0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4},
                {0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4},
                {0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4},
                {0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4},
                {0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4},
                {0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4},
                {0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4},
                {0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4},
                {0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4},
                {0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4},
                {0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4},
                {0, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5}
        };
    }

    @Test
    public void playerInitPosition() {
        assertEquals(player.getCol(), 1);
        assertEquals(player.getRow(), 3);
        assertEquals(currentScore, 11);
    }

    @Test
    public void wait_TooLong_BeforeReachingDoor() throws InterruptedException {
        visibleStartTime = System.currentTimeMillis();
        Thread.sleep(10000);
        player.setCol(19);
        player.setRow(3);

        playerMoving();

        assertEquals(currentScore, 10);
    }

    @Test
    public void wait_50milli_BeforeReachingDoor() throws InterruptedException {
        visibleStartTime = System.currentTimeMillis();
        Thread.sleep(50);
        player.setCol(19);
        player.setRow(3);

        playerMoving();

        int newScore = (10 - (int) (50 / 1000)) * 10;
        assertEquals(currentScore, newScore + 10);
    }

    @Test
    public void wait_1sec_BeforeReachingDoor() throws InterruptedException {
        visibleStartTime = System.currentTimeMillis();
        Thread.sleep(1000);
        player.setCol(19);
        player.setRow(3);

        playerMoving();

        int newScore = (10 - (int) (1000 / 1000)) * 10;
        assertEquals(currentScore, newScore + 10);
    }




    private void playerMoving() {

        MovementStrategy movementStrategy = new MoveRightStrategy();

        if (movementStrategy != null) {
            int newTileType = tilemap[player.getRow()][player.getCol()];
            if (newTileType == 3) {

                //Player gets 10 - (seconds to reach the door) points after reaching door
                long timeToReachDoor = System.currentTimeMillis() - visibleStartTime;
                System.out.println(timeToReachDoor);
                int scoreChange = (10 - (int) (timeToReachDoor / 1000)) * 10;
                if (scoreChange <= 0) {
                    scoreChange = 1;
                }
                currentScore += scoreChange;
            }
        }
    }

}
