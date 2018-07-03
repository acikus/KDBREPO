package com.test.kdb.reports;

public class TotalQA {
    private String name;
    private Long counter;
    public TotalQA() {
        super();
    }

    public TotalQA(String name, Long counter) {
        super();
        this.name = name;
        this.counter = counter;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public Long getCounter() {
        return counter;
    }
}
