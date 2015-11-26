package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome.getaggregatedecgoutcome.integrationtest;

import static se.skltp.agp.test.producer.TestProducerDb.TEST_RR_ID_ONE_HIT;

import javax.xml.ws.Holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.clinicalprocess.healthcond.actoutcome.getecgoutcome.v1.rivtabp21.GetECGOutcomeResponderInterface;
import riv.clinicalprocess.healthcond.actoutcome.getecgoutcomeresponder.v1.GetECGOutcomeResponseType;
import riv.clinicalprocess.healthcond.actoutcome.getecgoutcomeresponder.v1.GetECGOutcomeType;
import riv.clinicalprocess.healthcond.actoutcome.v3.PersonIdType;
import se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome.getaggregatedecgoutcome.GetAggregatedECGOutcomeMuleServer;
import se.skltp.agp.test.consumer.AbstractTestConsumer;
import se.skltp.agp.test.consumer.SoapHeaderCxfInterceptor;
import se.skltp.agp.riv.interoperability.headers.v1.ProcessingStatusType;

public class GetAggregatedECGOutcomeTestConsumer extends AbstractTestConsumer<GetECGOutcomeResponderInterface> {

	private static final Logger log = LoggerFactory.getLogger(GetAggregatedECGOutcomeTestConsumer.class);

	public static void main(String[] args) {
		String serviceAddress = GetAggregatedECGOutcomeMuleServer.getAddress("SERVICE_INBOUND_URL");
		String personnummer = TEST_RR_ID_ONE_HIT;

		GetAggregatedECGOutcomeTestConsumer consumer = new GetAggregatedECGOutcomeTestConsumer(serviceAddress, SAMPLE_SENDER_ID, SAMPLE_ORIGINAL_CONSUMER_HSAID, SAMPLE_CORRELATION_ID);
		Holder<GetECGOutcomeResponseType> responseHolder = new Holder<GetECGOutcomeResponseType>();
		Holder<ProcessingStatusType> processingStatusHolder = new Holder<ProcessingStatusType>();

		consumer.callService("logical-adress", personnummer, processingStatusHolder, responseHolder);
	}

	public GetAggregatedECGOutcomeTestConsumer(String serviceAddress, String senderId, String originalConsumerHsaId, String correlationId) {
		// Setup a web service proxy for communication using HTTPS with Mutual Authentication
		super(GetECGOutcomeResponderInterface.class, serviceAddress, senderId, originalConsumerHsaId, correlationId);
	}

	public void callService(String logicalAddress, String registeredResidentId, Holder<ProcessingStatusType> processingStatusHolder, Holder<GetECGOutcomeResponseType> responseHolder) {
		log.debug("Calling GetECGOutcome-soap-service with Registered Resident Id = {}", registeredResidentId);

		GetECGOutcomeType request = new GetECGOutcomeType();
		final PersonIdType personId = new PersonIdType();
		personId.setId(registeredResidentId);
		personId.setType("1.2.752.129.2.1.3.1");
		request.setPatientId(personId);

		final GetECGOutcomeResponseType response = _service.getECGOutcome(logicalAddress, request);
		responseHolder.value = response;

		processingStatusHolder.value = SoapHeaderCxfInterceptor.getLastFoundProcessingStatus();
	}
}
