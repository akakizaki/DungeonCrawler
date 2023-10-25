package com.example.team24dungeoncrawler.model;

import android.view.KeyEvent;

public class MoveUpStrategy implements MovementStrategy {
    @Override
    public void move(Player player, int keyCode, int[][] tilemap) {
        if (keyCode == KeyEvent.KEYCODE_W) {
            int newRow = player.getRow() - 1;
            int newCol = player.getCol();
            if (isValidMove(newRow, newCol, tilemap)) {
                player.setRow(newRow);
                player.setCol(newCol);
            }
        }
    }

    private boolean isValidMove(int newRow, int newCol, int[][] tilemap) {
        if (newRow >= 0 && newRow < tilemap.length && newCol >= 0 && newCol < tilemap[0].length) {
            int newTileType = tilemap[newRow][newCol];

            // floor tile
            if (newTileType == 2 || newTileType == 3) {
                return true;
            }
        }
        return false;
    }
}

