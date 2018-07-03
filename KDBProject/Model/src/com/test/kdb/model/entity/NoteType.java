package com.test.kdb.model.entity;

import java.io.Serializable;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@NamedQueries({
  @NamedQuery(name = "NoteType.findAll", query = "select o from NoteType o Order by o.typeOfNote"),
  @NamedQuery(name = "NoteType.activeOrNot", query = "select o from NoteType o where o.activeYn=:active or :active is null Order by o.typeOfNote"),
  @NamedQuery(name = "NoteType.findActive", query = "select o from NoteType o where o.activeYn = :active")
})
@Table(name = "NOTE_TYPE")
public class NoteType implements Serializable {
    @Column(name="ACTIVE_YN")
    private Long activeYn;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqNoteTypeId")
    @SequenceGenerator(name="seqNoteTypeId", sequenceName="NOTE_TYPE_ID", allocationSize=1)
    @Column(name="NOTE_TYPE_ID", nullable = false)
    private Long noteTypeId;
    @Column(name="TYPE_OF_NOTE", length = 500)
    private String typeOfNote;
    @OneToMany(mappedBy = "noteType")
    private List<QaNote> qaNoteList;

    public NoteType() {
    }

    public NoteType(Boolean activeYn, Long noteTypeId, String typeOfNote) {
        if (activeYn == null)
            this.activeYn = 0L;
        else
            this.activeYn = activeYn ? 1L : 0L;
        this.noteTypeId = noteTypeId;
        this.typeOfNote = typeOfNote;
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

    public Long getNoteTypeId() {
        return noteTypeId;
    }

    public void setNoteTypeId(Long noteTypeId) {
        this.noteTypeId = noteTypeId;
    }

    public String getTypeOfNote() {
        return typeOfNote;
    }

    public void setTypeOfNote(String typeOfNote) {
        this.typeOfNote = typeOfNote;
    }

    public List<QaNote> getQaNoteList() {
        return qaNoteList;
    }

    public void setQaNoteList(List<QaNote> qaNoteList) {
        this.qaNoteList = qaNoteList;
    }

    public QaNote addQaNote(QaNote qaNote) {
        getQaNoteList().add(qaNote);
        qaNote.setNoteType(this);
        return qaNote;
    }

    public QaNote removeQaNote(QaNote qaNote) {
        getQaNoteList().remove(qaNote);
        qaNote.setNoteType(null);
        return qaNote;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof NoteType)) {
            return false;
        }
        final NoteType other = (NoteType)object;
        if (!(noteTypeId == null ? other.noteTypeId == null : noteTypeId.equals(other.noteTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((noteTypeId == null) ? 0 : noteTypeId.hashCode());
        return result;
    }
}
