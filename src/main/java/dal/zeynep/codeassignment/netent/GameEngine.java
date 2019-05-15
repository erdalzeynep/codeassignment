package dal.zeynep.codeassignment.netent;

import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
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
        try {

            Connection conn = DriverManager.getConnection("jdbc:h2:/Users/zeynepdal/test",
                    "zeyneperdal", "123456");
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root);
            query.where(builder.equal(root.get("id"), user.getId()));
            User dbUser = session.createQuery(query).uniqueResult();
            if (dbUser == null) {
                session.save(user);
                session.getTransaction().commit();
            } else {
                session.evict(dbUser);
                dbUser.setBalance(user.getBalance());
                session.update(dbUser);
                transaction.commit();
                session.close();
            }
            conn.close();
        } catch (Exception e) {

        }

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
