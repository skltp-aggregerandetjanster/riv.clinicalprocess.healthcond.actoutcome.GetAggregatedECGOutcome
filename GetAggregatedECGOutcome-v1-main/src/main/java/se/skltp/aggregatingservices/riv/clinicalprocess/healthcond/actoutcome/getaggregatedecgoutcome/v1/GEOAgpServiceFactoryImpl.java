package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome.getaggregatedecgoutcome.v1;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import riv.clinicalprocess.healthcond.actoutcome.enums.v3.ResultCodeEnum;
import riv.clinicalprocess.healthcond.actoutcome.getecgoutcomeresponder.v1.GetECGOutcomeResponseType;
import riv.clinicalprocess.healthcond.actoutcome.getecgoutcomeresponder.v1.GetECGOutcomeType;
import riv.clinicalprocess.healthcond.actoutcome.v3.ResultType;
import se.skltp.aggregatingservices.AgServiceFactoryBase;

@Log4j2
public class GEOAgpServiceFactoryImpl extends
    AgServiceFactoryBase<GetECGOutcomeType, GetECGOutcomeResponseType> {

  @Override
  public String getPatientId(GetECGOutcomeType queryObject) {
    return queryObject.getPatientId().getId();
  }

  @Override
  public String getSourceSystemHsaId(GetECGOutcomeType queryObject) {
    return queryObject.getSourceSystemHSAId();
  }

  @Override
  public GetECGOutcomeResponseType aggregateResponse(List<GetECGOutcomeResponseType> aggregatedResponseList) {

    GetECGOutcomeResponseType aggregatedResponse = new GetECGOutcomeResponseType();

    for (Object object : aggregatedResponseList) {
      GetECGOutcomeResponseType response = (GetECGOutcomeResponseType) object;
      aggregatedResponse.getEcgOutcome().addAll(response.getEcgOutcome());
    }

    aggregatedResponse.setResult(new ResultType());
    aggregatedResponse.getResult().setResultCode(ResultCodeEnum.INFO);
    aggregatedResponse.getResult().setLogId("NA");

    log.info("Returning {} aggregated ECGOutcome V1", aggregatedResponse.getEcgOutcome().size());
    return aggregatedResponse;
  }
}

