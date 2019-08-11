package com.interview.jpmorgan.api;

import java.util.UUID;

/**
 * Copyright Babelway 2017.
 */
public class ApiMessage {
    private UUID id;
    private String company;
    private String content;

    public ApiMessage(UUID id, String company, String content) {
        this.id = id;
        this.company = company;
        this.content = content;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
