package game;



import game.engine.BoardFieldOwnership;
import game.engine.GameEngineStatus;
import game.engine.Player;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by ubuntu-master on 26.12.16.
 */
public class GoPlayer implements Player {

    private BoardFieldOwnership color;
    private GameEngineStatus gameStatus;

    public GoPlayer(BoardFieldOwnership color, GameEngineStatus status){
        this.color = color;
        gameStatus = status;
    }

    @Override
    public void stonePlaced(Point opponentPoint, BoardFieldOwnership player) {

    }

    @Override
    public void playerPassedTurn(BoardFieldOwnership player) {

    }

    @Override
    public void setColor(BoardFieldOwnership color) {
        this.color = color;
    }

    @Override
    public BoardFieldOwnership getColor() {
        return color;
    }

    @Override
    public void notifyGameStateChanged(GameEngineStatus newState) {
        gameStatus = newState;
    }

    @Override
    public void announceWinner(BoardFieldOwnership winner, int blackScore, int whiteScore) {

    }

    @Override
    public void territoryProposition(ArrayList<Point> blackTerritory, ArrayList<Point> whiteTerritory, BoardFieldOwnership player) {

    }
}
