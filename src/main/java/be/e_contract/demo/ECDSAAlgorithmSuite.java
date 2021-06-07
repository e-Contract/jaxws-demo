package be.e_contract.demo;

import org.apache.cxf.ws.security.policy.model.AlgorithmSuite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ECDSAAlgorithmSuite extends AlgorithmSuite {

    private static final Logger LOGGER = LoggerFactory.getLogger(ECDSAAlgorithmSuite.class);

    public ECDSAAlgorithmSuite() {
        LOGGER.debug("constructor");
    }
}
