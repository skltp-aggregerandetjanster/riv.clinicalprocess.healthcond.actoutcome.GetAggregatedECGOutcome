package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome.getaggregatedecgoutcome;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.util.ThreadSafeSimpleDateFormat;

import riv.clinicalprocess.healthcond.actoutcome.getecgoutcomeresponder.v1.GetECGOutcomeType;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentResponseType;
import se.skltp.agp.riv.itintegration.engagementindex.v1.EngagementType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.RequestListFactory;

public class RequestListFactoryImpl implements RequestListFactory {

	private static final Logger log = LoggerFactory.getLogger(RequestListFactoryImpl.class);
	private static final ThreadSafeSimpleDateFormat df = new ThreadSafeSimpleDateFormat("yyyyMMddhhmmss");

	/**
	 * Svarsposter från EI som passerat filtreringen grupperas på fältet sourceSystem
	 * 
	 */
	public List<Object[]> createRequestList(QueryObject qo, FindContentResponseType src) {

        GetECGOutcomeType request = (GetECGOutcomeType) qo.getExtraArg(); // consumer's original request
		List<Object[]> requestsToBeSentToSourceSystems = new ArrayList<Object[]>();

		FindContentResponseType findContentResponse = (FindContentResponseType) src;
		
        String sourceSystemHsaId = request.getSourceSystemHSAId();
		Date reqFrom = parseTs(
		        (request.getDatePeriod() == null
		                ||
		                request.getDatePeriod().getStart() == null)
		                ?
		                null : request.getDatePeriod().getStart());
		 
		Date reqTo = parseTs(
		        (request.getDatePeriod() == null
		                ||
		                request.getDatePeriod().getEnd() == null)
		                ? null : request.getDatePeriod().getEnd());

        List<EngagementType> engagements = findContentResponse.getEngagement();
        log.debug("Got {} hits in the engagement index", engagements.size());

        if (!engagements.isEmpty()) {
            Set<String> sourceSystems = new HashSet<String>(); // set of unique source system hsa ids
            
            for (EngagementType engagement : engagements) {
    			if (isBetween(reqFrom, reqTo, engagement.getMostRecentContent())) {
	                if (isPartOf(sourceSystemHsaId, engagement.getLogicalAddress())) {
	                	sourceSystems.add(engagement.getSourceSystem());
	                }
    			}
            }
            
            if (sourceSystems.isEmpty()) {
                log.debug("No engagements found for ECGOutcome categorization");
            } else {
                log.debug("Preparing to call {} different source systems", sourceSystems.size());
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

	Date parseTs(String ts) {
		try {
			if (ts == null || ts.length() == 0) {
				return null;
			} else {
				return df.parse(ts);
			}
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	boolean isBetween(Date from, Date to, String tsStr) {
		try {
			if (log.isDebugEnabled()) {
				log.debug("Is {} between {} and ", new Object[] {tsStr, from, to});
			}
			
			Date ts = df.parse(tsStr);
			if (from != null && from.after(ts)) return false;
			if (to != null && to.before(ts)) return false;
			return true;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

    private boolean isPartOf(String careUnitId, String careUnit) {
        log.debug("Check presence of {} in {}", careUnit, careUnitId);
        if (StringUtils.isBlank(careUnitId))
            return true;
        return careUnitId.equals(careUnit);
    }

}