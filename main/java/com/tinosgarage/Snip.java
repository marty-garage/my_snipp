package com.tinosgarage;

import org.apache.wicket.util.io.IClusterable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * Simple "POJO" bean for guestbook comments.
 *
 * @author Jonathan Locke
 */
@Entity(name = "Snip")
@Table(name = "Snip")
public class Snip implements IClusterable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //private Long users_id;

    @ManyToOne
    @JoinColumn(name="fk_user")
    private Users users;

    @Column(name = "code")
    private String code;

    @Column(name = "date")
    private Date date;// = new Date();

    /**
     * Constructor
     */
    public Snip()
    {
    }

    /**
     * Copy constructor
     *
     * @param snip
     *
     */
    public Snip(final Snip snip)
    {
        this.code = snip.code;
        this.date = snip.date;
    }

    /**
     * @return Returns the text.
     */
    public String getCode()
    {
        return code;
    }

    /**
     * @param text
     *            The text to set.
     */
    public void setCode(String text)
    {
        this.code = text;
    }

    /**
     * @return Returns the date.
     */
    public Date getDate()
    {
        return date;
    }

    /**
     * @param date
     *            The date to set.
     */
    public void setDate(Date date)
    {
        this.date = date;
    }

    @Override
    public String toString()
    {
        return "[Snip date = " + date + ", text = " + code + "]";
    }

    public void setUser(Serializable user) {
        this.users = (Users)user;
    }
}
