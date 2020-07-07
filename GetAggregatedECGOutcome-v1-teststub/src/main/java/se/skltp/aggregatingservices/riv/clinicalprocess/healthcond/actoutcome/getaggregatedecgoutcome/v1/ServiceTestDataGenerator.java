package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome.getaggregatedecgoutcome.v1;

import lombok.extern.log4j.Log4j2;
import org.apache.cxf.message.MessageContentsList;
import org.springframework.stereotype.Service;
import riv.clinicalprocess.healthcond.actoutcome.getecgoutcomeresponder.v1.GetECGOutcomeResponseType;
import riv.clinicalprocess.healthcond.actoutcome.getecgoutcomeresponder.v1.GetECGOutcomeType;
import riv.clinicalprocess.healthcond.actoutcome.v3.ECGOutcomeBodyType;
import riv.clinicalprocess.healthcond.actoutcome.v3.ECGOutcomeType;
import riv.clinicalprocess.healthcond.actoutcome.v3.PatientSummaryHeaderType;
import riv.clinicalprocess.healthcond.actoutcome.v3.PersonIdType;
import se.skltp.aggregatingservices.data.TestDataGenerator;

@Log4j2
@Service
public class ServiceTestDataGenerator extends TestDataGenerator {

  @Override
  public String getPatientId(MessageContentsList messageContentsList) {
    GetECGOutcomeType request = (GetECGOutcomeType) messageContentsList.get(1);
    return request.getPatientId().getId();
  }

  @Override
  public Object createResponse(Object... responseItems) {
    log.info("Creating a response with {} items", responseItems.length);
    GetECGOutcomeResponseType response = new GetECGOutcomeResponseType();
    for (int i = 0; i < responseItems.length; i++) {
      response.getEcgOutcome().add((ECGOutcomeType) responseItems[i]);
    }

    log.info("response.toString:" + response.toString());

    return response;
  }

  @Override
  public Object createResponseItem(String logicalAddress, String registeredResidentId, String businessObjectId, String time) {
    log.debug("Created ResponseItem for logical-address {}, registeredResidentId {} and businessObjectId {}",
        new Object[]{logicalAddress, registeredResidentId, businessObjectId});

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

  public Object createRequest(String patientId, String sourceSystemHSAId) {
    GetECGOutcomeType outcomeType = new GetECGOutcomeType();

    outcomeType.setSourceSystemHSAId(sourceSystemHSAId);
    PersonIdType personIdType = new PersonIdType();
    personIdType.setType("1.2.752.129.2.1.3.1");
    personIdType.setId(patientId);
    outcomeType.setPatientId(personIdType);

    return outcomeType;
  }
}
