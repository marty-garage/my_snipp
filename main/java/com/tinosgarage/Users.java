package com.tinosgarage;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "Users")
@Table(name = "Users")
public class Users implements Serializable {

    @Id
    private Long id;

    @OneToMany(mappedBy = "users")
    private List<Snip> snip = new ArrayList<Snip>();


    //private SnippetBook snips;

    @Column(name ="mail")
    private String mail;

    @Column(name = "pwd")
    private String pwd;

    public Users(){

    }
    public Users(String mail, String pwd) {
        this.mail = mail;
        this.pwd = pwd;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPwd(){
        return this.pwd;

    }
    public void setPwd(String pwd1){
        this.pwd = pwd1;
    }

    public Serializable getId() {
        return this.id;
    }



    public Collection<Snip> getSnip() {
        return snip;
    }

    public void setSnip(List<Snip> snip) {
        this.snip = snip;
    }

    public void setId(Long singleResult) {
        this.id = singleResult;
    }
}