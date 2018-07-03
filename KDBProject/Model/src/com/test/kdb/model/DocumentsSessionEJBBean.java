package com.test.kdb.model;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import oracle.sql.TIMESTAMP;

import org.eclipse.persistence.config.QueryHints;

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

@Stateless(name = "DocumentsSessionEJB", mappedName = "KDB-Model-DocumentsSessionEJB")
@Local
public class DocumentsSessionEJBBean implements DocumentsSessionEJBLocal {

    final static Long DOC_CATEGORY_ID = 3L;

    @PersistenceContext(unitName = "Model")
    private EntityManager em;

    public DocumentsSessionEJBBean() {
    }

    public Object queryByRange(String jpqlStmt, int firstResult, int maxResults) {
        Query query = em.createQuery(jpqlStmt);
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    public DocSource findOrCreateDocSource(DocSource docSource) {
        if (docSource == null) {
            docSource = new DocSource();
        }
        return docSource;
    }

    public Document findDocument(Long documentId) {
        Document document = em.find(Document.class, documentId);
        return document;
    }


    public DocSource persistDocSource(DocSource docSource) {
        em.persist(docSource);
        return docSource;
    }

    public DocSource mergeDocSource(DocSource docSource) {
        docSource.setActiveYn(docSource.getActiveYn());
        return em.merge(docSource);
    }

    public void removeDocSource(DocSource docSource) {
        docSource = em.find(DocSource.class, docSource.getDocSourceId());
        em.remove(docSource);
    }

    /** <code>select o from DocSource o</code> */
    public List<DocSource> listDocSource() {
        return em.createNamedQuery("DocSource.findAll").getResultList();
    }

    /** <code>select o from DocSource o</code> */
    public List<DocSource> getDocSourceFindAll() {
        return em.createNamedQuery("DocSource.findAll").getResultList();
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

    public DocExtension findOrCreateDocExtension(DocExtension docExtension) {
        if (docExtension == null) {
            docExtension = new DocExtension();
        }
        return docExtension;
    }

    public DocExtension persistDocExtension(DocExtension docExtension) {
        em.persist(docExtension);
        return docExtension;
    }

    public DocExtension mergeDocExtension(DocExtension docExtension) {
        docExtension.setActiveYn(docExtension.getActiveYn());
        return em.merge(docExtension);
    }

    public void removeDocExtension(DocExtension docExtension) {
        docExtension = em.find(DocExtension.class, docExtension.getDocExtensionId());
        em.remove(docExtension);
    }

    /** <code>select o from DocExtension o</code> */
    public List<DocExtension> listDocExtension() {
        return em.createNamedQuery("DocExtension.findAll").getResultList();
    }

    /** <code>select o from DocExtension o</code> */
    public List<DocExtension> getDocExtensionFindAll() {
        return em.createNamedQuery("DocExtension.findAll").getResultList();
    }

    public DocType findOrCreateDocType(DocType docType) {
        if (docType == null) {
            docType = new DocType();
        }
        return docType;
    }

    public DocType persistDocType(DocType docType) {
        em.persist(docType);
        return docType;
    }

    public DocType mergeDocType(DocType docType) {
        docType.setActiveYn(docType.getActiveYn());
        return em.merge(docType);
    }

    public void removeDocType(DocType docType) {
        docType = em.find(DocType.class, docType.getDocTypeId());
        em.remove(docType);
    }

    /** <code>select o from DocType o</code> */
    public List<DocType> listDocType() {
        return em.createNamedQuery("DocType.findAll").getResultList();
    }

    /** <code>select o from DocType o</code> */
    public List<DocType> getDocTypeFindAll() {
        return em.createNamedQuery("DocType.findAll").getResultList();
    }

    public Document findOrCreate(Document document) {
        if (document == null) {
            document = new Document();
            document.setActiveYn(false);
            document.setContentApproved(false);
            document.setContentRecommended(false);
            document.setExpiredYn(false);
            document.setInternalDocYn(false);
            document.setLanguage(em.find(Language.class, new Long(1)));

        }
        return document;
    }

    public Document mergeOrPersist(Document document) {
        String filename, extension;
        DocExtension docExtension;
        int dot;

        boolean newDocument = document.getDocumentId() == null;
        if (newDocument) {
            //document.setUser(em.find(User.class, new Long(1)));
            document.setLanguage(em.find(Language.class, new Long(1)));
            document.setDateInsert(new java.sql.Timestamp(new Date().getTime()));
            document.setDateUpdate(new java.sql.Timestamp(new Date().getTime()));
            document.setDocExtension(em.find(DocExtension.class, new Long(1)));
            document.setUsefulN(0L);
            document.setUsefulY(0L);
            document.setCounter(0L);
        } else {
            document.setDateUpdate(new java.sql.Timestamp(new Date().getTime()));
        }
        /* sanitize activeYn value */
        if (document.getActiveYn() == null) {
            document.setActiveYn(false);
        }

        /* document extension */
        filename = document.getFilename();
        if (filename != null) {
            dot = filename.lastIndexOf(".");
            try {
                extension = filename.substring(dot + 1, filename.length());
                docExtension =
                        (DocExtension)em.createNamedQuery("DocExtension.findByExtension").setParameter("extension",
                                                                                                       extension).getResultList().get(0);
            } catch (IndexOutOfBoundsException e) {
                // no known extension- default to application/octet-stream
                docExtension =
                        (DocExtension)em.createNamedQuery("DocExtension.findById").setParameter("id", 1L).getResultList().get(0);
            }
            document.setDocExtension(docExtension);
        }
        document = em.merge(document);
        // .
        
        return document;
    }

    /** <code>select o from DocSource o where o.activeYn = :active</code> */
    public List<DocSource> getDocSourceFindActive(Object active) {
        if (active == null)
            active = 1;
        List<DocSource> list =
            em.createNamedQuery("DocSource.findActive").setParameter("active", active).getResultList();
        return list;
    }

    /** <code>select o from Category o where o.activeYn = :active</code> */
    public List<DocSource> getCategoryFindActive(Object active) {
        if (active == null)
            active = 1;
        List<DocSource> list =
            em.createNamedQuery("Category.findActive").setParameter("active", active).getResultList();
        return list;
    }

    /** <code>select o from DocExtension o where o.activeYn = :active</code> */
    public List<DocExtension> getDocExtensionFindActive(Object active) {
        if (active == null)
            active = 1;
        List<DocExtension> list =
            em.createNamedQuery("DocExtension.findActive").setParameter("active", active).getResultList();
        return list;
    }

    /** <code>select o from DocType o where o.activeYn = :active</code> */
    public List<DocType> getDocTypeFindActive(Object active) {
        if (active == null)
            active = 1;
        List<DocType> list = em.createNamedQuery("DocType.findActive").setParameter("active", active).getResultList();
        return list;
    }

    /* increment Document.counter */

    public Document incrementDocumentCounter(Document document) {
        document = em.find(Document.class, document.getDocumentId());
        if (document.getCounter() == null) {
            document.setCounter(1L);
        } else {
            document.setCounter(document.getCounter() + 1);
        }
        em.merge(document);
        return document;
    }

    /* increment Document.usefulY */

    public Document incrementDocumentUsefulY(Document document, User user) {
        document = em.find(Document.class, document.getDocumentId());
        if (document.getUsefulY() == null) {
            document.setUsefulY(1L);
        } else {
            document.setUsefulY(document.getUsefulY() + 1);
        }

        boolean alreadyExistsInUseful = false;
        if (user.getUsefulDocuments() != null) {
            for (Document tmpDoc : user.getUsefulDocuments()) {
                if (document.getDocumentId() == tmpDoc.getDocumentId()) {
                    alreadyExistsInUseful = true;
                    break;
                }
            }
        }
        if (!alreadyExistsInUseful) {
            user.addUsefulDocument(document);
            em.merge(user);
        }
        em.merge(document);
        return document;
    }

    /* increment Document.usefulN */

    public Document incrementDocumentUsefulN(Document document, User user) {
        document = em.find(Document.class, document.getDocumentId());
        if (document.getUsefulN() == null) {
            document.setUsefulN(1L);
        } else {
            document.setUsefulN(document.getUsefulN() + 1);
        }

        if (user.getUsefulDocuments() != null) {
            for (Document tmpDoc : user.getUsefulDocuments()) {
                if (document.getDocumentId() == tmpDoc.getDocumentId()) {
                    user.getUsefulDocuments().remove(tmpDoc);
                    break;
                }
            }
        }
        em.merge(user);
        document.removeUser(user);
        em.merge(document);

        return document;
    }

    public List<Document> searchDocuments(Object term, Object maxCount, Object offset, Object searchKeywords,
                                          Object searchName, Object searchDescription, Object searchBody,
                                          Object dateLegalFrom, Object dateLegalTo, Object docSource, Object docType,
                                          Object language, Object category, Object chunkSize, Object showChunks) {
        List<Document> result; //= new ArrayList<Document>();

        if (term == null) {
            return null;
        }
        if (searchKeywords == null)
            searchKeywords = true;
        if (searchName == null)
            searchName = true;
        if (searchDescription == null)
            searchDescription = true;
        if (searchBody == null)
            searchBody = false;
        if (showChunks == null)
            showChunks = false;
        if ((Boolean)searchBody) {
            searchKeywords = false;
            searchName = false;
            searchDescription = false;
        } else {
            showChunks = false;
        }

        // if term is integer number, return document with this documentId
        // otherwise, do the full search
        try {
            // case: term is numeric
            Long documentId = Long.parseLong((String)term);
            result = em.createNamedQuery("Document.findById").setParameter("id", documentId).getResultList();
            showChunks = false;

        } catch (NumberFormatException nfe) {
            // case: term is not numeric
            if (((String)term).length() < 3) {
                // search term is too short
                return null;
            }
            StringBuffer queryString =
                new StringBuffer("select Document.* " + "from table(doc_mgmt.search_documents(" +
                                 "common.construct_cyrlat_query(?1), 0, 100, " + "?4, ?5, ?6, ?7, " + "?8, ?9, " +
                                 "?10, ?11, " + "?12, ?13)" + ") Document ");
            Query query = em.createNativeQuery(queryString.toString(), Document.class);
            // parameters
            query.setParameter(1, (String)term);
            query.setParameter(4, ((Boolean)searchKeywords ? 1 : 0));
            query.setParameter(5, ((Boolean)searchName ? 1 : 0));
            query.setParameter(6, ((Boolean)searchDescription ? 1 : 0));
            query.setParameter(7, ((Boolean)searchBody ? 1 : 0));
            query.setParameter(8, (Date)dateLegalFrom);
            query.setParameter(9, (Date)dateLegalTo);
            // docSource
            if (docSource != null)
                query.setParameter(10, (Long)((DocSource)docSource).getDocSourceId());
            else
                query.setParameter(10, null);
            // docType
            if (docType != null)
                query.setParameter(11, (Long)((DocType)docType).getDocTypeId());
            else
                query.setParameter(11, null);
            // language
            if (language != null)
                query.setParameter(12, (Long)((Language)language).getLanguageId());
            else
                query.setParameter(12, null);
            // categoryId
            if (category != null)
                query.setParameter(13, (Long)((Category)category).getCategoryId());
            else
                query.setParameter(13, null);

            result = query.getResultList();
        }

        // fetch document chunks only if we will show them
        if ((Boolean)showChunks) {

            List<DocumentChunk> documentChunks ;
            for (Document doc : result) {
                documentChunks = searchDocumentDetails(doc.getDocumentId(), term, chunkSize);
                doc.setDocumentChunksList(documentChunks);
            
            }
        }
        return result;
    }

    public List<DocumentChunk> searchDocumentDetails(Object documentId, Object term, Object chunkSize) {
        Long longChunkSize;
        List<DocumentChunk> result = new ArrayList<DocumentChunk>();
        DocumentChunk dch = null;
        if (term == null) {
            return null;
        }
        try {
            longChunkSize = Long.parseLong((String)chunkSize);
        } catch (NumberFormatException nfe) {
            longChunkSize = 200L;
        }
        StringBuffer queryString =
            new StringBuffer("select DocumentChunk.DOCUMENT_ID,DocumentChunk.CHUNK,DocumentChunk.CHUNK_BODY,DocumentChunk.CHUNK_START,DocumentChunk.CHUNK_END " + "from table(doc_mgmt.search_document_details(" + "?1, " +
                             "common.construct_cyrlat_query(?2)," + "?3)" + ") DocumentChunk ");
        Query query = em.createNativeQuery(queryString.toString(), "DCResults");
        query.setParameter(1, (Long)documentId);
        query.setParameter(2, (String)term);
        query.setParameter(3, longChunkSize);
        List<Object[]> rawResultList = query.getResultList();
        for(Object[] object : rawResultList){
        dch = new DocumentChunk();

        dch.setDocumentId(object[0]!= null ? ((BigDecimal)object[0]).longValue() : null);
        dch.setChunk(object[1]!= null ? ((BigDecimal)object[1]).longValue() : null);
        dch.setChunkBody(object[2]!= null ? object[2].toString() : "");
        dch.setChunkStart(object[3]!= null ? ((BigDecimal)object[3]).longValue() : null);
        dch.setChunkEnd(object[4]!= null ? ((BigDecimal)object[4]).longValue() : null);
        result.add(dch);
        }

        return result;
    }

    public Document lazyFetchDocumentObject(Document document) {
        Document document1;
        Long documentId = document.getDocumentId();
        document1 = em.find(Document.class, (Long)documentId);

        if (document1 != null) {
            document1.setObject(em.find(Document.class, document.getDocumentId()).getObject());
        }
        return document1;
    }

    public Boolean reindexDocuments() {
        Query query;
        query = em.createNativeQuery("begin doc_mgmt.sync_search_indices; end;");
        query.executeUpdate();
        return true;
    }

    public void documentAddCategory(Document document, Category category) {
        if (!document.getCategories().contains(category)) {
            document.getCategories().add(category);
        }
        // category.getDocuments().add(document);
    }

    public void documentRemoveCategory(Document document, Category category) {
        document.getCategories().remove(category);
        // category.getDocuments().remove(document);
    }

    public void documentAddQuestion(Document document, Qa question) {
        if (!document.getQas().contains(question)) {
            document.getQas().add(question);
            em.merge(document);
        }
    }

    public void documentRemoveQuestion(Document document, Qa question) {
        document.getQas().remove(question);
        em.merge(document);
    }

    public Category getCategoryById(Long categoryId) {
        if (categoryId == null) {
            categoryId = 1L;
        }
        Category result = em.find(Category.class, categoryId);
        return result;
    }

    public Category findDocCategory(Long categoryId) {

        if (categoryId == null) {
            categoryId = DOC_CATEGORY_ID;
        }

        Category category = em.find(Category.class, categoryId);

        if (category == null) {
            category = em.find(Category.class, 1L);
        }
        return category;
    }

    /** <code>select o from Language o where o.activeYn = :active</code> */
    public List<Language> getLanguageFindActive(Object active) {
        return em.createNamedQuery("Language.findActive").setParameter("active", active).getResultList();
    }

    /** <code>select o from Document o where o.documentId = :id </code> */
    public List<Document> getDocumentFindById(Long id) {
        return em.createNamedQuery("Document.findById").setParameter("id", id).getResultList();
    }
}
