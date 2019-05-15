package dal.zeynep.codeassignment.netent;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Random;

public class GameEngine {

    private Random random;
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

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
        persistUser(user);

        GameRound gameRound = new GameRound(user.getId(), winningAmount, hasWonFreeRound);
        persistGameRound(gameRound);
        return gameRound;
    }

    private boolean hasWonCoins() {
        return random.nextInt(100) < 30;
    }

    private boolean hasWonFreeRound() {
        return random.nextInt(100) < 10;
    }

    private void persistUser(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(user);
        session.getTransaction().commit();
        session.close();
    }

    private void persistGameRound(GameRound gameRound) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(gameRound);
        session.getTransaction().commit();
        session.close();
    }
}
