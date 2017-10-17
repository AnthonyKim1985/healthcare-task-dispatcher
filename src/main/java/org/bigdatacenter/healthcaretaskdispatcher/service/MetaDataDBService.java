package org.bigdatacenter.healthcaretaskdispatcher.service;

import java.util.List;

public interface MetaDataDBService {
    List<Integer> findAcceptedExtractionRequest();

    List<Integer> findAcceptedWorkFlowRequest();
}
