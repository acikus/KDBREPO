package com.test.kdb.reports;

public class NumberOfEmails {
    private Long numberOfReceivedEmails;
    private Long numberOfSentEmails;
    
    public NumberOfEmails() {
        super();
    }

    public NumberOfEmails(Long numberOfReceivedEmails,
                          Long numberOfSentEmails) {
        super();
        this.numberOfReceivedEmails = numberOfReceivedEmails;
        this.numberOfSentEmails = numberOfSentEmails;
    }

    public void setNumberOfReceivedEmails(Long numberOfReceivedEmails) {
        this.numberOfReceivedEmails = numberOfReceivedEmails;
    }

    public Long getNumberOfReceivedEmails() {
        return numberOfReceivedEmails;
    }

    public void setNumberOfSentEmails(Long numberOfSentEmails) {
        this.numberOfSentEmails = numberOfSentEmails;
    }

    public Long getNumberOfSentEmails() {
        return numberOfSentEmails;
    }
}
