package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome.getaggregatedecgoutcome.integrationtest;

import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.clinicalprocess.healthcond.actoutcome.getecgoutcome.v1.rivtabp21.GetECGOutcomeResponderInterface;
import riv.clinicalprocess.healthcond.actoutcome.getecgoutcomeresponder.v1.GetECGOutcomeResponseType;
import riv.clinicalprocess.healthcond.actoutcome.getecgoutcomeresponder.v1.GetECGOutcomeType;
import se.skltp.agp.test.producer.TestProducerDb;

@WebService(serviceName = "GetECGOutcomeResponderService", 
               portName = "GetECGOutcomeResponderPort", 
        targetNamespace = "urn:riv:clinicalprocess:healthcond:actoutcome:GetECGOutcome:1:rivtabp21", 
                   name = "GetECGOutcomeInteraction")
public class GetAggregatedECGOutcomeTestProducer implements GetECGOutcomeResponderInterface {

	private static final Logger log = LoggerFactory.getLogger(GetAggregatedECGOutcomeTestProducer.class);

	private TestProducerDb testDb;
	
	public void setTestDb(TestProducerDb testDb) {
		this.testDb = testDb;
	}

	public GetECGOutcomeResponseType getECGOutcome(String logicalAddress, GetECGOutcomeType request) {
		GetECGOutcomeResponseType response = null;
		
		if (request == null) {
            throw new RuntimeException("GetECGOutcomeType is null");
        }
        if (request.getPatientId() == null) {
            throw new RuntimeException("GetECGOutcomeType.getPatientId is null");
        }
        if (StringUtils.isBlank(request.getPatientId().getId())) {
            throw new RuntimeException("GetECGOutcomeType.patientId.id is blank");
        }
        
        log.info("### Virtual service for GetECGOutcomeType call the source system with logical address: {} and patientId: {}", 
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