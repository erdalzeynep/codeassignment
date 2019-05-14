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

    public GameRound play(User user) {
        boolean hasWonCoins = hasWonCoins();
        boolean hasWonFreeRound = hasWonFreeRound();
        boolean hasFreePlay = user.hasFreeRound();
        int winningAmount = 0;

        if (!hasFreePlay) {
            winningAmount -= 10;
        } else {
            user.setHasFreeRound(false);
        }

        if (hasWonCoins) {
            winningAmount += 20;
        }

        if (hasWonFreeRound) {
            user.setHasFreeRound(true);
        }
        user.setBalance(user.getBalance() + winningAmount);

        return new GameRound(user.getId(), winningAmount, hasWonFreeRound);
    }

    private boolean hasWonCoins() {
        return random.nextInt(100) < 30;
    }

    private boolean hasWonFreeRound() {
        return random.nextInt(100) < 10;
    }
}
