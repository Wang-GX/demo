package com.example.demo.Mybatis相关;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


public class Dymic extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }
}
