package pl.bartekk.revoluttask.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    @Getter
    private String name;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Getter
    @Setter
    private Account account;

    public User() {
    }

    public User(String name) {
        this.name = name;
        this.account = new Account();
    }
}