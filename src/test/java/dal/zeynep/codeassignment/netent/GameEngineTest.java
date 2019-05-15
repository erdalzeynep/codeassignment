package dal.zeynep.codeassignment.netent;

import org.hibernate.Session;
import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class GameEngineTest {

    @Test
    public void shouldNeitherWinCoinsNorFreeRound() {
        Random random = Mockito.mock(Random.class);
        when(random.nextInt(100)).thenReturn(31, 11);
        User user = new User();
        GameEngine engine = new GameEngine(random);
        GameRound gameRound = engine.play(user);
        assertFalse(gameRound.hasCoinsWon());
        assertFalse(gameRound.hasFreeRoundWon());
    }

    @Test
    public void shouldBothWinCoinsAndFreeRound() {
        Random random = Mockito.mock(Random.class);
        when(random.nextInt(100)).thenReturn(29, 9);
        User user = new User();
        GameEngine engine = new GameEngine(random);
        GameRound gameRound = engine.play(user);
        assertTrue(gameRound.hasCoinsWon());
        assertTrue(gameRound.hasFreeRoundWon());
    }

    @Test
    public void shouldOnlyWinCoins() {
        Random random = Mockito.mock(Random.class);
        when(random.nextInt(100)).thenReturn(29, 11);
        User user = new User();
        GameEngine engine = new GameEngine(random);
        GameRound gameRound = engine.play(user);
        assertTrue(gameRound.hasCoinsWon());
        assertFalse(gameRound.hasFreeRoundWon());
    }

    @Test
    public void shouldOnlyWinFreeRound() {
        Random random = Mockito.mock(Random.class);
        when(random.nextInt(100)).thenReturn(31, 9);
        User user = new User();
        GameEngine engine = new GameEngine(random);
        GameRound gameRound = engine.play(user);
        assertFalse(gameRound.hasCoinsWon());
        assertTrue(gameRound.hasFreeRoundWon());
    }

    @Test
    public void shouldSubstractTheRoundCostFromUserBalanceWhenNoWinning() {
        Random random1 = Mockito.mock(Random.class);
        when(random1.nextInt(100)).thenReturn(31, 11, 31, 11);
        User user = new User();
        GameEngine engine = new GameEngine(random1);

        GameRound round1 = engine.play(user);
        assertEquals((Object) (-10), round1.getWinningAmount());

        GameRound round2 = engine.play(user);
        assertEquals((Object) (-10), round2.getWinningAmount());

        assertEquals((Object) (-20), user.getBalance());
    }

    @Test
    public void shouldSubstractRoundCostFromWinningAmount() {
        Random random = Mockito.mock(Random.class);
        when(random.nextInt(100)).thenReturn(29, 11);
        User user = new User();
        GameEngine engine = new GameEngine(random);
        GameRound gameRound = engine.play(user);
        assertEquals((Object) (10), gameRound.getWinningAmount());
    }

    @Test
    public void shouldCalculateWinningAmountAsZeroWhenNoWinningWithAFreeRound() {
        Random random = Mockito.mock(Random.class);
        when(random.nextInt(100)).thenReturn(31, 11);
        User user = new User();
        user.setHasFreeRound(true);
        GameEngine engine = new GameEngine(random);

        GameRound gameRound = engine.play(user);
        assertEquals((Object) (0), gameRound.getWinningAmount());
    }

    @Test
    public void shouldConsumeFreeRoundAfterAPlay() {
        User user = new User();
        user.setHasFreeRound(true);
        GameEngine engine = new GameEngine();
        engine.play(user);
        assertFalse(user.hasFreeRound());
    }

    @Test
    public void shouldGiveUserFreeRoundAfterAFreeRoundWinning() {
        Random random = Mockito.mock(Random.class);
        when(random.nextInt(100)).thenReturn(31, 9);
        User user = new User();
        GameEngine engine = new GameEngine(random);
        engine.play(user);
        assertTrue(user.hasFreeRound());
    }

    @Test
    public void shouldPersistUser() {
        Random random = Mockito.mock(Random.class);
        when(random.nextInt(100)).thenReturn(29, 11);
        User user = new User();
        user.setBalance(100);
        user.setHasFreeRound(true);
        GameEngine engine = new GameEngine(random);
        engine.play(user);

        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);

        Root<User> root = query.from(User.class);
        query.select(root).where(builder.equal(root.get("id"), user.getId()));
        User persistedUser = session.createQuery(query).uniqueResult();

        assertNotNull(persistedUser);
        assertEquals(user.getBalance(), persistedUser.getBalance());
        assertEquals(user.getId(), persistedUser.getId());

        session.close();

    }

    @Test
    public void shouldPersistGameRound() {
        Random random = Mockito.mock(Random.class);
        when(random.nextInt(100)).thenReturn(29, 11);
        User user = new User();
        user.setBalance(100);
        user.setHasFreeRound(false);
        GameEngine engine = new GameEngine(random);
        GameRound gameRound = engine.play(user);

        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<GameRound> query = builder.createQuery(GameRound.class);

        Root<GameRound> root = query.from(GameRound.class);
        query.select(root).where(builder.equal(root.get("id"), gameRound.getId()));
        GameRound persistedRound = session.createQuery(query).uniqueResult();

        assertNotNull(persistedRound);
        assertEquals(gameRound.getId() ,persistedRound.getId());
        assertEquals(gameRound.getWinningAmount(), persistedRound.getWinningAmount());

        session.close();

    }
}
