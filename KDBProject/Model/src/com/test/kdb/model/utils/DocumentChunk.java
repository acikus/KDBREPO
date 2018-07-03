package com.test.kdb.model.utils;

//import javax.persistence.EntityResult;
//import javax.persistence.FieldResult;
//import javax.persistence.SqlResultSetMapping;
//
//@SqlResultSetMapping(name = "DCResults",
//     entities = { @EntityResult(entityClass = DocumentChunk.class, 
//                fields = { 
//                         @FieldResult(name ="documentId",column = "DOCUMENT_ID"),
//                         @FieldResult(name ="chunk",     column = "CHUNK"),
//                         @FieldResult(name ="chunkBody", column = "CHUNK_BODY"),
//                         @FieldResult(name ="chunkStart",column = "CHUNK_START"),
//                         @FieldResult(name ="chunkEnd",  column = "CHUNK_END") }) })

public class DocumentChunk {

    private Long documentId;
    private Long chunk;
    private String chunkBody;
    private Long chunkStart;
    private Long chunkEnd;

    public DocumentChunk() {
        super();
    }

    public DocumentChunk( Long documentId, Long chunk, String chunkBody, Long chunkStart, Long chunkEnd) {
        super();
        
        this.documentId = documentId;
        this.chunk = chunk;
        this.chunkBody = chunkBody;
        this.chunkStart = chunkStart;
        this.chunkEnd = chunkEnd;
    }

    public void setChunk(Long chunk) {
        this.chunk = chunk;
    }

    public Long getChunk() {
        return chunk;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setChunkBody(String chunkBody) {
        this.chunkBody = chunkBody;
    }

    public String getChunkBody() {
        return chunkBody;
    }

    public void setChunkStart(Long chunkStart) {
        this.chunkStart = chunkStart;
    }

    public Long getChunkStart() {
        return chunkStart;
    }

    public void setChunkEnd(Long chunkEnd) {
        this.chunkEnd = chunkEnd;
    }

    public Long getChunkEnd() {
        return chunkEnd;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof DocumentChunk)) {
            return false;
        }
        final DocumentChunk other = (DocumentChunk)object;
        if (!(chunk == null ? other.chunk == null : chunk.equals(other.chunk))) {
            return false;
        }
        if (!(documentId == null ? other.documentId == null : documentId.equals(other.documentId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((chunk == null) ? 0 : chunk.hashCode());
        result = PRIME * result + ((documentId == null) ? 0 : documentId.hashCode());
        return result;
    }
}
