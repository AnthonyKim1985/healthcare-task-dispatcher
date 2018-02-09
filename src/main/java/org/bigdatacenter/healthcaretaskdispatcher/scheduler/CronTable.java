package org.bigdatacenter.healthcaretaskdispatcher.scheduler;

import org.bigdatacenter.healthcaretaskdispatcher.api.caller.DataIntegrationPlatformAPICaller;
import org.bigdatacenter.healthcaretaskdispatcher.service.MetaDataDBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class CronTable {
    private static final Logger logger = LoggerFactory.getLogger(CronTable.class);
    private static final String currentThreadName = Thread.currentThread().getName();

    private static final int cronTabInitialDelay = 90000;
    private static final int cronTabFixedDelay = 10000;

    @Value("${integration-platform.rest.api.request.extraction}")
    private String extractionURL;

    @Value("${integration-platform.rest.api.request.workflow}")
    private String workflowURL;

    private final RestTemplate restTemplate;

    private final HttpHeaders headers;

    private final MetaDataDBService metaDataDBService;

    private final DataIntegrationPlatformAPICaller dataIntegrationPlatformAPICaller;

    @Autowired
    public CronTable(MetaDataDBService metaDataDBService, DataIntegrationPlatformAPICaller dataIntegrationPlatformAPICaller) {
        this.metaDataDBService = metaDataDBService;
        this.dataIntegrationPlatformAPICaller = dataIntegrationPlatformAPICaller;

        this.restTemplate = new RestTemplate();
        this.restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    }

    @Scheduled(initialDelay = cronTabInitialDelay, fixedDelay = cronTabFixedDelay)
    public void dispatchExtractionTask() {
        final List<Integer> dataSetUIDList = metaDataDBService.findAcceptedExtractionRequest();
        logger.debug(String.format("%s - The dispatchExtractionTask has been scheduled.", currentThreadName));

        for (Integer dataSetUID : dataSetUIDList) {
            try {
                final String response = restTemplate.getForObject(extractionURL, String.class, dataSetUID);
                logger.info(String.format("%s - The extraction request for #%d has been dispatched. (%s)", currentThreadName, dataSetUID, response));
            } catch (Exception e) {
                logger.error(String.format("%s - The REST API error occurs: %s", currentThreadName, e.getMessage()));
                e.printStackTrace();
            }
        }
    }

    @Scheduled(initialDelay = cronTabInitialDelay, fixedDelay = cronTabFixedDelay)
    public void dispatchWorkFlowTask() {
        final List<Integer> dataSetUIDList = metaDataDBService.findAcceptedWorkFlowRequest();
        logger.debug(String.format("%s - The dispatchWorkFlowTask has been scheduled.", currentThreadName));

        for (Integer dataSetUID : dataSetUIDList) {
            try {
                final String response = restTemplate.getForObject(workflowURL, String.class, dataSetUID);
                logger.info(String.format("%s - The scenario request for #%d has been dispatched. (%s)", currentThreadName, dataSetUID, response));
            } catch (Exception e) {
                dataIntegrationPlatformAPICaller.callUpdateProcessState(dataSetUID, DataIntegrationPlatformAPICaller.PROCESS_STATE_CODE_REJECTED);
                logger.error(String.format("%s - The REST API error occurs: %s", currentThreadName, e.getMessage()));
                e.printStackTrace();
            }
        }
    }
}