package com.tinosgarage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class _EntityManager {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("manager1");

    public javax.persistence.EntityManager getEm() {
        return em;
    }

    private javax.persistence.EntityManager em;

    public _EntityManager(){
        em = emf.createEntityManager();
    }

    public static <T> T _getSingleResult(TypedQuery<T> query) {
        query.setMaxResults(1);
        List<T> list = query.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }
}

//SELECT s.*, users_id FROM Snip AS s INNER JOIN Users ON (s.id = Users.id)


  //  SELECT DISTINCT s, users FROM Snip s, IN (s.users) as user where user.id = :userID

/*

CREATE TABLE Snip(
  id INT(10)   PRIMARY KEY ,
  code VARCHAR(380),
  time DATE,
  INDEX usr_ind (id),
  FOREIGN KEY (id)
  REFERENCES Users(id)
    ON UPDATE CASCADE ON DELETE RESTRICT



    @Entity
public class Snip {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_order")
    private Users users;

    ...
}

@Entity
public class Users {

    @OneToMany(mappedBy = "users")
    private List<Item> items = new ArrayList<Item>();

    ...
)
 */