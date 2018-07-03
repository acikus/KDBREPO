package com.test.kdb.model;

//import java.rmi.Remote;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;
//import javax.ejb.Remote;
import javax.ejb.Stateless;

import javax.ejb.TransactionAttribute;

import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.test.kdb.model.entity.Category;
import com.test.kdb.model.entity.DocExtension;
import com.test.kdb.model.entity.DocSource;
import com.test.kdb.model.entity.DocType;
import com.test.kdb.model.entity.Document;
import com.test.kdb.model.utils.DocumentChunk;
import com.test.kdb.model.entity.DocumentstatsWeb;
import com.test.kdb.model.entity.Email;
import com.test.kdb.model.entity.ErrorLog;
import com.test.kdb.model.entity.ExternalLink;
import com.test.kdb.model.entity.Group;
import com.test.kdb.model.entity.Language;
import com.test.kdb.model.entity.LogDoc;
import com.test.kdb.model.entity.LogEmail;
import com.test.kdb.model.entity.LogQa;
import com.test.kdb.model.entity.News;
import com.test.kdb.model.entity.NoteType;
import com.test.kdb.model.entity.Permission;
import com.test.kdb.model.entity.Qa;
import com.test.kdb.model.entity.QaNote;
import com.test.kdb.model.entity.QastatsWeb;
import com.test.kdb.model.entity.User;

@Stateless(name = "SessionEJB", mappedName = "KDB-Model-SessionEJB")
//@Remote
@Local
public class SessionEJBBean implements  SessionEJBLocal {
    @PersistenceContext(unitName="Model")
    private EntityManager em;

    public SessionEJBBean() {
    }

    public Object queryByRange(String jpqlStmt, int firstResult,
                               int maxResults) {
        Query query = em.createQuery(jpqlStmt);
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    public Email persistEmail(Email email) {
        em.persist(email);
        return email;
    }

    public Email mergeEmail(Email email) {
        return em.merge(email);
    }

    public void removeEmail(Email email) {
        email = em.find(Email.class, email.getEmailId());
        em.remove(email);
    }

    /** <code>select o from Email o</code> */
    public List<Email> getEmailFindAll() {
        return em.createNamedQuery("Email.findAll").getResultList();
    }

    public LogQa persistLogQa(LogQa logQa) {
        em.persist(logQa);
        return logQa;
    }

    public LogQa mergeLogQa(LogQa logQa) {
        return em.merge(logQa);
    }

    public void removeLogQa(LogQa logQa) {
        logQa = em.find(LogQa.class, logQa.getLogQaId());
        em.remove(logQa);
    }

    /** <code>select o from LogQa o</code> */
    public List<LogQa> getLogQaFindAll() {
        return em.createNamedQuery("LogQa.findAll").getResultList();
    }

    public DocSource persistDocSource(DocSource docSource) {
        em.persist(docSource);
        return docSource;
    }

    public DocSource mergeDocSource(DocSource docSource) {
        return em.merge(docSource);
    }

    public void removeDocSource(DocSource docSource) {
        docSource = em.find(DocSource.class, docSource.getDocSourceId());
        em.remove(docSource);
    }

    /** <code>select o from DocSource o</code> */
    public List<DocSource> getDocSourceFindAll() {
        return em.createNamedQuery("DocSource.findAll").getResultList();
    }

    public Category persistCategory(Category category) {
        em.persist(category);
        return category;
    }
    public Category mergeCategoryEdit(Category category , Category exParent) {
        /* sanitize values */
        if (category.getActiveYn() == null) {
            category.setActiveYn(new Long(1));
                    }
        if( exParent.equals(category.getCategory()) )
        //exParent.getCategory().getCategoryList().contains(category))
        {
            return em.merge(category);
        }
        else
        {   
            exParent.getCategoryList().remove(category);
            em.merge(exParent);
            category.getCategory().getCategoryList().add(category);
            em.merge(category.getCategory());
            if( exParent.getActiveYn() != category.getCategory().getActiveYn() )
                ActivateSubCategoryes(category.getCategory(), category.getCategory().getActiveYn());
            
            return em.merge(category);
                
            }
    }
  
    public Category mergeCategory(Category category) {
        /* sanitize values */
        if (category.getActiveYn() == null) {
            category.setActiveYn(new Long(1));
                    }
        if(category.getCategory().getCategoryList().contains(category))
        {
            return em.merge(category);
        }
        else
        {      // Category parent = category.getCategory();
              //  parent = em.find(Category.class, parent.getCategoryId());
                category.getCategory().getCategoryList().add(category);
                em.merge(category.getCategory());
                return em.merge(category);
            }
    }

    public void removeCategory(Category category) {
        category = em.find(Category.class, category.getCategoryId());
        em.remove(category);
    }

    /** <code>select o from Category o</code> */
    public List<Category> getCategoryFindAll() {
        return em.createNamedQuery("Category.findAll").getResultList();
    }

    public LogDoc persistLogDoc(LogDoc logDoc) {
        em.persist(logDoc);
        return logDoc;
    }

    public LogDoc mergeLogDoc(LogDoc logDoc) {
        return em.merge(logDoc);
    }

    public void removeLogDoc(LogDoc logDoc) {
        logDoc = em.find(LogDoc.class, logDoc.getLogDocId());
        em.remove(logDoc);
    }

    /** <code>select o from LogDoc o</code> */
    public List<LogDoc> getLogDocFindAll() {
        return em.createNamedQuery("LogDoc.findAll").getResultList();
    }

    public Group persistGroup(Group group) {
        em.persist(group);
        return group;
    }

    public Group mergeGroup(Group group) {
        return em.merge(group);
    }

    public void removeGroup(Group group) {
        group = em.find(Group.class, group.getGroupId());
        em.remove(group);
    }

    /** <code>select o from Group o</code> */
    public List<Group> getGroupFindAll() {
        return em.createNamedQuery("Group.findAll").getResultList();
    }

    public Language persistLanguage(Language language) {
        em.persist(language);
        return language;
    }

    public Language mergeLanguage(Language language) {
        return em.merge(language);
    }

    public void removeLanguage(Language language) {
        language = em.find(Language.class, language.getLanguageId());
        em.remove(language);
    }

    /** <code>select o from Language o</code> */
    public List<Language> getLanguageFindAll() {
        return em.createNamedQuery("Language.findAll").getResultList();
    }

    /** <code>select o from Language o where o.activeYn = :active</code> */
    public List<Language> getLanguageFindActive(Object active) {
        if (active == null) 
            active = 1;
        List<Language> list = 
            em.createNamedQuery("Language.findActive").
            setParameter("active", active).
            getResultList(); 
        return list;
    }

    public User persistUser(User user) {
        em.persist(user);
        return user;
    }

    public User mergeUser(User user) {
        return em.merge(user);
    }

    public void removeUser(User user) {
        user = em.find(User.class, user.getUserId());
        em.remove(user);
    }

    /** <code>select o from User o</code> */
    public List<User> getUserFindAll() {
        return em.createNamedQuery("User.findAll").getResultList();
    }

    public Permission persistPermission(Permission permission) {
        em.persist(permission);
        return permission;
    }

    public Permission mergePermission(Permission permission) {
        return em.merge(permission);
    }

    public void removePermission(Permission permission) {
        permission = em.find(Permission.class, permission.getPermissionId());
        em.remove(permission);
    }

    /** <code>select o from Permission o</code> */
    public List<Permission> getPermissionFindAll() {
        return em.createNamedQuery("Permission.findAll").getResultList();
    }

    public QaNote persistQaNote(QaNote qaNote) {
        em.persist(qaNote);
        return qaNote;
    }

    public QaNote mergeQaNote(QaNote qaNote) {
        return em.merge(qaNote);
    }

    public void removeQaNote(QaNote qaNote) {
        qaNote = em.find(QaNote.class, qaNote.getQaNoteId());
        em.remove(qaNote);
    }

    /** <code>select o from QaNote o</code> */
    public List<QaNote> getQaNoteFindAll() {
        return em.createNamedQuery("QaNote.findAll").getResultList();
    }

    public ErrorLog persistErrorLog(ErrorLog errorLog) {
        em.persist(errorLog);
        return errorLog;
    }

    public ErrorLog mergeErrorLog(ErrorLog errorLog) {
        return em.merge(errorLog);
    }

    public void removeErrorLog(ErrorLog errorLog) {
        errorLog = em.find(ErrorLog.class, errorLog.getErrorLogId());
        em.remove(errorLog);
    }

    /** <code>select o from ErrorLog o</code> */
    public List<ErrorLog> getErrorLogFindAll() {
        return em.createNamedQuery("ErrorLog.findAll").getResultList();
    }

    public Document persistDocument(Document document) {
        em.persist(document);
        return document;
    }

    public Document mergeDocument(Document document) {
        return em.merge(document);
    }

    public void removeDocument(Document document) {
        document = em.find(Document.class, document.getDocumentId());
        em.remove(document);
    }

    /** <code>select o from Document o</code> */
    public List<Document> getDocumentFindAll() {
        return em.createNamedQuery("Document.findAll").getResultList();
    }

    public DocumentstatsWeb persistDocumentstatsWeb(DocumentstatsWeb documentstatsWeb) {
        em.persist(documentstatsWeb);
        return documentstatsWeb;
    }

    public DocumentstatsWeb mergeDocumentstatsWeb(DocumentstatsWeb documentstatsWeb) {
        return em.merge(documentstatsWeb);
    }

    public void removeDocumentstatsWeb(DocumentstatsWeb documentstatsWeb) {
        documentstatsWeb = em.find(DocumentstatsWeb.class, documentstatsWeb.getDocumentId());
        em.remove(documentstatsWeb);
    }

    /** <code>select o from DocumentstatsWeb o</code> */
    public List<DocumentstatsWeb> getDocumentstatsWebFindAll() {
        return em.createNamedQuery("DocumentstatsWeb.findAll").getResultList();
    }

    public ExternalLink persistExternalLink(ExternalLink externalLink) {
        em.persist(externalLink);
        return externalLink;
    }

    public ExternalLink mergeExternalLink(ExternalLink externalLink) {
        return em.merge(externalLink);
    }

    public void removeExternalLink(ExternalLink externalLink) {
        externalLink = em.find(ExternalLink.class, externalLink.getExternalLinkId());
        em.remove(externalLink);
    }

    /** <code>select o from ExternalLink o</code> */
    public List<ExternalLink> getExternalLinkFindAll() {
        return em.createNamedQuery("ExternalLink.findAll").getResultList();
    }

    public DocExtension persistDocExtension(DocExtension docExtension) {
        em.persist(docExtension);
        return docExtension;
    }

    public DocExtension mergeDocExtension(DocExtension docExtension) {
        return em.merge(docExtension);
    }

    public void removeDocExtension(DocExtension docExtension) {
        docExtension = em.find(DocExtension.class, docExtension.getDocExtensionId());
        em.remove(docExtension);
    }

    /** <code>select o from DocExtension o</code> */
    public List<DocExtension> getDocExtensionFindAll() {
        return em.createNamedQuery("DocExtension.findAll").getResultList();
    }

    public QastatsWeb persistQastatsWeb(QastatsWeb qastatsWeb) {
        em.persist(qastatsWeb);
        return qastatsWeb;
    }

    public QastatsWeb mergeQastatsWeb(QastatsWeb qastatsWeb) {
        return em.merge(qastatsWeb);
    }

    public void removeQastatsWeb(QastatsWeb qastatsWeb) {
        qastatsWeb = em.find(QastatsWeb.class, qastatsWeb.getQaId());
        em.remove(qastatsWeb);
    }

    /** <code>select o from QastatsWeb o</code> */
    public List<QastatsWeb> getQastatsWebFindAll() {
        return em.createNamedQuery("QastatsWeb.findAll").getResultList();
    }

    public LogEmail persistLogEmail(LogEmail logEmail) {
        em.persist(logEmail);
        return logEmail;
    }

    public LogEmail mergeLogEmail(LogEmail logEmail) {
        return em.merge(logEmail);
    }

    public void removeLogEmail(LogEmail logEmail) {
        logEmail = em.find(LogEmail.class, logEmail.getLogEmailId());
        em.remove(logEmail);
    }

    /** <code>select o from LogEmail o</code> */
    public List<LogEmail> getLogEmailFindAll() {
        return em.createNamedQuery("LogEmail.findAll").getResultList();
    }

    public DocType persistDocType(DocType docType) {
        em.persist(docType);
        return docType;
    }

    public DocType mergeDocType(DocType docType) {
        return em.merge(docType);
    }

    public void removeDocType(DocType docType) {
        docType = em.find(DocType.class, docType.getDocTypeId());
        em.remove(docType);
    }

    /** <code>select o from DocType o</code> */
    public List<DocType> getDocTypeFindAll() {
        return em.createNamedQuery("DocType.findAll").getResultList();
    }

    public News persistNews(News news) {
        em.persist(news);
        return news;
    }
             
    public News mergeNews(News news) {
        
        return em.merge(news);
    }
   
        
    public News mergeNewsByUser(News news, User user) {
        Date dateUpdate = new Date();
        
        news.setDateUpdate(new java.sql.Timestamp(dateUpdate.getTime()));
        news.setUser(user);
        if(news.getNewsId() == null) {
            em.persist(news);
            return news;
        } else
           return em.merge(news);
    }

    public void removeNews(News news) {
        news = em.find(News.class, news.getNewsId());
        em.remove(news);
    }

    /** <code>select o from News o</code> */
    public List<News> getNewsFindAll() {
        return em.createNamedQuery("News.findAll").getResultList();
    }

    public NoteType persistNoteType(NoteType noteType) {
        em.persist(noteType);
        return noteType;
    }

    public NoteType mergeNoteType(NoteType noteType) {
        return em.merge(noteType);
    }

    public void removeNoteType(NoteType noteType) {
        noteType = em.find(NoteType.class, noteType.getNoteTypeId());
        em.remove(noteType);
    }

    /** <code>select o from NoteType o</code> */
    public List<NoteType> getNoteTypeFindAll() {
        return em.createNamedQuery("NoteType.findAll").getResultList();
    }

    public Qa persistQa(Qa qa) {
        em.persist(qa);
        return qa;
    }

    public Qa mergeQa(Qa qa) {
        return em.merge(qa);
    }

    public void removeQa(Qa qa) {
        qa = em.find(Qa.class, qa.getQaId());
        em.remove(qa);
    }

    /** <code>select o from Qa o</code> */
    public List<Qa> getQaFindAll() {
        return em.createNamedQuery("Qa.findAll").getResultList();
    }

    /** <code>select o from News o</code> */
    public List<News> listNewsVisible(){
        List <News> result;
        
        result = em.createNamedQuery("News.findAll").getResultList();
        return result;
    }

    /** <code>select o from Category o where o.activeYn = 1 and (o.category.categoryId = :parent or (:parent is null and o.category is null))</code> */
    public List<Category> getCategoryChildCategories(Object parent) {
        List<Category> result;

        if (parent == null) {
            parent = 1L;
        }
        result = em.createNamedQuery("Category.activeChildCategories").
            setParameter("parent", parent).
            getResultList();
        return result;
    }
    
    public Category getCategoryById(Long categoryId) {

        if (categoryId == null) {
            categoryId = 1L;
        }
        Category result = em.find(Category.class, categoryId);
        result = em.merge(result);
        return result;
    }
    
   
    // rekurzina metoda za nazubljivanje prikaza u svim podkategorijama --Slavisa
    
    private Category setDash(Category category, String dashToAdd) {
        
        if ( category.getCategoryList().size() == 0)
            return category;
            dashToAdd+="- "; 
            
        for( Category categoryChild : category.getCategoryList()) {
            categoryChild.setStrDisplayName(dashToAdd+categoryChild.getName());
            setDash(categoryChild , dashToAdd);
        }
        return category;
    }
    
    /** <code>select o from Category o where o.activeYn = 1</code> */
    // podrazumeva se da je root id =0;
    public List<Category> getCategoryFindActiveCategories() {
      
        List<Category> lstCategories = em.createNamedQuery("Category.findActiveCategories").getResultList();
        Category root = em.find(Category.class, 0);
        
     //   root =  setDash ( root  );
        
        return lstCategories;
    }
    
    public List<Category> getCategoryFindById(Object category) {
        Long idCategory = 1L;

        if (category != null) {
            idCategory = ((Category)category).getCategoryId();
        }
        return em.createNamedQuery("Category.findById").setParameter("id", idCategory).getResultList();
    }

    private void fillResultList(List<Category> result, Category category, Long onlyActive) {
           if (category.getCategoryList().size() == 0)
               return;
              
           for(Category categoryChild : category.getCategoryList()) {
               if (onlyActive == 0 || categoryChild.getActiveYn() == 1)
               result.add(categoryChild);
               fillResultList(result, categoryChild, onlyActive);
           }
    }

    /** <code>select o from Category o where o.activeYn = :Active or ( :Active is null )  </code> */
    // metoda ne perzistuje izmene
    // dodata indikacija u slucaju da je neaktivna
    @TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
    public List<Category> getCategoryFindDependActiveCategories(Object Active) {
        Category root = em.find(Category.class, 1L);
        
        root.setStrDisplayName(root.getName());
        root = setDash(root, "");
        
        List<Category> result = new ArrayList<Category>();
        result.add(root);
        fillResultList(result, root, 0L);
        for(Category currentCategory : result) {
            if (currentCategory.getActiveYn() == 0) {
                currentCategory.setStrDisplayName(currentCategory.getStrDisplayName() + " [NIJE AKTIVNA]");
            }
        }
        return result;
    }

    @TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
    public List<Category> listActiveCategoriesTree(Long rootId) {
        Category root;
        if (rootId == null)
            rootId = 1L;
        root = em.find(Category.class, rootId);

        root.setStrDisplayName(root.getName());
        root = setDash(root, "");
        
        List<Category> result = new ArrayList<Category>();
        result.add(root);
        fillResultList(result, root, 1L);
        return result;
    }
    
    public News findOrCreate(News news) {
        if(news == null) {
            news = new News();
        }
        return news;
    }


    public Category findOrCreate(Category category, Boolean isNew) {
        if(isNew) {
            category = new Category();
        } else {
            category = em.find(Category.class, category.getCategoryId());
        }
        return category;
    }
    

    public Category initSubCategory(Category parent, Category toAddCategory) {
         toAddCategory.setCategory(parent);   
         return toAddCategory;
    }
    
    public Boolean isSubCategoryCintainsSomething(Category category) {
        
        return false;        
    }
    
    public void ActivateSubCategoryes(Category rootCategory, Long setValue) {
        
        for(Category currentCategory : rootCategory.getCategoryList()) {
            currentCategory.setActiveYn(setValue);
            em.merge(currentCategory);
            ActivateSubCategoryes(currentCategory, setValue);
        }
    }

    public void ActivateCategoryes(Category rootCategory) {

        List<Category> lstCategories = 
            em.createNamedQuery("Category.findById").setParameter("id", rootCategory.getCategoryId()).getResultList();
            
        Category currentCategory = lstCategories.get(0);    
        Long value = currentCategory.getActiveYn()==1L ? 0L : 1L ;
            currentCategory.setActiveYn(value);
        em.merge(currentCategory);
        ActivateSubCategoryes(rootCategory, value);
    }
    public Category addCategoryGroup( Group group , Category category)
    {
        
        if ( !category.getGroups().contains(group) && group != null)
            category.getGroups().add(group);
        return category;
        }
    
    public Category removeCategoryGroup( Group group , Category category )
    {
        category.getGroups().remove(group);
        return category;
        }
    public List<Group> allActulaGroups()
    {
            return em.createNamedQuery("Group.findAll").getResultList();
        }
}