package dal.zeynep.codeassignment.netent;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

    public GameRound play(String userName) {
        User user = getOrCreateUser(userName);
        boolean hasWonCoins = hasWonCoins();
        boolean hasWonFreeRound = hasWonFreeRound();
        boolean hasFreePlay = user.isHasFreeRound();
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

        GameRound gameRound = new GameRound(user.getName(), winningAmount, hasWonFreeRound);
        persistGameRound(gameRound);

        return gameRound;
    }

    public GameRound getGameRound(String roundId) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<GameRound> query = builder.createQuery(GameRound.class);

        Root<GameRound> root = query.from(GameRound.class);
        query.select(root).where(builder.equal(root.get("id"), roundId));
        return session.createQuery(query).uniqueResult();
    }

    private User getOrCreateUser(String userName) {
        User user = getUser(userName);
        if (user == null) {
            user = new User(userName);
            user.setHasFreeRound(false);
            user.setBalance(0);
        }
        return user;
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

    public User getUser(String userName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);

        Root<User> root = query.from(User.class);
        query.select(root).where(builder.equal(root.get("name"), userName));
        User user = session.createQuery(query).uniqueResult();
        session.close();
        return user;
    }
}
