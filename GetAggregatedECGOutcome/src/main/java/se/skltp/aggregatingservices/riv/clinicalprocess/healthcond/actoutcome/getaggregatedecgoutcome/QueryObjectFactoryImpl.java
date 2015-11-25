package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome.getaggregatedecgoutcome;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.jaxb.JaxbUtil;
import org.w3c.dom.Node;

import riv.clinicalprocess.healthcond.actoutcome.getecgoutcomeresponder.v1.GetECGOutcomeType;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.QueryObjectFactory;

public class QueryObjectFactoryImpl implements QueryObjectFactory {

	private static final Logger log = LoggerFactory.getLogger(QueryObjectFactoryImpl.class);
	private static final JaxbUtil ju = new JaxbUtil(GetECGOutcomeType.class);

	private String eiServiceDomain;
	public void setEiServiceDomain(String eiServiceDomain) {
		this.eiServiceDomain = eiServiceDomain;
	}

	private String eiCategorization;
	public void setEiCategorization(String eiCategorization) {
		this.eiCategorization = eiCategorization;
	}

	/**
	 * Transformerar GetECGOutcomeType request till EI FindContent request enligt:
	 * 
	 * 1. subjectOfCareId --> registeredResidentIdentification
	 * 2. "riv:clinicalprocess:healthcond:actoutcome" --> serviceDomain
	 */
	public QueryObject createQueryObject(Node node) {
		
		GetECGOutcomeType request = (GetECGOutcomeType)ju.unmarshal(node);

		log.debug("Transformed payload for pid: {}", request.getPatientId().getId());

        FindContentType fc = new FindContentType();
        fc.setRegisteredResidentIdentification(request.getPatientId().getId());
        fc.setServiceDomain(eiServiceDomain);
        fc.setCategorization(eiCategorization);
        
        return new QueryObject(fc, request);

	}
}
