package dal.zeynep.codeassignment.netent;

import java.util.Random;

public class GameEngine {
    private Random random;

    public GameEngine() {
        this.random = new Random();
    }

    public GameEngine(Random random) {
        this.random = random;
    }

    public PlayResponse play(User user) {

        return new PlayResponse(hasWinCoins(), hasWonFreeRound());
    }

    private boolean hasWinCoins(){
        boolean hasWonCoins = false;
        int chanceOfWinCoins = random.nextInt(100);
        if (chanceOfWinCoins < 30) {
            hasWonCoins = true;
        }
        return hasWonCoins;
    }

    private boolean hasWonFreeRound(){
        boolean hasWonFreeRound = false;
        int chanceOfWinFreeRound = random.nextInt(100);
        if (chanceOfWinFreeRound < 10) {
            hasWonFreeRound = true;
        }
        return hasWonFreeRound;
    }
}
