package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome.getaggregatedecgoutcome.v1;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import riv.clinicalprocess.healthcond.actoutcome.getecgoutcome.v1.rivtabp21.GetECGOutcomeResponderInterface;
import riv.clinicalprocess.healthcond.actoutcome.getecgoutcome.v1.rivtabp21.GetECGOutcomeResponderService;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "getaggregatedecgoutcome.v1")
public class GEOAgpServiceConfiguration extends se.skltp.aggregatingservices.configuration.AgpServiceConfiguration {

  public static final String SCHEMA_PATH = "/schemas/clinicalprocess_healthcond_description_3.1_RC3/interactions/GetECGOutcomeInteraction/GetECGOutcomeInteraction_1.0_RIVTABP21.wsdl";

  public GEOAgpServiceConfiguration() {

    setServiceName("GetAggregatedECGOutcome-v1");
    setTargetNamespace("urn:riv:clinicalprocess:healthcond:actoutcome:GetECGOutcome:1:rivtabp21");

    // Set inbound defaults
    setInboundServiceURL("http://0.0.0.0:9020/GetAggregatedECGOutcome/service/v1");
    setInboundServiceWsdl(SCHEMA_PATH);
    setInboundServiceClass(GetECGOutcomeResponderInterface.class.getName());
    setInboundPortName(GetECGOutcomeResponderService.GetECGOutcomeResponderPort.toString());

    // Set outbound defaults
    setOutboundServiceWsdl(SCHEMA_PATH);
    setOutboundServiceClass(getInboundServiceClass());
    setOutboundPortName(getInboundPortName());

    // FindContent
    setEiServiceDomain("riv:clinicalprocess:healthcond:actoutcome");
    setEiCategorization("und-ekg-ure");

    // TAK
    setTakContract("urn:riv:clinicalprocess:healthcond:actoutcome:GetECGOutcomeResponder:1");

    // Set service factory
    setServiceFactoryClass(GEOAgpServiceFactoryImpl.class.getName());
  }


}
