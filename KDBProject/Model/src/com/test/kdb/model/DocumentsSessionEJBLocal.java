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
import com.test.kdb.model.entity.Language;
import com.test.kdb.model.entity.LogDoc;
import com.test.kdb.model.entity.Qa;
import com.test.kdb.model.entity.User;

@Local
public interface DocumentsSessionEJBLocal {
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    DocSource findOrCreateDocSource(DocSource docSource);

    DocSource persistDocSource(DocSource docSource);

    DocSource mergeDocSource(DocSource docSource);

    void removeDocSource(DocSource docSource);

    List<DocSource> listDocSource();

    List<DocSource> getDocSourceFindAll();

    LogDoc persistLogDoc(LogDoc logDoc);

    LogDoc mergeLogDoc(LogDoc logDoc);

    void removeLogDoc(LogDoc logDoc);

    List<LogDoc> getLogDocFindAll();

    Document persistDocument(Document document);

    Document mergeDocument(Document document);

    void removeDocument(Document document);

    List<Document> getDocumentFindAll();

    DocumentstatsWeb persistDocumentstatsWeb(DocumentstatsWeb documentstatsWeb);

    DocumentstatsWeb mergeDocumentstatsWeb(DocumentstatsWeb documentstatsWeb);

    void removeDocumentstatsWeb(DocumentstatsWeb documentstatsWeb);

    List<DocumentstatsWeb> getDocumentstatsWebFindAll();

    DocExtension findOrCreateDocExtension(DocExtension docExtension);

    DocExtension persistDocExtension(DocExtension docExtension);

    DocExtension mergeDocExtension(DocExtension docExtension);

    void removeDocExtension(DocExtension docExtension);

    List<DocExtension> listDocExtension();

    List<DocExtension> getDocExtensionFindAll();

    DocType findOrCreateDocType(DocType docType);

    DocType persistDocType(DocType docType);

    DocType mergeDocType(DocType docType);

    void removeDocType(DocType docType);

    List<DocType> listDocType();

    List<DocType> getDocTypeFindAll();

    Document findOrCreate(Document document);

    Document mergeOrPersist(Document document);

    List<DocSource> getDocSourceFindActive(Object active);

    List<DocSource> getCategoryFindActive(Object active);

    List<DocExtension> getDocExtensionFindActive(Object active);

    List<DocType> getDocTypeFindActive(Object active);

    Document incrementDocumentCounter(Document document);

    Document incrementDocumentUsefulY(Document document, User user);

    Document incrementDocumentUsefulN(Document document, User user);

    public List<Document> searchDocuments(Object term, Object maxCount, Object offset, Object searchKeywords,
                                          Object searchName, Object searchDescription, Object searchBody,
                                          Object dateLegalFrom, Object dateLegalTo, Object docSource, Object docType,
                                          Object language, Object category, Object chunkSize, Object showChunks);

    List<DocumentChunk> searchDocumentDetails(Object documentId, Object term, Object chunkSize);

    Document lazyFetchDocumentObject(Document document);

    Boolean reindexDocuments();

    void documentAddCategory(Document document, Category category);

    void documentRemoveCategory(Document document, Category category);

    void documentAddQuestion(Document document, Qa question);

    void documentRemoveQuestion(Document document, Qa question);

    Category getCategoryById(Long categoryId);

    Document findDocument(Long documentId);

    Category findDocCategory(Long categoryId);

    List<Language> getLanguageFindActive(Object active);
    
    List<Document> getDocumentFindById(Long id);
   
}
