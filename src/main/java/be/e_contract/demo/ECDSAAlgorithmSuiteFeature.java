package be.e_contract.demo;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.ws.security.policy.custom.AlgorithmSuiteLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ECDSAAlgorithmSuiteFeature implements Feature {

    private static final Logger LOGGER = LoggerFactory.getLogger(ECDSAAlgorithmSuiteFeature.class);

    @Override
    public void initialize(Server server, Bus bus) {
        LOGGER.debug("initialize server");
    }

    @Override
    public void initialize(Client client, Bus bus) {
        LOGGER.debug("initialize client");
    }

    @Override
    public void initialize(InterceptorProvider ip, Bus bus) {
        LOGGER.debug("initialize interceptor provider");
        bus.setExtension(new ECDSAAlgorithmSuiteLoader(), AlgorithmSuiteLoader.class);
    }

    @Override
    public void initialize(Bus bus) {
        LOGGER.debug("initialize bus");
    }
}
