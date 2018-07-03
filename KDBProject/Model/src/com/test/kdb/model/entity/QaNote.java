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
import javax.persistence.Table;

@Entity
@NamedQueries({
  @NamedQuery(name = "QaNote.findAll", query = "select o from QaNote o Order by o.dateUpdate desc")
})
@Table(name = "QA_NOTE")
public class QaNote implements Serializable {
    @Column(name="ACTIVE_YN")
    private Long activeYn;
    @Column(name="DATE_UPDATE")
    private Timestamp dateUpdate;
    @Column(name="PRIVATE_YN")
    private Boolean privateYn;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqQaNoteId")
    @SequenceGenerator(name="seqQaNoteId", sequenceName="QA_NOTE_ID", allocationSize=1)
    @Column(name="QA_NOTE_ID", nullable = false)
    private Long qaNoteId;
    private String text;
    @ManyToOne
    @JoinColumn(name = "USER_ID1")
    private User user;
    @ManyToOne
    @JoinColumn(name = "QA_ID")
    private Qa qa;
    @ManyToOne
    @JoinColumn(name = "NOTE_TYPE_ID")
    private NoteType noteType;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user1;

    public QaNote() {
    }

    public QaNote(Long activeYn, Timestamp dateUpdate, NoteType noteType,
                  Boolean privateYn, Qa qa, Long qaNoteId, User user1,
                  User user) {
        this.activeYn = activeYn;
        this.dateUpdate = dateUpdate;
        this.noteType = noteType;
        this.privateYn = privateYn;
        this.qa = qa;
        this.qaNoteId = qaNoteId;
        this.user1 = user1;
        this.user = user;
    }

    public Boolean getActiveYn() {
        if (this.activeYn == null)
            return false;
        return (this.activeYn == 1) ? true : false;
    }

    public void setActiveYn(Boolean activeYn) {
        if (activeYn == null)
            this.activeYn = 0L;
        else
            this.activeYn = activeYn ? 1L : 0L;
    }

    public Timestamp getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Timestamp dateUpdate) {
        this.dateUpdate = dateUpdate;
    }


    public Boolean getPrivateYn() {
        return privateYn;
    }

    public void setPrivateYn(Boolean privateYn) {
        this.privateYn = privateYn;
    }


    public Long getQaNoteId() {
        return qaNoteId;
    }

    public void setQaNoteId(Long qaNoteId) {
        this.qaNoteId = qaNoteId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Qa getQa() {
        return qa;
    }

    public void setQa(Qa qa) {
        this.qa = qa;
    }

    public NoteType getNoteType() {
        return noteType;
    }

    public void setNoteType(NoteType noteType) {
        this.noteType = noteType;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof QaNote)) {
            return false;
        }
        final QaNote other = (QaNote)object;
        if (!(qaNoteId == null ? other.qaNoteId == null : qaNoteId.equals(other.qaNoteId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((qaNoteId == null) ? 0 : qaNoteId.hashCode());
        return result;
    }
}
