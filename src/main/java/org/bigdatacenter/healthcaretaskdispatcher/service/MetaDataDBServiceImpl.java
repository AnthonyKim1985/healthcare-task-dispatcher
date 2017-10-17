package org.bigdatacenter.healthcaretaskdispatcher.service;

import org.bigdatacenter.healthcaretaskdispatcher.persistence.MetaDataDBMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetaDataDBServiceImpl implements MetaDataDBService {
    private final MetaDataDBMapper metaDataDBMapper;

    @Autowired
    public MetaDataDBServiceImpl(MetaDataDBMapper metaDataDBMapper) {
        this.metaDataDBMapper = metaDataDBMapper;
    }

    @Override
    public List<Integer> findAcceptedExtractionRequest() {
        return metaDataDBMapper.readAcceptedExtractionRequest();
    }

    @Override
    public List<Integer> findAcceptedWorkFlowRequest() {
        return metaDataDBMapper.readAcceptedWorkFlowRequest();
    }
}