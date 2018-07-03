package com.test.kdb.model.entity;

import java.io.Serializable;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

@Entity
@NamedQueries({
  @NamedQuery(name = "News.findAll", query = "select o from News o Order by o.dateUpdate desc")
})
public class News implements Serializable {
    @Column(name="CALL_CENTER_YN")
    private Boolean callCenterYn;
    @Column(name="DATE_UPDATE")
    private Timestamp dateUpdate;
    @Column(name="INTRANET_YN")
    private Boolean intranetYn;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqNewsId")
    @SequenceGenerator(name="seqNewsId", sequenceName="NEWS_ID", allocationSize=1)
    @Column(name="NEWS_ID", nullable = false)
    private Long newsId;
    private String text;
    @Column(length = 500)
    private String title;
    @Column(name="WEB_YN")
    private Boolean webYn;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public News() {
    }

    public News(Boolean callCenterYn, Timestamp dateUpdate, Boolean intranetYn,
                Long newsId, String title, User user, Boolean webYn) {
        this.callCenterYn = callCenterYn;
        this.dateUpdate = dateUpdate;
        this.intranetYn = intranetYn;
        this.newsId = newsId;
        this.title = title;
        this.user = user;
        this.webYn = webYn;
    }

    public Boolean getCallCenterYn() {
        return callCenterYn;
    }

    public void setCallCenterYn(Boolean callCenterYn) {
        this.callCenterYn = callCenterYn;
    }

    public Timestamp getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Timestamp dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public Boolean getIntranetYn() {
        return intranetYn;
    }

    public void setIntranetYn(Boolean intranetYn) {
        this.intranetYn = intranetYn;
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Boolean getWebYn() {
        return webYn;
    }

    public void setWebYn(Boolean webYn) {
        this.webYn = webYn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof News)) {
            return false;
        }
        final News other = (News)object;
        if (!(newsId == null ? other.newsId == null : newsId.equals(other.newsId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((newsId == null) ? 0 : newsId.hashCode());
        return result;
    }
}
