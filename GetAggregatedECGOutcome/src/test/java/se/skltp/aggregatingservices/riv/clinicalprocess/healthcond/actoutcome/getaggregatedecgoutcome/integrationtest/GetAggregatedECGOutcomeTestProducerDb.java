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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.clinicalprocess.healthcond.actoutcome.getecgoutcomeresponder.v1.GetECGOutcomeResponseType;
import riv.clinicalprocess.healthcond.actoutcome.v3.ECGOutcomeBodyType;
import riv.clinicalprocess.healthcond.actoutcome.v3.ECGOutcomeType;
import riv.clinicalprocess.healthcond.actoutcome.v3.PatientSummaryHeaderType;
import riv.clinicalprocess.healthcond.actoutcome.v3.PersonIdType;
import se.skltp.agp.test.producer.TestProducerDb;

public class GetAggregatedECGOutcomeTestProducerDb extends TestProducerDb {

	private static final Logger log = LoggerFactory.getLogger(GetAggregatedECGOutcomeTestProducerDb.class);

	@Override
	public Object createResponse(Object... responseItems) {
		log.debug("Create a response with {} items", responseItems.length);
		GetECGOutcomeResponseType response = new GetECGOutcomeResponseType();
		for (int i = 0; i < responseItems.length; i++) {
			response.getEcgOutcome().add((ECGOutcomeType) responseItems[i]);
		}
		return response;
	}
	
	@Override
	public Object createResponseItem(String logicalAddress, String registeredResidentId, String businessObjectId, String time) {
	
         log.debug("Created one response item for logical-address {}, registeredResidentId {} and businessObjectId {}", 
                    new Object[] {logicalAddress, registeredResidentId, businessObjectId });
     
		 ECGOutcomeType response = new ECGOutcomeType();
		 
		 PatientSummaryHeaderType psht = new PatientSummaryHeaderType();
		 PersonIdType pit = new PersonIdType();
		 pit.setId(registeredResidentId);
		 pit.setType("1.2.752.129.2.1.3.1");
		 psht.setPatientId(pit);
		 psht.setSourceSystemHSAId(logicalAddress);
		 psht.setDocumentId(businessObjectId);
		 response.setEcgOutcomeHeader(psht);
		 
		 ECGOutcomeBodyType ecgOutcomeBody = new ECGOutcomeBodyType();
		 response.setEcgOutcomeBody(ecgOutcomeBody);
		 return response;
	}
}