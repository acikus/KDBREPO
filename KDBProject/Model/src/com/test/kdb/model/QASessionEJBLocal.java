package com.test.kdb.model;

import java.util.List;

import javax.ejb.Local;

import com.test.kdb.model.entity.Category;
import com.test.kdb.model.entity.Document;
import com.test.kdb.model.entity.ExternalLink;
import com.test.kdb.model.entity.Language;
import com.test.kdb.model.entity.LogQa;
import com.test.kdb.model.entity.News;
import com.test.kdb.model.entity.NoteType;
import com.test.kdb.model.entity.Qa;
import com.test.kdb.model.entity.QaNote;
import com.test.kdb.model.entity.QastatsWeb;
import com.test.kdb.model.entity.User;

@Local
public interface QASessionEJBLocal {
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    LogQa persistLogQa(LogQa logQa);

    LogQa mergeLogQa(LogQa logQa);

    void removeLogQa(LogQa logQa);

    List<LogQa> getLogQaFindAll();

    QaNote persistQaNote(QaNote qaNote);

    QaNote mergeQaNote(QaNote qaNote);

    void removeQaNote(QaNote qaNote);

    List<QaNote> getQaNoteFindAll();

    QastatsWeb persistQastatsWeb(QastatsWeb qastatsWeb);

    QastatsWeb mergeQastatsWeb(QastatsWeb qastatsWeb);

    void removeQastatsWeb(QastatsWeb qastatsWeb);

    List<QastatsWeb> getQastatsWebFindAll();

    NoteType findOrCreateNoteType(NoteType noteType);
    
    NoteType persistNoteType(NoteType noteType);

    NoteType mergeNoteType(NoteType noteType);

    void removeNoteType(NoteType noteType);

    List<NoteType> listNoteType();
    
    List<NoteType> getNoteTypeFindAll();

    Qa persistQa(Qa qa);

    Qa mergeQa(Qa qa);

    void removeQa(Qa qa);

    List<Qa> getQaFindAll();
    
    Qa findOrCreate(Qa qa);
    
    Qa mergeOrPersist(Qa qa);

    Qa incrementQaCounter(Qa qa);
    
    Qa incrementQaUsefulY(Qa qa, User user);
    
    Qa incrementQaUsefulN(Qa qa, User user);

    public List<Qa> searchQA(
            Object term, Object maxCount, Object offset,
            Object searchKeywords, 
            Object searchQuestion, 
            Object searchShortAnswer, 
            Object searchDetailAnswer,
            Object language, 
            Object category);
    
    Boolean reindexQa();

    void qaAddCategory(Qa qa, Category category);
    void qaRemoveCategory(Qa qa, Category category);
    
        
    void qaAddQuestion(Qa qustion1, Qa qustion2);
    void qaRemoveQuestion(Qa qustion1, Qa qustion2);

    void qaAddExternalLink(Qa question, String urllink, String description);
    void qaRemoveExternalLink(Qa question, ExternalLink externalLink);
    
    void qaAddDocument(Qa qustion, Document document);
    void qaRemoveDocument(Qa qustion, Document document);

    Qa findQa(Long qaId);
    Category findQaCategory(Long categoryId);
    List<NoteType> getNoteTypeActiveOrNot(Object active);
    QaNote mergeQaNoteByUser( QaNote qaNote , User user);
    QaNote findOrCreate( QaNote source  );
    QaNote CreateNote( Qa qa);
    QaNote persistQaNoteByUser(QaNote qaNote ,  User user);

    List<Language> getLanguageFindActive(Object active);
}
