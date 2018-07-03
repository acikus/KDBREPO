package com.test.kdb.model;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.test.kdb.model.entity.Category;
import com.test.kdb.model.entity.DocSource;
import com.test.kdb.model.entity.DocType;
import com.test.kdb.model.entity.Document;
import com.test.kdb.model.entity.Email;
import com.test.kdb.model.entity.NoteType;
import com.test.kdb.model.entity.Qa;
import com.test.kdb.model.entity.QaNote;
import com.test.kdb.reports.EmailLastDays;
import com.test.kdb.reports.NumberOfDays;
import com.test.kdb.reports.NumberOfEmails;
import com.test.kdb.reports.TotalQA;


@Stateless(name = "ReportsSessionEJB", mappedName = "KDB-Model-ReportsSessionEJB")
@Local
public class ReportsSessionEJBBean implements ReportsSessionEJBLocal {
    @PersistenceContext(unitName = "Model")
    private EntityManager em;

    public ReportsSessionEJBBean() {
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

    public List<Qa> QAMostVisited(Integer startFrom, Integer maxResults) {
        List<Qa> result;
        String query = "select q from Qa q " + "order by q.counter desc";

        Query q = em.createQuery(query);
        if (startFrom != null)
            q.setFirstResult(startFrom);
        if (maxResults != null)
            q.setMaxResults(maxResults);
        result = q.getResultList();
        return result;
    }

    public List<Qa> QAMostUseful(Integer startFrom, Integer maxResults) {
        List<Qa> result;
        String query = "select q from Qa q " + "order by q.usefulY desc";

        Query q = em.createQuery(query);
        if (startFrom != null)
            q.setFirstResult(startFrom);
        if (maxResults != null)
            q.setMaxResults(maxResults);
        result = q.getResultList();
        return result;
    }

    public List<Qa> QAMostUnuseful(Integer startFrom, Integer maxResults) {
        List<Qa> result;
        String query = "select q from Qa q " + "order by q.usefulN desc";

        Query q = em.createQuery(query);
        if (startFrom != null)
            q.setFirstResult(startFrom);
        if (maxResults != null)
            q.setMaxResults(maxResults);
        result = q.getResultList();
        return result;
    }

    public List<Qa> QAInInactiveCategories(Integer startFrom, Integer maxResults) {
        List<Qa> result = new ArrayList<Qa>();
        List<Qa> qa;
        List<Category> category;
        int count = 0;
        String query = "select c from Category c " + "where c.activeYn=0";
        Query q = em.createQuery(query);
        category = q.getResultList();

        for (int i = 0; i < category.size(); i++) {
            qa = category.get(i).getQas();
            for (int j = 0; j < qa.size(); j++) {
                if (count < maxResults && !result.contains(qa.get(j)) && qa.get(j).getActiveYn()) {
                    result.add(qa.get(j));
                    count++;
                }
            }
        }
        return result;
    }

    public List<Document> DOCInInactiveCategories(Integer startFrom, Integer maxResults) {
        List<Document> result;
        String query = "select d from Document as d " + "left join d.categories as c " + "where c.activeYn=0";

        Query q = em.createQuery(query);
        if (startFrom != null)
            q.setFirstResult(startFrom);
        if (maxResults != null)
            q.setMaxResults(maxResults);
        result = q.getResultList();
        return result;
    }


    public List<Qa> QAMostRatioUsefulness(Integer startFrom, Integer maxResults) {
        List<Qa> result;
        String query =
            "select q.* from Qa q " + " where q.useful_N is not null and q.useful_N <> 0 " + "order by q.useful_Y/q.useful_N desc";
        Query q = em.createNativeQuery(query, Qa.class);
        if (startFrom != null)
            q.setFirstResult(startFrom);
        if (maxResults != null)
            q.setMaxResults(maxResults);
        result = q.getResultList();
        return result;

    }

    public List<Document> DOCMostRatioUsefulness(Integer startFrom, Integer maxResults) {
        List<Document> result;
        String query =
            "select q.* from Document q " + " where q.useful_N is not null and q.useful_N <> 0 " + "order by q.useful_Y/q.useful_N desc";
        Query q = em.createNativeQuery(query, Document.class);
        if (startFrom != null)
            q.setFirstResult(startFrom);
        if (maxResults != null)
            q.setMaxResults(maxResults);
        result = q.getResultList();
        return result;

    }

    public List<Qa> QAUpdated(Integer startFrom, Integer maxResults, Date dateUpdateFrom, Date dateUpdateTo) {
        List<Qa> result;
        String query = "select q from Qa q " + "where q.dateUpdate >= :date1 and q.dateUpdate <= :date2";

        Query q = em.createQuery(query);
        q.setParameter("date1", dateUpdateFrom);
        q.setParameter("date2", dateUpdateTo);

        if (startFrom != null)
            q.setFirstResult(startFrom);
        if (maxResults != null)
            q.setMaxResults(maxResults);
        result = q.getResultList();
        return result;
    }

    public List<Document> DOCUpdated(Integer startFrom, Integer maxResults, Date dateUpdateFrom, Date dateUpdateTo) {
        List<Document> result;
        String query = "select q from Document q " + "where q.dateUpdate >= :date1 and q.dateUpdate <= :date2";

        Query q = em.createQuery(query);
        q.setParameter("date1", dateUpdateFrom);
        q.setParameter("date2", dateUpdateTo);

        if (startFrom != null)
            q.setFirstResult(startFrom);
        if (maxResults != null)
            q.setMaxResults(maxResults);
        result = q.getResultList();
        return result;
    }

    public List<TotalQA> QATotal(Integer startFrom, Integer maxResults) {
        List<TotalQA> result = new ArrayList<TotalQA>();
        long counter;
        List<Category> categories = new ArrayList<Category>();
        String name;
        //String query = "select c.* from Category c ";
        //Query q = em.createNativeQuery(query, Category.class);
        categories = em.createNamedQuery("Category.findAll").getResultList(); //q.getResultList();

        for (int i = 0; i < categories.size(); i++) {
            counter = categories.get(i).getQas().size();
            name = categories.get(i).getName();
            result.add(new TotalQA(name, counter));
        }
        return result;
    }

    public List<QaNote> QANote(Integer startFrom, Integer maxResults, NoteType noteType, Date dateUpdateFrom,
                               Date dateUpdateTo) {
        List<QaNote> result;
        String typeOfNote = null;
        String query =
            "select q from QaNote q " + "where q.noteType.typeOfNote = :type and q.dateUpdate >= :date1 and q.dateUpdate <= :date2";
        if (noteType != null) {
            typeOfNote = noteType.getTypeOfNote();
        }

        Query q = em.createQuery(query);
        q.setParameter("type", typeOfNote);
        q.setParameter("date1", dateUpdateFrom);
        q.setParameter("date2", dateUpdateTo);

        if (startFrom != null)
            q.setFirstResult(startFrom);
        if (maxResults != null)
            q.setMaxResults(maxResults);
        result = q.getResultList();
        return result;
    }

    public List<Document> DOCFilter(Integer startFrom, Integer maxResults, DocType docType, DocSource docSource,
                                    Category category) {
        List<Document> result;

        String qdocType;
        String qdocSource;
        String qcategory;

        String lVeznik, dVeznik,qWhere;
        lVeznik = "";
        dVeznik = "";
        qWhere = " where ";

        if (docType != null) {
            qdocType = " d.docType = :type ";
            if (category != null) {
                qcategory = " d.categories = :cat ";
                if (docSource != null) {
                    qdocSource = "and d.docSource = :source and";
                } else {
                    qdocSource = " and ";
                }
            } else {
                qcategory = "";
                if (docSource != null) {
                    qdocSource = "and d.docSource = :source ";
                } else {
                    qdocSource = "";
                }
            }
        } else {
            qdocType = "";
            if (category != null) {
                qcategory = " d.categories = :cat ";
                if (docSource != null) {
                    qdocSource = " d.docSource = :source and";
                } else {
                    qdocSource = "";
                }
            } else {
                qcategory = "";
                if (docSource != null) {
                    qdocSource = "d.docSource = :source ";
                } else {
                    qdocSource = "";
                    qWhere = "";
                }
            }
        }


        String query = "select d from Document d " + qWhere + qdocType + lVeznik + qdocSource + dVeznik + qcategory;


        Query q = em.createQuery(query);
        if (docType != null) {
            q.setParameter("type", docType);
        }
        if (category != null) {
            q.setParameter("cat", category);
        }
        if (docSource != null) {
            q.setParameter("source", docSource);
        }

        if (startFrom != null)
            q.setFirstResult(startFrom);
        if (maxResults != null)
            q.setMaxResults(maxResults);
        result = q.getResultList();
        return result;
    }

    public List<Document> DocumentMostVisited(Integer startFrom, Integer maxResults) {
        List<Document> result;
        String query = "select d from Document d " + "order by d.counter desc";

        Query q = em.createQuery(query);
        if (startFrom != null)
            q.setFirstResult(startFrom);
        if (maxResults != null)
            q.setMaxResults(maxResults);
        result = q.getResultList();
        return result;
    }

    public List<Document> DocumentMostUseful(Integer startFrom, Integer maxResults) {
        List<Document> result;
        String query = "select d from Document d " + "order by d.usefulY desc";

        Query q = em.createQuery(query);
        if (startFrom != null)
            q.setFirstResult(startFrom);
        if (maxResults != null)
            q.setMaxResults(maxResults);
        result = q.getResultList();
        return result;
    }

    public List<Document> DocumentMostUnuseful(Integer startFrom, Integer maxResults) {
        List<Document> result;
        String query = "select d from Document d " + "order by d.usefulN desc";

        Query q = em.createQuery(query);
        if (startFrom != null)
            q.setFirstResult(startFrom);
        if (maxResults != null)
            q.setMaxResults(maxResults);
        result = q.getResultList();
        return result;
    }

    public List<Document> DocumentUncategorized(Integer startFrom, Integer maxResults) {
        List<Document> result;
        String query = "select d from Document d " + "where d.categories is empty";

        Query q = em.createQuery(query);
        if (startFrom != null)
            q.setFirstResult(startFrom);
        if (maxResults != null)
            q.setMaxResults(maxResults);
        result = q.getResultList();
        return result;
    }

    public List<TotalQA> DocumentDownloadedByCategories(Integer startFrom, Integer maxResults) {
        List<TotalQA> result = new ArrayList<TotalQA>();
        Long counter;
        List<Category> categories;
        List<Document> documents;
        String name;
        String query = "select c from Category c ";
        Query q = em.createQuery(query);
        categories = q.getResultList();

        for (int i = 0; i < categories.size(); i++) {
            documents = categories.get(i).getDocuments();
            name = categories.get(i).getName();
            counter = Long.valueOf(0);
            for (int j = 0; j < documents.size(); j++) {
                counter += documents.get(j).getCounter();
            }
            result.add(new TotalQA(name, counter));
        }
        return result;
    }

    public List<Qa> QARecommendedNotApproved(Integer startFrom, Integer maxResults) {
        List<Qa> result;
        String query =
            "select q from Qa q " + "where q.contentRecommended = 1 and (q.contentApproved is null or q.contentApproved = 0)" +
            "order by q.qaId";

        Query q = em.createQuery(query);
        if (startFrom != null)
            q.setFirstResult(startFrom);
        if (maxResults != null)
            q.setMaxResults(maxResults);
        result = q.getResultList();
        return result;
    }

    public NumberOfEmails EmailReceivedVsSent(Integer startFrom, Integer maxResults, Date dateUpdateFrom,
                                              Date dateUpdateTo) {
        NumberOfEmails result;
        List<Email> result1, result2;
        long counter1, counter2;
        String query1 =
            "select e from Email e " + "where e.status=1 and e.dateUpdate >= :date1 and e.dateUpdate <= :date2";
        String query2 =
            "select e from Email e " + "where e.status=3 and e.dateUpdate >= :date1 and e.dateUpdate <= :date2";

        Query q1 = em.createQuery(query1);
        q1.setParameter("date1", dateUpdateFrom);
        q1.setParameter("date2", dateUpdateTo);
        Query q2 = em.createQuery(query2);
        q2.setParameter("date1", dateUpdateFrom);
        q2.setParameter("date2", dateUpdateTo);


        result1 = q1.getResultList();
        counter1 = (long)result1.size();
        result2 = q2.getResultList();
        counter2 = (long)result2.size();
        result = new NumberOfEmails(counter1, counter2);
        return result;
    }

    public List<NumberOfDays> EmailLastDaysUntilAnswer(Integer startFrom, Integer maxResults, Date dateUpdateFrom,
                                                       Date dateUpdateTo) {
        List<NumberOfDays> result = new ArrayList<NumberOfDays>();
        List<EmailLastDays> emails;
        Long diff;
        String uniqueId, firstName, lastName;
        Timestamp dateUpdateReceived, dateUpdateSent;
        String query =
            "select a.uniqueId,  a.firstName, a.lastName, min(a.dateUpdate), min(b.dateUpdate)  from Email a, Email b " +
            "where a.status=1 and b.status=3 and a.uniqueId = b.uniqueId and a.dateUpdate >= :date1 and a.dateUpdate <= :date2 " +
            "group by a.uniqueId,  a.firstName, a.lastName, a.law";
        /*String query = "select a.first_name, a.last_name, a.subject, a.body, min(b.date_update)-min(a.date_update) numberOfDays from Email a, Email b " +
            "where a.status=1 and b.status=3 and a.unique_id = b.unique_id and a.date_update >= :date1 and a.date_update <= :date2 " +
            "group by a.unique_id,  a.first_name, a.last_name, a.subject, a.body";*/

        Query q = em.createQuery(query);
        q.setParameter("date1", dateUpdateFrom);
        q.setParameter("date2", dateUpdateTo);
        emails = q.getResultList();
        for (int i = 0; i < emails.size(); i++) {
            uniqueId = emails.get(i).getUniqueId();
            firstName = emails.get(i).getFirstName();
            lastName = emails.get(i).getLastName();
            dateUpdateReceived = emails.get(i).getDateUpdateReceived();
            dateUpdateSent = emails.get(i).getDateUpdateSent();
            diff = (dateUpdateSent.getTime() - dateUpdateReceived.getTime()) / (1000L * 60L * 60L * 24L);
            result.add(new NumberOfDays(uniqueId, firstName, lastName, diff));
        }
        return result;
    }

    /** <code>select o from NoteType o where o.activeYn = :active</code> */
    public List<NoteType> getNoteTypeFindActive(Object active) {
        return em.createNamedQuery("NoteType.findActive").setParameter("active", active).getResultList();
    }

}
