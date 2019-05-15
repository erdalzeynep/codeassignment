package dal.zeynep.codeassignment.netent;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private final GameEngine engine = new GameEngine();

    @RequestMapping(value = "/play/{username}", method = RequestMethod.POST)
    public GameRound play(@PathVariable("username") String userName) {
        return engine.play(userName);
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public User getUser(@PathVariable("username") String userName) {
        return engine.getUser(userName);
    }

    @RequestMapping(value = "/gameround/{round_id}", method = RequestMethod.GET)
    public GameRound getGameRound(@PathVariable("round_id") String roundId) {
        return engine.getGameRound(roundId);
    }
}
