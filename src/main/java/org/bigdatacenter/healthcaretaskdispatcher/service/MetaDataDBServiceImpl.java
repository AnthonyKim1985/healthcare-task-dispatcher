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
        try {
            return metaDataDBMapper.readAcceptedExtractionRequest();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Integer> findAcceptedWorkFlowRequest() {
        try {
            return metaDataDBMapper.readAcceptedWorkFlowRequest();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}