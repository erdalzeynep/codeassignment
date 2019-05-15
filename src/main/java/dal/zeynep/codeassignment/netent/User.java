package dal.zeynep.codeassignment.netent;

import javax.persistence.*;

@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private String id;

    @Column(name="has_free_round")
    private boolean hasFreeRound;

    @Column(name="balance")
    private Integer balance = 0;

    public User() {
    }

    public User(Integer balance, boolean hasFreeRound) {
        this.balance = balance;
        this.hasFreeRound = hasFreeRound;
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
