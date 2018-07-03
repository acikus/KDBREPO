package com.test.kdb.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

@Entity
@NamedQueries({
  @NamedQuery(name = "DocumentChunks.findAll", query = "select o from DocumentChunks o")
})
public class DocumentChunks implements Serializable {
    @Id
    @Column(name="CHUNK", nullable = false)
    private Long chunk;
    @Column(name="CHUNK_BODY")
    private String chunkBody;
    @Column(name="CHUNK_START")
    private Long chunkStart;
    @Column(name="CHUNK_END")
    private Long chunkEnd;

    public DocumentChunks() {
    }

    public Long getChunk() {
        return chunk;
    }

    public String getChunkBody() {
        return chunkBody;
    }

    public Long getChunkStart() {
        return chunkStart;
    }

    public Long getChunkEnd() {
        return chunkEnd;
    }

    public void setChunk(Long chunk) {
        this.chunk = chunk;
    }

    public void setChunkBody(String chunkBody) {
        this.chunkBody = chunkBody;
    }

    public void setChunkStart(Long chunkStart) {
        this.chunkStart = chunkStart;
    }

    public void setChunkEnd(Long chunkEnd) {
        this.chunkEnd = chunkEnd;
    }
}
