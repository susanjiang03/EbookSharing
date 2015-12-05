/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ebooksharing1;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.math.BigInteger;
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
    @NamedQuery(name = "Bookinfo.findByRewardPoints", query = "SELECT b FROM Bookinfo b WHERE b.rewardPoints = :rewardPoints"),
    @NamedQuery(name = "Bookinfo.findByReadingPoints", query = "SELECT b FROM Bookinfo b WHERE b.readingPoints = :readingPoints"),
    @NamedQuery(name = "Bookinfo.findByReadingCounts", query = "SELECT b FROM Bookinfo b WHERE b.readingCounts = :readingCounts"),
    @NamedQuery(name = "Bookinfo.findByLastDateRead", query = "SELECT b FROM Bookinfo b WHERE b.lastDateRead = :lastDateRead"),
    @NamedQuery(name = "Bookinfo.findByReadingTotalDuration", query = "SELECT b FROM Bookinfo b WHERE b.readingTotalDuration = :readingTotalDuration"),
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
    private Integer bookid;
    @Column(name = "BOOKNAME")
    private String bookname;
    @Lob
    @Column(name = "COVER")
    private byte[] cover;
    //private Serializable cover;

    public ImageIcon getImage(){
        
        return new ImageIcon(new ImageIcon(cover).getImage().getScaledInstance(100, 150, java.awt.Image.SCALE_SMOOTH));

    }
    @Column(name = "AUTHOR")
    private String author;
    @Column(name = "SUMMARY")
    private String summary;
    @Lob
    @Column(name = "BOOKFILE")
    //private Serializable bookfile;
    private byte[] bookfile;

    @Column(name = "UPLOADER")
    private String uploader;
    @Column(name = "REWARD_POINTS")
    private Short rewardPoints;
    @Column(name = "READING_POINTS")
    private Short readingPoints;
    @Column(name = "READING_COUNTS")
    private Short readingCounts;
    @Column(name = "LAST_DATE_READ")
    @Temporal(TemporalType.DATE)
    private Date lastDateRead;
    @Column(name = "READING_TOTAL_DURATION")
    private BigInteger readingTotalDuration;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "RATING")
    private Double rating;
    @Column(name = "RATING_COUNTS")
    private Short ratingCounts;

    public Bookinfo() {
    }

    public Bookinfo(Integer bookid) {
        this.bookid = bookid;
    }

    public Integer getBookid() {
        return bookid;
    }

    public void setBookid(Integer bookid) {
        Integer oldBookid = this.bookid;
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
    //originally Serializable converted to byte[]
    public byte[] getCover() {
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

    public Short getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(Short rewardPoints) {
        Short oldRewardPoints = this.rewardPoints;
        this.rewardPoints = rewardPoints;
        changeSupport.firePropertyChange("rewardPoints", oldRewardPoints, rewardPoints);
    }

    public Short getReadingPoints() {
        return readingPoints;
    }

    public void setReadingPoints(Short readingPoints) {
        Short oldReadingPoints = this.readingPoints;
        this.readingPoints = readingPoints;
        changeSupport.firePropertyChange("readingPoints", oldReadingPoints, readingPoints);
    }

    public Short getReadingCounts() {
        return readingCounts;
    }

    public void setReadingCounts(Short readingCounts) {
        Short oldReadingCounts = this.readingCounts;
        this.readingCounts = readingCounts;
        changeSupport.firePropertyChange("readingCounts", oldReadingCounts, readingCounts);
    }

    public Date getLastDateRead() {
        return lastDateRead;
    }

    public void setLastDateRead(Date lastDateRead) {
        Date oldLastDateRead = this.lastDateRead;
        this.lastDateRead = lastDateRead;
        changeSupport.firePropertyChange("lastDateRead", oldLastDateRead, lastDateRead);
    }

    public BigInteger getReadingTotalDuration() {
        return readingTotalDuration;
    }

    public void setReadingTotalDuration(BigInteger readingTotalDuration) {
        BigInteger oldReadingTotalDuration = this.readingTotalDuration;
        this.readingTotalDuration = readingTotalDuration;
        changeSupport.firePropertyChange("readingTotalDuration", oldReadingTotalDuration, readingTotalDuration);
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        Double oldRating = this.rating;
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
