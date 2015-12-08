package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome.getaggregatedecgoutcome;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.clinicalprocess.healthcond.actoutcome.getecgoutcomeresponder.v1.GetECGOutcomeType;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentResponseType;
import se.skltp.agp.riv.itintegration.engagementindex.v1.EngagementType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.RequestListFactory;

public class RequestListFactoryImpl implements RequestListFactory {

	private static final Logger log = LoggerFactory.getLogger(RequestListFactoryImpl.class);

	/**
	 * Svarsposter från EI som passerat filtreringen grupperas på fältet sourceSystem
	 * 
	 */
	public List<Object[]> createRequestList(QueryObject qo, FindContentResponseType src) {

		List<Object[]> requestsToBeSentToSourceSystems = new ArrayList<Object[]>();
        FindContentResponseType findContentResponse = (FindContentResponseType) src;
        List<EngagementType> engagements = findContentResponse.getEngagement();
        log.debug("Got {} hits in the engagement index", engagements.size());

        if (!engagements.isEmpty()) {
            Set<String> sourceSystems = new HashSet<String>(); // set of unique source system hsa ids
            
            for (EngagementType engagement : engagements) {
                sourceSystems.add(engagement.getSourceSystem());
            }
            if (sourceSystems.isEmpty()) {
                log.debug("No engagements found for ECGOutcome categorization");
            } else {
                log.debug("Preparing to call {} different source systems", sourceSystems.size());
                GetECGOutcomeType request = (GetECGOutcomeType) qo.getExtraArg(); // consumer's original request
                for (String sourceSystem : sourceSystems) {
                    log.info("Preparing to call source system {} for subject of care id {}", sourceSystem, request.getPatientId() == null ? null : request.getPatientId().getId());
                    // the original request is sent unchanged to each sourceSystem
                    Object[] reqArr = new Object[] { sourceSystem, request };
                    requestsToBeSentToSourceSystems.add(reqArr);
                }
            }
        }
        return requestsToBeSentToSourceSystems;
	}
	
}