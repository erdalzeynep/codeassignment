package dal.zeynep.codeassignment.netent;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class GameEngineTest {

    @Test
    public void shouldNeitherWinCoinsNorFreeRound() {
        Random random = Mockito.mock(Random.class);
        when(random.nextInt(100)).thenReturn(31, 11);
        User user = new User();
        GameEngine engine = new GameEngine(random);
        PlayResponse response = engine.play(user);
        assertFalse(response.hasWonCoins());
        assertFalse(response.hasWonFreeRound());
    }

    @Test
    public void shouldBothWinCoinsAndFreeRound() {
        Random random = Mockito.mock(Random.class);
        when(random.nextInt(100)).thenReturn(29, 9);
        User user = new User();
        GameEngine engine = new GameEngine(random);
        PlayResponse response = engine.play(user);
        assertTrue(response.hasWonCoins());
        assertTrue(response.hasWonFreeRound());
    }

    @Test
    public void shouldOnlyWinCoins() {
        Random random = Mockito.mock(Random.class);
        when(random.nextInt(100)).thenReturn(29, 11);
        User user = new User();
        GameEngine engine = new GameEngine(random);
        PlayResponse response = engine.play(user);
        assertTrue(response.hasWonCoins());
        assertFalse(response.hasWonFreeRound());
    }

    @Test
    public void shouldOnlyWinFreeRound() {
        Random random = Mockito.mock(Random.class);
        when(random.nextInt(100)).thenReturn(31, 9);
        User user = new User();
        GameEngine engine = new GameEngine(random);
        PlayResponse response = engine.play(user);
        assertFalse(response.hasWonCoins());
        assertTrue(response.hasWonFreeRound());
    }
}
