/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ebooksharing1;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.swing.ImageIcon;

/**
 *
 * @author indrajit
 */
@Entity
@Table(name = "BOOKINFO", catalog = "", schema = "JAVA")
@NamedQueries({
    @NamedQuery(name = "Bookinfo.findAll", query = "SELECT b FROM Bookinfo b"),
    @NamedQuery(name = "Bookinfo.findByBookid", query = "SELECT b FROM Bookinfo b WHERE b.bookid = :bookid"),
    @NamedQuery(name = "Bookinfo.findByBookname", query = "SELECT b FROM Bookinfo b WHERE b.bookname = :bookname"),
    @NamedQuery(name = "Bookinfo.findByAuthor", query = "SELECT b FROM Bookinfo b WHERE b.author = :author"),
    @NamedQuery(name = "Bookinfo.findBySummary", query = "SELECT b FROM Bookinfo b WHERE b.summary = :summary"),
    @NamedQuery(name = "Bookinfo.findByUploader", query = "SELECT b FROM Bookinfo b WHERE b.uploader = :uploader"),
    @NamedQuery(name = "Bookinfo.findByAwardPoints", query = "SELECT b FROM Bookinfo b WHERE b.awardPoints = :awardPoints"),
    @NamedQuery(name = "Bookinfo.findByReadingPoint", query = "SELECT b FROM Bookinfo b WHERE b.readingPoint = :readingPoint"),
    @NamedQuery(name = "Bookinfo.findByReadCounts", query = "SELECT b FROM Bookinfo b WHERE b.readCounts = :readCounts"),
    @NamedQuery(name = "Bookinfo.findByLastDateRead", query = "SELECT b FROM Bookinfo b WHERE b.lastDateRead = :lastDateRead"),
    @NamedQuery(name = "Bookinfo.findByRating", query = "SELECT b FROM Bookinfo b WHERE b.rating = :rating"),
    @NamedQuery(name = "Bookinfo.findByRatingCounts", query = "SELECT b FROM Bookinfo b WHERE b.ratingCounts = :ratingCounts")})
public class Bookinfo implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BOOKID")
    private Short bookid;
    @Basic(optional = false)
    @Column(name = "BOOKNAME")
    private String bookname;
    @Basic(optional = false)
    @Lob
    @Column(name = "COVER")
    private byte[] cover;
    
    public ImageIcon getImage(){
        return new ImageIcon(new ImageIcon(cover).getImage().getScaledInstance(100, 150, java.awt.Image.SCALE_SMOOTH));
    }
    @Column(name = "AUTHOR")
    private String author;
    @Column(name = "SUMMARY")
    private String summary;
    @Basic(optional = false)
    @Lob
    @Column(name = "BOOKFILE")
    private byte[] bookfile;
    
//    public ImageIcon getImage(){
//        return new ImageIcon(new ImageIcon(bookfile).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH));
//    }
    @Basic(optional = false)
    @Column(name = "UPLOADER")
    private String uploader;
    @Basic(optional = false)
    @Column(name = "AWARD_POINTS")
    private short awardPoints;
    @Basic(optional = false)
    @Column(name = "READING_POINT")
    private short readingPoint;
    @Column(name = "READ_COUNTS")
    private Short readCounts;
    @Column(name = "LAST_DATE_READ")
    @Temporal(TemporalType.DATE)
    private Date lastDateRead;
    @Column(name = "RATING")
    private Short rating;
    @Column(name = "RATING_COUNTS")
    private Short ratingCounts;

    public Bookinfo() {
    }

    public Bookinfo(Short bookid) {
        this.bookid = bookid;
    }

    public Bookinfo(Short bookid, String bookname, byte[] cover, byte[] bookfile, String uploader, short awardPoints, short readingPoint) {
        this.bookid = bookid;
        this.bookname = bookname;
        this.cover = cover;
        this.bookfile = bookfile;
        this.uploader = uploader;
        this.awardPoints = awardPoints;
        this.readingPoint = readingPoint;
    }

    public Short getBookid() {
        return bookid;
    }

    public void setBookid(Short bookid) {
        Short oldBookid = this.bookid;
        this.bookid = bookid;
        changeSupport.firePropertyChange("bookid", oldBookid, bookid);
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        String oldBookname = this.bookname;
        this.bookname = bookname;
        changeSupport.firePropertyChange("bookname", oldBookname, bookname);
    }

    public Serializable getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        Serializable oldCover = this.cover;
        this.cover = cover;
        changeSupport.firePropertyChange("cover", oldCover, cover);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        String oldAuthor = this.author;
        this.author = author;
        changeSupport.firePropertyChange("author", oldAuthor, author);
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        String oldSummary = this.summary;
        this.summary = summary;
        changeSupport.firePropertyChange("summary", oldSummary, summary);
    }

    public Serializable getBookfile() {
        return bookfile;
    }

    public void setBookfile(byte[] bookfile) {
        Serializable oldBookfile = this.bookfile;
        this.bookfile = bookfile;
        changeSupport.firePropertyChange("bookfile", oldBookfile, bookfile);
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        String oldUploader = this.uploader;
        this.uploader = uploader;
        changeSupport.firePropertyChange("uploader", oldUploader, uploader);
    }

    public short getAwardPoints() {
        return awardPoints;
    }

    public void setAwardPoints(short awardPoints) {
        short oldAwardPoints = this.awardPoints;
        this.awardPoints = awardPoints;
        changeSupport.firePropertyChange("awardPoints", oldAwardPoints, awardPoints);
    }

    public short getReadingPoint() {
        return readingPoint;
    }

    public void setReadingPoint(short readingPoint) {
        short oldReadingPoint = this.readingPoint;
        this.readingPoint = readingPoint;
        changeSupport.firePropertyChange("readingPoint", oldReadingPoint, readingPoint);
    }

    public Short getReadCounts() {
        return readCounts;
    }

    public void setReadCounts(Short readCounts) {
        Short oldReadCounts = this.readCounts;
        this.readCounts = readCounts;
        changeSupport.firePropertyChange("readCounts", oldReadCounts, readCounts);
    }

    public Date getLastDateRead() {
        return lastDateRead;
    }

    public void setLastDateRead(Date lastDateRead) {
        Date oldLastDateRead = this.lastDateRead;
        this.lastDateRead = lastDateRead;
        changeSupport.firePropertyChange("lastDateRead", oldLastDateRead, lastDateRead);
    }

    public Short getRating() {
        return rating;
    }

    public void setRating(Short rating) {
        Short oldRating = this.rating;
        this.rating = rating;
        changeSupport.firePropertyChange("rating", oldRating, rating);
    }

    public Short getRatingCounts() {
        return ratingCounts;
    }

    public void setRatingCounts(Short ratingCounts) {
        Short oldRatingCounts = this.ratingCounts;
        this.ratingCounts = ratingCounts;
        changeSupport.firePropertyChange("ratingCounts", oldRatingCounts, ratingCounts);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookid != null ? bookid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bookinfo)) {
            return false;
        }
        Bookinfo other = (Bookinfo) object;
        if ((this.bookid == null && other.bookid != null) || (this.bookid != null && !this.bookid.equals(other.bookid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ebooksharing1.Bookinfo[ bookid=" + bookid + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
