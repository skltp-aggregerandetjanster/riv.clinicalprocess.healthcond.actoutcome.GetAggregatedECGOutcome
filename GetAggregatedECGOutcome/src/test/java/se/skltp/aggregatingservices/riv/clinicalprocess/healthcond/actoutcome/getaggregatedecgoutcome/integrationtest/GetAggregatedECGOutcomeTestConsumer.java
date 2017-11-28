/**
 * Copyright (c) 2014 Inera AB, <http://inera.se/>
 *
 * This file is part of SKLTP.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
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

/**
 * Soap test consumer. Knows how to build a GetECGOutcomeType request, send it to the producer, receive a GetECGOutcomeResponseType.
 */
public class GetAggregatedECGOutcomeTestConsumer extends AbstractTestConsumer<GetECGOutcomeResponderInterface> {

	private static final Logger log = LoggerFactory.getLogger(GetAggregatedECGOutcomeTestConsumer.class);

	/**
	 * main - bootstrap the consumer.
	 */
	public static void main(String[] args) {
		String serviceAddress = GetAggregatedECGOutcomeMuleServer.getAddress("SERVICE_INBOUND_URL");
		String personnummer = TEST_RR_ID_ONE_HIT;

		GetAggregatedECGOutcomeTestConsumer consumer = new GetAggregatedECGOutcomeTestConsumer(serviceAddress, SAMPLE_SENDER_ID, SAMPLE_ORIGINAL_CONSUMER_HSAID, SAMPLE_CORRELATION_ID);
		Holder<GetECGOutcomeResponseType> responseHolder = new Holder<GetECGOutcomeResponseType>();
		Holder<ProcessingStatusType> processingStatusHolder = new Holder<ProcessingStatusType>();

		consumer.callService("logical-adress", personnummer, processingStatusHolder, responseHolder);
	}

	/**
	 * constructor - each test will build its own test consumer.
	 */
	public GetAggregatedECGOutcomeTestConsumer(String serviceAddress, String senderId, String originalConsumerHsaId, String correlationId) {
		// Setup a web service proxy for communication using HTTPS with Mutual Authentication
		super(GetECGOutcomeResponderInterface.class, serviceAddress, senderId, originalConsumerHsaId, correlationId);
	}

	/**
	 * @param logicalAddress - input parameter
	 * @param registeredResidentId - input parameter
	 * @param processingStatusHolder - output parameter for wrapping the status returned from the producer
	 * @param responseHolder - output parameter of wrapping the response returned from the producer
	 */
	public void callService(String logicalAddress, String registeredResidentId, Holder<ProcessingStatusType> processingStatusHolder, Holder<GetECGOutcomeResponseType> responseHolder) {
		log.debug("Calling GetECGOutcome-soap-service with registered resident id {}", registeredResidentId);

		// set up the input parameters
		GetECGOutcomeType request = new GetECGOutcomeType();
		final PersonIdType personId = new PersonIdType();
		personId.setId(registeredResidentId);
		personId.setType("1.2.752.129.2.1.3.1");
		request.setPatientId(personId);

		// call the producer
		final GetECGOutcomeResponseType response = _service.getECGOutcome(logicalAddress, request);
		
		// capture the results of calling the producer
		responseHolder.value = response;
		processingStatusHolder.value = SoapHeaderCxfInterceptor.getLastFoundProcessingStatus();
	}
}
