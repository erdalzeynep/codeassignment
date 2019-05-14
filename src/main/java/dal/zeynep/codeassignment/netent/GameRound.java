package dal.zeynep.codeassignment.netent;

import java.util.Date;
import java.util.UUID;

public class GameRound {
    private String roundId;
    private String userId;
    private Integer winningAmount;
    private Date playingTime;
    private boolean hasCoinsWon;
    private boolean hasFreeRoundWon;


    public GameRound(String userId, Integer winningAmount, boolean hasFreeRoundWon) {
        this.userId = userId;
        this.winningAmount = winningAmount;
        this.hasFreeRoundWon = hasFreeRoundWon;
        this.hasCoinsWon = this.winningAmount >= 0;
        this.roundId = UUID.randomUUID().toString();
        this.playingTime = new Date();
    }

    public String getRoundId() {
        return roundId;
    }

    public void setRoundId(String roundId) {
        this.roundId = roundId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getWinningAmount() {
        return winningAmount;
    }

    public void setWinningAmount(Integer winningAmount) {
        this.winningAmount = winningAmount;
    }

    public Date getPlayingTime() {
        return playingTime;
    }

    public void setPlayingTime(Date playingTime) {
        this.playingTime = playingTime;
    }

    public boolean hasCoinsWon() {
        return hasCoinsWon;
    }

    public void setHasCoinsWon(boolean hasCoinsWon) {
        this.hasCoinsWon = hasCoinsWon;
    }

    public boolean hasFreeRoundWon() {
        return hasFreeRoundWon;
    }

    public void setHasFreeRoundWon(boolean hasFreeRoundWon) {
        this.hasFreeRoundWon = hasFreeRoundWon;
    }
}
