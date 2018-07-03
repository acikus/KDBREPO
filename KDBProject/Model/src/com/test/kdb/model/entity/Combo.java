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
  @NamedQuery(name = "Combo.findAll", query = "select o from Combo o")
})
@Table(name = "Combo")

public class Combo implements Serializable {
    @Id
    @Column(name="COMBO_ID", nullable = false)
    private String comboId;
    @Column(name="VALUE", nullable = false)
    private String value;
    @Column(name="DESCRIPTION", nullable = true)
    private String description;
    @Column(name="LIST_ORDER", nullable = true)
    private String listOrder;

    public Combo() {
    }

    public Combo(String comboId, String value) {
        this.comboId = comboId;
        this.value = value;
    }

    public void setComboId(String comboId) {
        this.comboId = comboId;
    }

    public String getComboId() {
        return comboId;
    }
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setListOrder(String listOrder) {
        this.listOrder = listOrder;
    }

    public String getListOrder() {
        return listOrder;
    }

}
