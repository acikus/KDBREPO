package com.test.kdb.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import oracle.adf.share.ADFContext;

import com.test.kdb.model.entity.Category;
import com.test.kdb.model.entity.Document;
import com.test.kdb.model.entity.ExternalLink;
import com.test.kdb.model.entity.Language;
import com.test.kdb.model.entity.LogQa;
import com.test.kdb.model.entity.NoteType;
import com.test.kdb.model.entity.Qa;
import com.test.kdb.model.entity.QaNote;
import com.test.kdb.model.entity.QastatsWeb;
import com.test.kdb.model.entity.User;


@Stateless(name = "QASessionEJB", mappedName = "KDB-Model-QASessionEJB")
@Local
public class QASessionEJBBean implements QASessionEJBLocal {
    
    final static Long QA_CATEGORY_ID = 2L;
        
    @PersistenceContext(unitName="Model")
    private EntityManager em;

    public QASessionEJBBean() {
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

    public QaNote persistQaNote(QaNote qaNote) {
        em.persist(qaNote);
        return qaNote;
    }
    public QaNote persistQaNoteByUser(QaNote qaNote ,  User user)
    {
            Date dateUpdate = new Date();
            
            qaNote.setDateUpdate(new java.sql.Timestamp( dateUpdate.getTime() ) );
            //izmenio
            qaNote.setUser1(user);
            qaNote.setUser(user);
        
            em.persist(qaNote);
            Qa qa = qaNote.getQa();
            qa.getQaNoteList().add(qaNote);
            em.merge(qa);
            return qaNote;
        }

    public QaNote mergeQaNote(QaNote qaNote) {
        return em.merge(qaNote);
    }
    public QaNote mergeQaNoteByUser( QaNote qaNote , User user)
    {
        Date dateUpdate = new Date();
        
        qaNote.setDateUpdate(new java.sql.Timestamp( dateUpdate.getTime() ) );
        //izmenio
        qaNote.setUser1(user);
        
        
        
        return em.merge(qaNote);
    }
    public QaNote CreateNote( Qa qa)
    {
        QaNote note =  new QaNote();
        note.setQa(qa);
        note.setActiveYn( true );
        
    
        return note;
        }
    public QaNote findOrCreate( QaNote source  )
    {
        if( source == null)
        {
            
          source = new QaNote();
          return source;
        }
        else
        {
                if( source != null && source.getQaNoteId() == null)
                  return source;
                
                source = em.find(QaNote.class, source.getQaNoteId());
                return source;
            }
       
        }
    
    public void removeQaNote(QaNote qaNote) {
        qaNote = em.find(QaNote.class, qaNote.getQaNoteId());
        em.remove(qaNote);
    }

    /** <code>select o from QaNote o</code> */
    public List<QaNote> getQaNoteFindAll() {
        return em.createNamedQuery("QaNote.findAll").getResultList();
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

    public NoteType findOrCreateNoteType(NoteType noteType) {
        if (noteType == null) {
            noteType = new NoteType();
        }
        return noteType;
    }

    public NoteType persistNoteType(NoteType noteType) {
        em.persist(noteType);
        return noteType;
    }

    public NoteType mergeNoteType(NoteType noteType) {
        noteType.setActiveYn(noteType.getActiveYn());
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

    /** <code>select o from NoteType o</code> */
    public List<NoteType> listNoteType() {
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
    
    public Qa findOrCreate(Qa qa) {
        if (qa == null) {
            qa = new Qa();
            qa.setLanguage(em.find(Language.class, new Long(1)));
            qa.setActiveYn(false);
            qa.setContentApproved(false);
            qa.setContentRecommended(false);
        }
        return qa;
    }

    public Qa findQa(Long qaId) {
        if (qaId != null)
            return em.find(Qa.class, qaId);
        else
            return null;
    }
    public Qa mergeOrPersist(Qa qa) {
        
          //qa.setUser(em.find(User.class, new Long(1)));
          //qa.setLanguage(em.find(Language.class, new Long(1)));

        if (qa.getQaId() == null) {
            qa.setLanguage(em.find(Language.class, new Long(1)));
            qa.setDateInsert(new java.sql.Timestamp(new Date().getTime()));
            qa.setDateUpdate(new java.sql.Timestamp(new Date().getTime()));
            qa.setUsefulN(0L);
            qa.setUsefulY(0L);
            qa.setCounter(0L);

        } else {
            qa.setDateUpdate(new java.sql.Timestamp(new Date().getTime()));
        }
        /* sanitize values */
        if (qa.getActiveYn() == null) {
            qa.setActiveYn(false);
        }
        qa = em.merge(qa);
        //reindexQa();
        setPageFlowScopeValue("selectedQuestion",qa);
        return qa;
    }
    
//        public Qa mergeOrPersist(Qa qa) {
//        
//        qa.setUser(em.find(User.class, new Long(1)));//////////////////////////////////////////////////////////!!!!!!!!
//        qa.setLanguage(em.find(Language.class, new Long(1)));//////////////////////////////////////////////////////////!!!!!!!!
//
//        if (qa.getQaId() == null) {
//            qa.setDateInsert(new java.sql.Timestamp(new Date().getTime()));
//            qa.setDateUpdate(new java.sql.Timestamp(new Date().getTime()));
//            qa.setUsefulN(0L);
//            qa.setUsefulY(0L);
//            qa.setCounter(0L);
//
//        } else {
//            qa.setDateUpdate(new java.sql.Timestamp(new Date().getTime()));
//        }
//        /* sanitize values */
//        if (qa.getActiveYn() == null) {
//            qa.setActiveYn(false);
//        }
//        qa = em.merge(qa);
//        return qa;
//    }
    
    /* increment Qa.counter */
    public Qa incrementQaCounter(Qa qa) {
        qa = em.find(Qa.class, qa.getQaId());
        if (qa.getCounter() == null) {
            qa.setCounter(1L);
        } else {
            qa.setCounter(qa.getCounter()+1);
        }
        em.merge(qa);
        return qa;
    }

    /* increment Qa.usefulY */
    public Qa incrementQaUsefulY(Qa qa, User user) {
        qa = em.find(Qa.class, qa.getQaId());
        if (qa.getUsefulY()==null) {
            qa.setUsefulY(1L);
        } else {
            qa.setUsefulY(qa.getUsefulY()+1);
        }
        
        boolean alreadyExistsInUseful = false;
        if  (user.getUsefulQas() != null) {
            for (Qa tmpQa : user.getUsefulQas()) {
                if (qa.getQaId() == tmpQa.getQaId()) {
                    alreadyExistsInUseful = true;
                    break;
                }
            }
        }
        if (!alreadyExistsInUseful) {
            user.addUsefulQa(qa);
            em.merge(user);
        }
        
        em.merge(qa);
        return qa;
    }

    /* increment Qa.usefulN */
    public Qa incrementQaUsefulN(Qa qa, User user) {
        qa = em.find(Qa.class, qa.getQaId());
        if (qa.getUsefulN()==null) {
            qa.setUsefulN(1L);
        } else {
            qa.setUsefulN(qa.getUsefulN()+1);
        }
                    
        if  (user.getUsefulQas() != null) {
            for (Qa tmpQa : user.getUsefulQas()) {
                if (qa.getQaId() == tmpQa.getQaId()) {
                    user.getUsefulQas().remove(tmpQa);
                    break;
                }
            }
        }
        em.merge(user);
        qa.removeUser(user);
        em.merge(qa);
                
        return qa;
    }

    public List<Qa> searchQA(
            Object term, Object maxCount, Object offset,
            Object searchKeywords, 
            Object searchQuestion, 
            Object searchShortAnswer, 
            Object searchDetailAnswer,
            Object language, 
            Object category) 
    {
        List<Qa> result;
        
        if (term == null) {
            return null;
        }
        if (searchKeywords == null) searchKeywords = true;
        if (searchQuestion == null) searchQuestion = true;
        if (searchShortAnswer == null) searchShortAnswer = true;
        if (searchDetailAnswer == null) searchDetailAnswer = false;

        // if term is integer number, return QA with this qaId
        // otherwise, do the full search
        try {
            // case: term is numeric
            Long qaId = Long.parseLong((String)term);
            result = em.createNamedQuery("Qa.findById").setParameter("id", qaId).getResultList();
            
        } catch (NumberFormatException nfe) {
            // case: term is not numeric
            if (((String)term).length() < 3) {
                // search term is too short
                return null;
            }
            String sql = 
                "select Qa.* " +
                    "from table(qa_mgmt.search_qa(" +
                        "common.construct_cyrlat_query(?1), 0, 100, " +
                        "?4, ?5, ?6, ?7, " +
                        "?8, ?9)) Qa ";
            Query query = em.createNativeQuery(sql, Qa.class);
            // parameters
            query.setParameter(1, (String) term);
            query.setParameter(4, ((Boolean)searchKeywords ? 1 : 0));
            query.setParameter(5, ((Boolean)searchQuestion ? 1 : 0));
            query.setParameter(6, ((Boolean)searchShortAnswer ? 1 : 0));
            query.setParameter(7, ((Boolean)searchDetailAnswer ? 1 : 0));
            // language
            if (language != null)
                query.setParameter(8, (Long)((Language)language).getLanguageId());
            else
                query.setParameter(8, null);
            // category
            if (category != null)
                query.setParameter(9, (Long)((Category)category).getCategoryId());
            else
                query.setParameter(9, null);
            result = query.getResultList();
        }
        return result;
    }

    public Boolean reindexQa() {
        Query query;
        query = em.createNativeQuery("begin qa_mgmt.sync_search_indices; end;");
        query.executeUpdate();
        return true;
    }

    public void qaAddCategory(Qa qa, Category category) {
        if (!qa.getCategories().contains(category)) {
            qa.getCategories().add(category);
        }
        // category.getDocuments().add(document);
    }

    public void qaRemoveCategory(Qa qa, Category category) {
        qa.getCategories().remove(category);
        // category.getDocuments().remove(document);
    }
    
    public void qaAddQuestion(Qa question1, Qa question2) {
        if (!question1.getQas().contains(question2)) {
            question1.getQas().add(question2);
            em.merge(question1);
        }
    }

    public void qaRemoveQuestion(Qa question1, Qa question2) {
        question1.getQas().remove(question2);
        em.merge(question1);
    }

    public void qaAddExternalLink(Qa question, String urllink, String description) {
        String ourUrllink;
        String ourDescription;

        if (urllink == null || description == null || urllink.isEmpty() || description.isEmpty())
            /* no valid data passed */
            return;
        
        /* check if we already have this data */
        for(ExternalLink el : question.getExternalLinkList()) {
            ourUrllink = el.getUrllink();
            ourDescription = el.getDescription();
            
            if (ourUrllink != null && ourDescription != null &&
                ourUrllink.equals(urllink) && ourDescription.equals(description))
                /* we already have this data */
                return;
        }
        
        /* add new data */
        ExternalLink externalLink = new ExternalLink();
        externalLink.setUrllink(urllink);
        externalLink.setDescription(description);
        externalLink.setQa(question);
        em.persist(externalLink);
        question.getExternalLinkList().add(externalLink);
        em.merge(question);
    }

    public void qaRemoveExternalLink(Qa question, ExternalLink externalLink) {
        if (externalLink == null || externalLink.getExternalLinkId() == null)
            return;
        externalLink = em.find(ExternalLink.class, externalLink.getExternalLinkId());
        question.getExternalLinkList().remove(externalLink);
        em.remove(externalLink);
        em.merge(question);
    }

    public void qaAddDocument(Qa question, Document document) {
        if (!question.getDocuments().contains(document)) {
            question.getDocuments().add(document);
            em.merge(question);
        }
    }

    public void qaRemoveDocument(Qa question, Document document) {
        question.getDocuments().remove(document);
        em.merge(question);
    }
    
    public Category findQaCategory(Long categoryId) {
        
        if (categoryId == null ) {
            categoryId = QA_CATEGORY_ID;
        }
        
        Category category = em.find(Category.class, categoryId);
        
        if (category == null) {
            category = em.find(Category.class, 1L);
        }
        return category;
    }


    /** <code>select o from NoteType o where activeYn==:active or :active == null </code> */
    public List<NoteType> getNoteTypeActiveOrNot(Object active) {
        return em.createNamedQuery("NoteType.activeOrNot").setParameter("active", active).getResultList();
    }

    /** <code>select o from Language o where o.activeYn = :active</code> */
    public List<Language> getLanguageFindActive(Object active) {
        return em.createNamedQuery("Language.findActive").setParameter("active", active).getResultList();
    }
    
    public void setPageFlowScopeValue(String name, Object value) {
        ADFContext adfCtx = ADFContext.getCurrent();
        Map pageFlowScope = adfCtx.getPageFlowScope();
        pageFlowScope.put(name, value);
    }

    public Object getPageFlowScopeValue(Object key) {
        ADFContext adfCtx = ADFContext.getCurrent();
        Map pageFlowScope = adfCtx.getPageFlowScope();
        return pageFlowScope.get(key);
    }
    

}
