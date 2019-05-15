package dal.zeynep.codeassignment.netent;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class GameRound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private String id;
    @Column
    private String userId;
    @Column
    private Integer winningAmount;
    @Column
    private Date playingTime;
    @Column
    private boolean hasCoinsWon;
    @Column
    private boolean hasFreeRoundWon;

    public GameRound() {
    }

    public GameRound(String userId, Integer winningAmount, boolean hasFreeRoundWon) {
        this.userId = userId;
        this.winningAmount = winningAmount;
        this.hasFreeRoundWon = hasFreeRoundWon;
        this.hasCoinsWon = this.winningAmount >= 0;
        this.playingTime = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
