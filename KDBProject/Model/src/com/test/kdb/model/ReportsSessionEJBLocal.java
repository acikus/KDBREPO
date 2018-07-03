package com.test.kdb.model;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import com.test.kdb.model.entity.Category;
import com.test.kdb.model.entity.DocSource;
import com.test.kdb.model.entity.DocType;
import com.test.kdb.model.entity.NoteType;
import com.test.kdb.model.entity.Qa;
import com.test.kdb.model.entity.Document;
import com.test.kdb.model.entity.QaNote;
import com.test.kdb.reports.NumberOfDays;
import com.test.kdb.reports.NumberOfEmails;
import com.test.kdb.reports.TotalQA;

@Local
public interface ReportsSessionEJBLocal {
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);
    
    List<Qa> QAMostVisited(Integer startFrom, Integer maxResults);
    
    List<Qa> QAMostUseful(Integer startFrom, Integer maxResults);
    
    List<Qa> QAMostUnuseful(Integer startFrom, Integer maxResults);
    
    List<Qa> QAInInactiveCategories(Integer startFrom, Integer maxResults);
    
    List<Qa> QAMostRatioUsefulness(Integer startFrom, Integer maxResults);
    
    List<Qa> QAUpdated(Integer startFrom, Integer maxResults, Date dateUpdateFrom, Date dateUpdateTo);
    
    List<Document> DOCInInactiveCategories(Integer startFrom, Integer maxResults);
    
    List<Document> DOCMostRatioUsefulness(Integer startFrom, Integer maxResults);
    
    List<Document> DOCUpdated(Integer startFrom, Integer maxResults, Date dateUpdateFrom, Date dateUpdateTo);
    
    List<TotalQA> QATotal(Integer startFrom, Integer maxResults);
    
    List<QaNote> QANote(Integer startFrom, Integer maxResults, NoteType noteType, Date dateUpdateFrom, Date dateUpdateTo);
    
    List<Document> DOCFilter(Integer startFrom, Integer maxResults, DocType docType, DocSource docSource, Category category);
    
    List<Document> DocumentMostVisited(Integer startFrom, Integer maxResults);
    
    List<Qa> QARecommendedNotApproved(Integer startFrom, Integer maxResults);
    
    List<Document> DocumentUncategorized(Integer startFrom, Integer maxResults);
    
    List<Document> DocumentMostUseful(Integer startFrom, Integer maxResults);
    
    List<Document> DocumentMostUnuseful(Integer startFrom, Integer maxResults);
    
    List<TotalQA> DocumentDownloadedByCategories(Integer startFrom, Integer maxResults);
    
    NumberOfEmails EmailReceivedVsSent(Integer startFrom, Integer maxResults, Date dateUpdateFrom, Date dateUpdateTo);
    
    List<NumberOfDays> EmailLastDaysUntilAnswer(Integer startFrom, Integer maxResults, Date dateUpdateFrom, Date dateUpdateTo);

    List<NoteType> getNoteTypeFindActive(Object active);

}
