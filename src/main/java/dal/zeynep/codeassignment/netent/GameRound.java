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
    private String userName;
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

    public GameRound(String userName, Integer winningAmount, boolean hasFreeRoundWon) {
        this.userName = userName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public boolean isHasCoinsWon() {
        return hasCoinsWon;
    }

    public boolean isHasFreeRoundWon() {
        return hasFreeRoundWon;
    }

    public void setPlayingTime(Date playingTime) {
        this.playingTime = playingTime;
    }

    public void setHasCoinsWon(boolean hasCoinsWon) {
        this.hasCoinsWon = hasCoinsWon;
    }

    public void setHasFreeRoundWon(boolean hasFreeRoundWon) {
        this.hasFreeRoundWon = hasFreeRoundWon;
    }
}
