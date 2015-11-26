package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.actoutcome.getaggregatedecgoutcome

trait CommonParameters {
  val serviceName:String     = "ECGOutcome"
  val urn:String             = "urn:riv:clinicalprocess:healthcond:actoutcome:GetECGOutcomeResponder:1"
  val responseElement:String = "GetECGOutcomeResponse"
  val responseItem:String    = "ecgOutcome"
  var baseUrl:String         = if (System.getProperty("baseUrl") != null && !System.getProperty("baseUrl").isEmpty()) {
                                   System.getProperty("baseUrl")
                               } else {
                                   "http://33.33.33.33:8081/GetAggregatedECGOutcome/service/v1"
                               }
}
