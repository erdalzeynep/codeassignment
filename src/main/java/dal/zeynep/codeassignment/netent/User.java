package dal.zeynep.codeassignment.netent;

import javax.persistence.*;

@Entity
@Table
public class User {
    @Id
    @Column
    private String name;

    @Column
    private boolean hasFreeRound;

    @Column
    private Integer balance = 0;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHasFreeRound() {
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
