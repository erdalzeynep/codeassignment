package dal.zeynep.codeassignment.netent;

import java.util.UUID;

public class User {
    private String id;
    private boolean hasFreeRound;
    private Integer balance = 0;

    public User() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean hasFreeRound() {
        return hasFreeRound;
    }

    public void setHasFreeRound(boolean hasFreeRound) {
        this.hasFreeRound = hasFreeRound;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
