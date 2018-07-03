package com.test.kdb.model.entity;

import java.io.Serializable;

import java.util.List;
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
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="KDBGROUP")
@NamedQueries({
  @NamedQuery(name = "Group.findAll", query = "select o from Group o Order by o.name")
})
public class Group implements Serializable {
    @Column(name="ACTIVE_YN")
    private Long activeYn;
    @Column(length = 500)
    private String description;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqGroupId")
    @SequenceGenerator(name="seqGroupId", sequenceName="GROUP_ID", allocationSize=1)
    @Column(name="GROUP_ID", nullable = false)
    private Long groupId;
    @Column(length = 500)
    private String name;

    public Group() {
    }

    public Group(Boolean activeYn, String description, Long groupId,
                 String name) {
        if (activeYn == null)
            this.activeYn = 0L;
        else
            this.activeYn = activeYn ? 1L : 0L;
        this.description = description;
        this.groupId = groupId;
        this.name = name;
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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    /* ************************************************************************
     * for joing the tables (many-to-many)
     * Added by Igor Paunov
     *************************************************************************/
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "GROUP_USER",
            joinColumns = {
                @JoinColumn(name="GROUP_ID") 
            },
            inverseJoinColumns = {
                @JoinColumn(name="USER_ID")
            }
        )
    @OrderBy("username")
    private List<User> users;

    /**
    * @return 
    */
    public List<User> getUsers() {
    return users;
    }

    /**
    * @param 
    */
    public void setUsers(List<User> users) {
    this.users = users;
    }
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "GROUP_PERMISSION",
            joinColumns = {
                @JoinColumn(name = "GROUP_ID") 
            },
            inverseJoinColumns = {
                @JoinColumn(name = "PERMISSION_ID")
            }
        )
    @OrderBy("name")
    private List<Permission> permissions;

    /**
    * @return 
    */
    public List<Permission> getPermissions() {
    return permissions;
    }

    /**
    * @param 
    */
    public void setPermissions(List<Permission> permissions) {
    this.permissions = permissions;
    }
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "GROUP_CATEGORY",
            joinColumns = {
                @JoinColumn(name = "GROUP_ID") 
            },
            inverseJoinColumns = {
                @JoinColumn(name="CATEGORY_ID")
            }
        )
    @OrderBy("name")
    private List<Category> categories;

    /**
    * @return 
    */
    public List<Category> getCategories() {
    return categories;
    }

    /**
    * @param 
    */
    public void setCategories(List<Category> categories) {
    this.categories = categories;
    }
    /* ***********************************************************************/

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Group)) {
            return false;
        }
        final Group other = (Group)object;
        if (!(groupId == null ? other.groupId == null : groupId.equals(other.groupId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((groupId == null) ? 0 : groupId.hashCode());
        return result;
    }
}
