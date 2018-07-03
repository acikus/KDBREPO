package com.test.kdb.model.entity;

import java.io.Serializable;

import java.util.ArrayList;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.QueryHint;

import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheType;
import org.eclipse.persistence.config.QueryHints;

@Entity
@Cache(type = CacheType.NONE)
@NamedQueries({
  @NamedQuery(name = "Category.findAll", 
        query = "select o from Category o Order by o.categoryId"),  
  @NamedQuery(name = "Category.findById", 
        query = "select o from Category o where o.categoryId = :id ",
        hints = { @QueryHint(name = QueryHints.LEFT_FETCH, value = "o.categoryList") }),
  @NamedQuery(name = "Category.activeChildCategories", 
        query = "select o from Category o where o.activeYn = 1 and ((:parent is null and o.category is null ) or o.category.categoryId = :parent ) Order by o.categoryId"),
  @NamedQuery(name = "Category.findActiveCategories", 
        query = "select o from Category o where o.activeYn = 1 Order by o.categoryId"),
  @NamedQuery(name = "Category.findActive", 
        query = "select o from Category o where o.activeYn = :active Order by o.categoryId"),
  @NamedQuery(name = "Category.findDependActiveCategories", 
        query = "select o from Category o where o.activeYn = :Active or ( :Active is null ) or ( :Active = 2 ) Order by o.categoryId" ,
        hints = { @QueryHint(name = QueryHints.LEFT_FETCH, value = "o.categoryList") }  ) 
})
/*
 * query = "select o from Category o where o.category.categoryId = :parent or (:parent is null and o.category is null)")
 */
public class Category implements Serializable {
    @Column(name="ACTIVE_YN")
    private Long activeYn;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqCategoryId")
    @SequenceGenerator(name="seqCategoryId", sequenceName="CATEGORY_ID", allocationSize=1)
    @Column(name="CATEGORY_ID", nullable = false)
    private Long categoryId;
    @Column(length = 500)
    private String description;
    @Column(length = 500)
    private String name;
    /* this is parent cetegory - attribute name 'category' is a misnomer */
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    @OrderBy("categoryId")
    private Category category;
    @OneToMany(mappedBy = "category")
    @OrderBy("categoryId")
    private List<Category> categoryList;
    @Transient
    private String strDisplayName;
    public Category() {
         
    }

    public Category(Long activeYn, Long categoryId, String description,
                    String name, Category category) {
        this.activeYn = activeYn;
        this.categoryId = categoryId;
        this.description = description;
        this.name = name;
        this.category = category;
    }

    public Category(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getActiveYn() {
        return activeYn;
    }

    public void setActiveYn(Long activeYn) {
        this.activeYn = activeYn;
        
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public Category addCategory(Category category) {
        getCategoryList().add(category);
        category.setCategory(this);
        return category;
    }

    public Category removeCategory(Category category) {
        getCategoryList().remove(category);
        category.setCategory(null);
        return category;
    }
    
    /* ************************************************************************
     * for joing the tables (many-to-many)
     * Added by Igor Paunov
     *************************************************************************/
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "DOCUMENT_CATEGORY",
        joinColumns = {
            @JoinColumn(name = "CATEGORY_ID") 
        },
        inverseJoinColumns = {
            @JoinColumn(name = "DOCUMENT_ID")
        }
    )
    @OrderBy("counter DESC") 
    private List<Document> documents;

    /**
    * @return 
    */
    public List<Document> getDocuments() {
    return documents;
    }

    /**
    * @param 
    */
    public void setDocuments(List<Document> documents) {
    this.documents = documents;
    }
        
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "CATEGORY_QA",
            joinColumns = {
                @JoinColumn(name = "CATEGORY_ID") 
            },
            inverseJoinColumns = {
                @JoinColumn(name = "QA_ID")
            }
        )
    @OrderBy("counter DESC") 
    private List<Qa> qas;

    /**
    * @return 
    */
    public List<Qa> getQas() {
    return qas;
    }

    /**
    * @param 
    */
    public void setQas(List<Qa> qas) {
    this.qas = qas;
    }
    
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "GROUP_CATEGORY",
            joinColumns = {
                @JoinColumn(name = "CATEGORY_ID") 
            },
            inverseJoinColumns = {
                @JoinColumn(name = "GROUP_ID")
            }
        )
    private List<Group> groups = new ArrayList<Group>();

    /**
    * @return 
    */
    public List<Group> getGroups() {
    return groups;
    }

    /**
    * @param 
    */
    public void setGroups(List<Group> groups) {
    this.groups = groups;
    }
    /* ***********************************************************************/

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Category)) {
            return false;
        }
        final Category other = (Category)object;
        if (!(categoryId == null ? other.categoryId == null : categoryId.equals(other.categoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((categoryId == null) ? 0 : categoryId.hashCode());
        return result;
    }

    public void setStrDisplayName(String strActive) {
        this.strDisplayName = strActive;
    }

    public String getStrDisplayName() {
        return strDisplayName;
    }
}
