package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome.getaggregatedecgoutcome.v1;


import org.junit.jupiter.api.BeforeAll;
import riv.clinicalprocess.healthcond.actoutcome.getecgoutcomeresponder.v1.GetECGOutcomeResponseType;
import se.skltp.aggregatingservices.api.AgpServiceFactory;
import se.skltp.aggregatingservices.tests.CreateFindContentTest;
import se.skltp.aggregatingservices.data.TestDataGenerator;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class GEOCreateFindContentTest extends CreateFindContentTest {

  private static GEOAgpServiceConfiguration configuration = new GEOAgpServiceConfiguration();
  private static AgpServiceFactory<GetECGOutcomeResponseType> agpServiceFactory = new GEOAgpServiceFactoryImpl();
  private static ServiceTestDataGenerator testDataGenerator = new ServiceTestDataGenerator();

  public GEOCreateFindContentTest() {
    super(testDataGenerator, agpServiceFactory, configuration);
  }

  @BeforeAll
  public static void before() {
    configuration = new GEOAgpServiceConfiguration();
    agpServiceFactory = new GEOAgpServiceFactoryImpl();
    agpServiceFactory.setAgpServiceConfiguration(configuration);
  }


}
