package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome.getaggregatedecgoutcome.integrationtest;

import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.clinicalprocess.healthcond.actoutcome.getecgoutcome.v1.rivtabp21.GetECGOutcomeResponderInterface;
import riv.clinicalprocess.healthcond.actoutcome.getecgoutcomeresponder.v1.GetECGOutcomeResponseType;
import riv.clinicalprocess.healthcond.actoutcome.getecgoutcomeresponder.v1.GetECGOutcomeType;
import se.skltp.agp.test.producer.TestProducerDb;

@WebService(serviceName = "GetRequestStatusResponderService", portName = "GetRequestStatusResponderPort", targetNamespace = "urn:riv:clinicalprocess:healthcond:actoutcome:GetRequestStatus:1:rivtabp21", name = "GetRequestStatusInteraction")
public class GetAggregatedECGOutcomeTestProducer implements GetECGOutcomeResponderInterface {

	private static final Logger log = LoggerFactory.getLogger(GetAggregatedECGOutcomeTestProducer.class);

	private TestProducerDb testDb;
	
	public void setTestDb(TestProducerDb testDb) {
		this.testDb = testDb;
	}

	public GetECGOutcomeResponseType getECGOutcome(String logicalAddress, GetECGOutcomeType request) {
		GetECGOutcomeResponseType response = null;
		
		if (request == null) {
            throw new RuntimeException("GetFunctionalStatusType is null");
        }
        if (request.getPatientId() == null) {
            throw new RuntimeException("GetFunctionalStatusType.getPatientId is null");
        }
        if (StringUtils.isBlank(request.getPatientId().getId())) {
            throw new RuntimeException("GetFunctionalStatusType.patientId.id is blank");
        }
        
        log.info("### Virtual service for GetFunctionalStatus call the source system with logical address: {} and patientId: {}", 
                logicalAddress, request.getPatientId().getId());

        response = (GetECGOutcomeResponseType)testDb.processRequest(logicalAddress, request.getPatientId().getId());
        if (response == null) {
            // Return an empty response object instead of null if nothing is found
            response = new GetECGOutcomeResponseType();
        }

        log.info("### Virtual service got {} bookings in the reply from the source system with logical address: {} and patientId: {}", 
                new Object[] {response.getEcgOutcome().size(), logicalAddress, request.getPatientId().getId()});

        return response;
	}
}