package dal.zeynep.codeassignment.netent;

import javax.persistence.*;

@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private String id;

    @Column
    private boolean hasFreeRound;

    @Column
    private Integer balance = 0;

    public User() {
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
