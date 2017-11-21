package org.bigdatacenter.healthcaretaskdispatcher.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MetaDataDBMapper {
    Integer PROCESS_STATE_CODE_COMPLETED = 1;
    Integer PROCESS_STATE_CODE_PROCESSING = 2;
    Integer PROCESS_STATE_CODE_REJECTED = 3;
    Integer PROCESS_STATE_CODE_REQUESTED = 4;
    Integer PROCESS_STATE_CODE_ADMIN_ACCEPTED = 5;
    Integer PROCESS_STATE_CODE_REQUEST_ACCEPTED = 6;

    /*
    * Transaction Database Mapper
    */
    @Select("SELECT dataSetUID FROM health_care_ui.tr_dataset_list WHERE processState = 5 AND dataType = 1 ORDER BY dataSetUID ASC")
    List<Integer> readAcceptedExtractionRequest();

    @Select("SELECT dataSetUID FROM health_care_ui.tr_dataset_list WHERE processState = 5 AND dataType = 2 ORDER BY dataSetUID ASC")
    List<Integer> readAcceptedWorkFlowRequest();
}
