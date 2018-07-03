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
  @NamedQuery(name = "Config.findAll", query = "select o from Config o"),
  @NamedQuery(name = "Config.findByKey", query = "select o from Config o where o.key = :key ")
})
@Table(name = "CONFIG")

public class Config implements Serializable {
    @Id
    @Column(name="KEY", nullable = false)
    private String key;
    @Column(name="VALUE", nullable = false)
    private String value;

    public Config() {
    }

    public Config(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
