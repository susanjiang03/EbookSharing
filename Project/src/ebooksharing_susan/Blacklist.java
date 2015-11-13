/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ebooksharing_susan;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author lingshanjiang
 */
@Entity
@Table(name = "BLACKLIST", catalog = "", schema = "ROOT")
@NamedQueries({
    @NamedQuery(name = "Blacklist.findAll", query = "SELECT b FROM Blacklist b"),
    @NamedQuery(name = "Blacklist.findByBlid", query = "SELECT b FROM Blacklist b WHERE b.blid = :blid"),
    @NamedQuery(name = "Blacklist.findByEmail", query = "SELECT b FROM Blacklist b WHERE b.email = :email")})
public class Blacklist implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BLID")
    private Integer blid;
    @Column(name = "EMAIL")
    private String email;

    public Blacklist() {
    }

    public Blacklist(Integer blid) {
        this.blid = blid;
    }

    public Integer getBlid() {
        return blid;
    }

    public void setBlid(Integer blid) {
        Integer oldBlid = this.blid;
        this.blid = blid;
        changeSupport.firePropertyChange("blid", oldBlid, blid);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        String oldEmail = this.email;
        this.email = email;
        changeSupport.firePropertyChange("email", oldEmail, email);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (blid != null ? blid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Blacklist)) {
            return false;
        }
        Blacklist other = (Blacklist) object;
        if ((this.blid == null && other.blid != null) || (this.blid != null && !this.blid.equals(other.blid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ebooksharing.Blacklist[ blid=" + blid + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
