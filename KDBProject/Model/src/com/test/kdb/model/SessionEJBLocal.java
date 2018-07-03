package com.test.kdb.model;

import java.util.List;

import javax.ejb.Local;

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

@Local
public interface SessionEJBLocal {
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    Email persistEmail(Email email);

    Email mergeEmail(Email email);

    void removeEmail(Email email);

    List<Email> getEmailFindAll();

    LogQa persistLogQa(LogQa logQa);

    LogQa mergeLogQa(LogQa logQa);

    void removeLogQa(LogQa logQa);

    List<LogQa> getLogQaFindAll();

    DocSource persistDocSource(DocSource docSource);

    DocSource mergeDocSource(DocSource docSource);

    void removeDocSource(DocSource docSource);

    List<DocSource> getDocSourceFindAll();

    Category persistCategory(Category category);

    Category mergeCategory(Category category);
    
    Category mergeCategoryEdit(Category category , Category exParent);
    
    void removeCategory(Category category);

    List<Category> getCategoryFindAll();

    LogDoc persistLogDoc(LogDoc logDoc);

    LogDoc mergeLogDoc(LogDoc logDoc);

    void removeLogDoc(LogDoc logDoc);

    List<LogDoc> getLogDocFindAll();

    Group persistGroup(Group group);

    Group mergeGroup(Group group);

    void removeGroup(Group group);

    List<Group> getGroupFindAll();

    Language persistLanguage(Language language);

    Language mergeLanguage(Language language);

    void removeLanguage(Language language);

    List<Language> getLanguageFindAll();

    List<Language> getLanguageFindActive(Object active);
    
    User persistUser(User user);

    User mergeUser(User user);

    void removeUser(User user);

    List<User> getUserFindAll();

    Permission persistPermission(Permission permission);

    Permission mergePermission(Permission permission);

    void removePermission(Permission permission);

    List<Permission> getPermissionFindAll();

    QaNote persistQaNote(QaNote qaNote);

    QaNote mergeQaNote(QaNote qaNote);

    void removeQaNote(QaNote qaNote);

    List<QaNote> getQaNoteFindAll();

    ErrorLog persistErrorLog(ErrorLog errorLog);

    ErrorLog mergeErrorLog(ErrorLog errorLog);

    void removeErrorLog(ErrorLog errorLog);

    List<ErrorLog> getErrorLogFindAll();

    Document persistDocument(Document document);

    Document mergeDocument(Document document);

    void removeDocument(Document document);

    List<Document> getDocumentFindAll();

    DocumentstatsWeb persistDocumentstatsWeb(DocumentstatsWeb documentstatsWeb);

    DocumentstatsWeb mergeDocumentstatsWeb(DocumentstatsWeb documentstatsWeb);

    void removeDocumentstatsWeb(DocumentstatsWeb documentstatsWeb);

    List<DocumentstatsWeb> getDocumentstatsWebFindAll();

    ExternalLink persistExternalLink(ExternalLink externalLink);

    ExternalLink mergeExternalLink(ExternalLink externalLink);

    void removeExternalLink(ExternalLink externalLink);

    List<ExternalLink> getExternalLinkFindAll();

    DocExtension persistDocExtension(DocExtension docExtension);

    DocExtension mergeDocExtension(DocExtension docExtension);

    void removeDocExtension(DocExtension docExtension);

    List<DocExtension> getDocExtensionFindAll();

    QastatsWeb persistQastatsWeb(QastatsWeb qastatsWeb);

    QastatsWeb mergeQastatsWeb(QastatsWeb qastatsWeb);

    void removeQastatsWeb(QastatsWeb qastatsWeb);

    List<QastatsWeb> getQastatsWebFindAll();

    LogEmail persistLogEmail(LogEmail logEmail);

    LogEmail mergeLogEmail(LogEmail logEmail);

    void removeLogEmail(LogEmail logEmail);

    List<LogEmail> getLogEmailFindAll();

    DocType persistDocType(DocType docType);

    DocType mergeDocType(DocType docType);

    void removeDocType(DocType docType);

    List<DocType> getDocTypeFindAll();

    News persistNews(News news);

    News mergeNews(News news);

    void removeNews(News news);

    List<News> getNewsFindAll();

    NoteType persistNoteType(NoteType noteType);

    NoteType mergeNoteType(NoteType noteType);

    void removeNoteType(NoteType noteType);

    List<NoteType> getNoteTypeFindAll();

    Qa persistQa(Qa qa);

    Qa mergeQa(Qa qa);

    void removeQa(Qa qa);

    List<Qa> getQaFindAll();
    
    List<News> listNewsVisible();

    List<Category> getCategoryChildCategories(Object parent);

    List<Category> getCategoryFindActiveCategories();

    List<Category> getCategoryFindById(Object id);

    List<Category> getCategoryFindDependActiveCategories(Object Active);
   
    List<Category> listActiveCategoriesTree(Long rootId);
    
    Category findOrCreate(Category category, Boolean newCategory );

    Category getCategoryById(Long categoryId);    
    
    Category initSubCategory( Category parent , Category toAddCategory);
    
    void ActivateCategoryes(Category rootCategory);
    
    News findOrCreate(News news );
    
    News mergeNewsByUser( News news , User user);
    
    Category addCategoryGroup( Group group , Category category);
    
    Category removeCategoryGroup( Group group , Category category );
    
    List<Group> allActulaGroups();
    
}
