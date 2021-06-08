package be.e_contract.demo;

import be.e_contract.jaxws_demo.ExampleServicePortType;
import javax.servlet.annotation.WebServlet;
import javax.xml.ws.BindingType;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.soap.SOAPBinding;
import org.apache.cxf.annotations.EndpointProperties;
import org.apache.cxf.annotations.EndpointProperty;
import org.apache.cxf.ws.security.SecurityConstants;
import org.jboss.ws.api.annotation.EndpointConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServiceProvider(targetNamespace = "urn:be:e-contract:jaxws-demo",
        serviceName = "ExampleService",
        wsdlLocation = "example.wsdl", portName = "ExampleServicePort")
@WebServlet("/example/*")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@EndpointProperties({
    @EndpointProperty(key = SecurityConstants.SIGNATURE_PROPERTIES, value = "demo-ws-signature.properties"),
    @EndpointProperty(key = SecurityConstants.SUBJECT_CERT_CONSTRAINTS, value = ".*")})
@EndpointConfig(configFile = "WEB-INF/jbossws-jaxws-config.xml", configName = "ECDSAConfig")
public class ExampleServicePortImpl implements ExampleServicePortType {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleServicePortImpl.class);

    @Override
    public String echo(String echoRequest) {
        LOGGER.debug("echo: {}", echoRequest);
        return echoRequest;
    }
}
