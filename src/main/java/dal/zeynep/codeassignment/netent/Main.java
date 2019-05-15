package dal.zeynep.codeassignment.netent;

import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:h2:/Users/zeynepdal/test", "zeyneperdal", "123456");

            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            User user = new User(10, true);

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

}
