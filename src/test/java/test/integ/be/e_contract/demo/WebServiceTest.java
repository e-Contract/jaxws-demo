package test.integ.be.e_contract.demo;

import be.e_contract.jaxws_demo.ExampleService;
import be.e_contract.jaxws_demo.ExampleServicePortType;
import javax.xml.ws.BindingProvider;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebServiceTest.class);

    @Test
    public void invokeWebService() throws Exception {
        LOGGER.debug("test");

        ExampleService service = new ExampleService();
        ExampleServicePortType port = service.getExampleServicePort();

        BindingProvider bindingProvider = (BindingProvider) port;
        bindingProvider.getRequestContext().put(
                BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "https://localhost/jaxws-demo/example");

        port.echo("hello world");
    }
}
