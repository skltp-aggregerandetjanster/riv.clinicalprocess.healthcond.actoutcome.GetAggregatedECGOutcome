package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome.getaggregatedecgoutcome.v1;

import riv.clinicalprocess.healthcond.actoutcome.getecgoutcomeresponder.v1.GetECGOutcomeResponseType;
import se.skltp.aggregatingservices.api.AgpServiceFactory;
import se.skltp.aggregatingservices.tests.CreateAggregatedResponseTest;
import se.skltp.aggregatingservices.data.TestDataGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class GEOCreateAggregatedResponseTest extends CreateAggregatedResponseTest {

  private static GEOAgpServiceConfiguration configuration = new GEOAgpServiceConfiguration();
  private static AgpServiceFactory<GetECGOutcomeResponseType> agpServiceFactory = new GEOAgpServiceFactoryImpl();
  private static ServiceTestDataGenerator testDataGenerator = new ServiceTestDataGenerator();

  public GEOCreateAggregatedResponseTest() {
    super(testDataGenerator, agpServiceFactory, configuration);
  }

  @Override
  public int getResponseSize(Object response) {
    GetECGOutcomeResponseType responseType = (GetECGOutcomeResponseType) response;
    return responseType.getEcgOutcome().size();
  }
}