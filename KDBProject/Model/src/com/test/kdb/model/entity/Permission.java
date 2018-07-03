package com.test.kdb.model.entity;

import java.io.Serializable;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

@Entity
@NamedQueries({
  @NamedQuery(name = "Permission.findAll", query = "select o from Permission o Order by o.name")
})
public class Permission implements Serializable {
    @Column(name="ACTIVE_YN")
    private Long activeYn;
    @Column(length = 500)
    private String description;
    @Column(nullable = false, unique = true, length = 200)
    private String name;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqPermissionId")
    @SequenceGenerator(name="seqPermissionId", sequenceName="PERMISSION_ID", allocationSize=1)
    @Column(name="PERMISSION_ID", nullable = false)
    private Long permissionId;

    public Permission() {
    }

    public Permission(Long activeYn, String description, String name,
                      Long permissionId) {
        this.activeYn = activeYn;
        this.description = description;
        this.name = name;
        this.permissionId = permissionId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    /* ************************************************************************
     * for joing the tables (many-to-many)
     * Added by Igor Paunov
     *************************************************************************/
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "GROUP_PERMISSION",
    joinColumns = {
    @JoinColumn(name="PERMISSION_ID") 
    },
    inverseJoinColumns = {
    @JoinColumn(name="GROUP_ID")
    }
    )
    private Set<Group> groups;

    /**
    * @return 
    */
    public Set<Group> getGroups() {
    return groups;
    }

    /**
    * @param 
    */
    public void setGroups(Set<Group> groups) {
    this.groups = groups;
    }
    /* ***********************************************************************/

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Permission)) {
            return false;
        }
        final Permission other = (Permission)object;
        if (!(permissionId == null ? other.permissionId == null : permissionId.equals(other.permissionId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((permissionId == null) ? 0 : permissionId.hashCode());
        return result;
    }
}
