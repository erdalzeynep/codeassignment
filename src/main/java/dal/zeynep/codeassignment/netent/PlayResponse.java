package dal.zeynep.codeassignment.netent;

public class PlayResponse {

    private boolean hasWonCoins;
    private boolean hasWonFreeRound;

    public PlayResponse(boolean hasWonCoins, boolean hasWonFreeRound) {
        this.hasWonCoins = hasWonCoins;
        this.hasWonFreeRound = hasWonFreeRound;
    }

    public boolean hasWonCoins() {
        return hasWonCoins;
    }

    public boolean hasWonFreeRound() {
        return hasWonFreeRound;
    }
}
