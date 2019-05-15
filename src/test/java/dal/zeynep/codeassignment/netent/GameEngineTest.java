package dal.zeynep.codeassignment.netent;

import org.hibernate.Session;
import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class GameEngineTest {

    @Test
    public void shouldNeitherWinCoinsNorFreeRound() {
        Random random = Mockito.mock(Random.class);
        when(random.nextInt(100)).thenReturn(31, 11);
        GameEngine engine = new GameEngine(random);
        GameRound gameRound = engine.play(UUID.randomUUID().toString());
        assertFalse(gameRound.isHasCoinsWon());
        assertFalse(gameRound.isHasFreeRoundWon());
    }

    @Test
    public void shouldBothWinCoinsAndFreeRound() {
        Random random = Mockito.mock(Random.class);
        when(random.nextInt(100)).thenReturn(29, 9);
        GameEngine engine = new GameEngine(random);
        GameRound gameRound = engine.play(UUID.randomUUID().toString());
        assertTrue(gameRound.isHasCoinsWon());
        assertTrue(gameRound.isHasFreeRoundWon());
    }

    @Test
    public void shouldOnlyWinCoins() {
        Random random = Mockito.mock(Random.class);
        when(random.nextInt(100)).thenReturn(29, 11);
        GameEngine engine = new GameEngine(random);
        GameRound gameRound = engine.play(UUID.randomUUID().toString());
        assertTrue(gameRound.isHasCoinsWon());
        assertFalse(gameRound.isHasFreeRoundWon());
    }

    @Test
    public void shouldOnlyWinFreeRound() {
        Random random = Mockito.mock(Random.class);
        when(random.nextInt(100)).thenReturn(31, 9);
        GameEngine engine = new GameEngine(random);
        GameRound gameRound = engine.play(UUID.randomUUID().toString());
        assertFalse(gameRound.isHasCoinsWon());
        assertTrue(gameRound.isHasFreeRoundWon());
    }

    @Test
    public void shouldSubstractTheRoundCostFromUserBalanceWhenNoWinning() {
        Random random1 = Mockito.mock(Random.class);
        when(random1.nextInt(100)).thenReturn(31, 11, 31, 11);
        GameEngine engine = new GameEngine(random1);
        String userName = UUID.randomUUID().toString();
        GameRound round1 = engine.play(userName);
        assertEquals((Object) (-10), round1.getWinningAmount());

        GameRound round2 = engine.play(userName);
        assertEquals((Object) (-10), round2.getWinningAmount());

        assertEquals((Object) (-20), getUser(userName).getBalance());
    }

    @Test
    public void shouldSubstractRoundCostFromWinningAmount() {
        Random random = Mockito.mock(Random.class);
        when(random.nextInt(100)).thenReturn(29, 11);
        GameEngine engine = new GameEngine(random);
        GameRound gameRound = engine.play(UUID.randomUUID().toString());
        assertEquals((Object) (10), gameRound.getWinningAmount());
    }

    @Test
    public void shouldCalculateWinningAmountAsZeroWhenNoWinningWithAFreeRound() {
        Random random = Mockito.mock(Random.class);
        when(random.nextInt(100)).thenReturn(31, 9, 31, 11);

        GameEngine engine = new GameEngine(random);

        String userName = UUID.randomUUID().toString();
        engine.play(userName);
        GameRound freeRound = engine.play(userName);

        assertEquals((Object) (0), freeRound.getWinningAmount());
    }

    @Test
    public void shouldGiveUserFreeRoundAfterAFreeRoundWinning() {
        Random random = Mockito.mock(Random.class);
        when(random.nextInt(100)).thenReturn(31, 9);
        GameEngine engine = new GameEngine(random);

        String userName = UUID.randomUUID().toString();
        engine.play(userName);

        assertTrue(getUser(userName).isHasFreeRound());
    }

    @Test
    public void shouldConsumeFreeRoundAfterAPlay() {
        Random random = Mockito.mock(Random.class);
        when(random.nextInt(100)).thenReturn(31, 9, 31, 11);
        GameEngine engine = new GameEngine(random);

        String userName = UUID.randomUUID().toString();
        engine.play(userName);
        engine.play(userName);

        assertFalse(getUser(userName).isHasFreeRound());
    }

    @Test
    public void shouldPersistUserAfterPlay() {
        Random random = Mockito.mock(Random.class);
        when(random.nextInt(100)).thenReturn(29, 11);
        String userName = UUID.randomUUID().toString();

        GameEngine engine = new GameEngine(random);
        engine.play(userName);

        User persistedUser = getUser(userName);

        assertNotNull(persistedUser);
        assertEquals((Object) 10, persistedUser.getBalance());
        assertEquals(userName, persistedUser.getName());
        assertFalse(persistedUser.isHasFreeRound());

    }

    @Test
    public void shouldPersistGameRoundAfterPlay() {
        Random random = Mockito.mock(Random.class);
        when(random.nextInt(100)).thenReturn(29, 11);
        String userName = UUID.randomUUID().toString();

        GameEngine engine = new GameEngine(random);
        GameRound gameRound = engine.play(userName);

        GameRound persistedRound = getGameRound(gameRound.getId());

        assertNotNull(persistedRound);
        assertEquals(gameRound.getId(), persistedRound.getId());
        assertEquals(gameRound.getWinningAmount(), persistedRound.getWinningAmount());
        assertFalse(persistedRound.isHasFreeRoundWon());

    }

    @Test
    public void shouldReturnCorrectGameRound() {
        Random random = Mockito.mock(Random.class);
        when(random.nextInt(100)).thenReturn(29, 11);
        String userName = UUID.randomUUID().toString();
        GameEngine engine = new GameEngine(random);
        GameRound gameRound = engine.play(userName);

        GameRound actualGameRound = engine.getGameRound(gameRound.getId());
        assertNotNull(actualGameRound);
        assertEquals(userName, actualGameRound.getUserName());
        assertEquals((Object) 10, actualGameRound.getWinningAmount());
        assertEquals(gameRound.getId(), actualGameRound.getId());
    }

    private User getUser(String userName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);

        Root<User> root = query.from(User.class);
        query.select(root).where(builder.equal(root.get("name"), userName));
        User user = session.createQuery(query).uniqueResult();
        session.close();
        return user;
    }

    private GameRound getGameRound(String id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<GameRound> query = builder.createQuery(GameRound.class);

        Root<GameRound> root = query.from(GameRound.class);
        query.select(root).where(builder.equal(root.get("id"), id));
        GameRound persistedRound = session.createQuery(query).uniqueResult();
        session.close();
        return persistedRound;
    }
}
