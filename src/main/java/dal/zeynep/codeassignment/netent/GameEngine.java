package dal.zeynep.codeassignment.netent;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
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

        printUserTable();

        return new GameRound(user.getId(), winningAmount, hasWonFreeRound);
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

    public void printUserTable() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);

        Root<User> root = query.from(User.class);
        query.select(root);

        List<User> userList = session.createQuery(query).getResultList();

        for (User user : userList) {
            System.out.println("ID : " + user.getId() + "     Balance : " + user.getBalance());
        }

    }
}
