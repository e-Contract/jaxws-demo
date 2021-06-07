package be.e_contract.demo;

import org.apache.neethi.Assertion;
import org.apache.neethi.Policy;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.wss4j.policy.model.AlgorithmSuite;

public class ECDSAAlgorithmSuite extends AlgorithmSuite {

    private static final Logger LOGGER = LoggerFactory.getLogger(ECDSAAlgorithmSuite.class);

    private static final int MAX_SKL = 256;
    private static final int MIN_AKL = 384;
    private static final int MAX_AKL = 4096;

    static {
        ALGORITHM_SUITE_TYPES.put(ECDSAAlgorithmSuiteLoader.ECDSA_ALGORITHM_SUITE_TYPE,
                new AlgorithmSuiteType(ECDSAAlgorithmSuiteLoader.ECDSA_ALGORITHM_SUITE_TYPE, SPConstants.SHA1,
                        SPConstants.AES128, SPConstants.KW_AES128, SPConstants.KW_RSA_OAEP, SPConstants.P_SHA1_L128,
                        SPConstants.P_SHA1_L128, 128, 128, 128, MAX_SKL, MIN_AKL, MAX_AKL));
        ALGORITHM_SUITE_TYPES.put(ECDSAAlgorithmSuiteLoader.ECDSA_ALGORITHM_SUITE_TYPE_256,
                new AlgorithmSuiteType(ECDSAAlgorithmSuiteLoader.ECDSA_ALGORITHM_SUITE_TYPE_256, SPConstants.SHA1,
                        SPConstants.AES256, SPConstants.KW_AES256, SPConstants.KW_RSA_OAEP, SPConstants.P_SHA1_L256,
                        SPConstants.P_SHA1_L192, 256, 192, 256, MAX_SKL, MIN_AKL, MAX_AKL));
    }

    public ECDSAAlgorithmSuite(SPConstants.SPVersion version, Policy nestedPolicy) {
        super(version, nestedPolicy);
        LOGGER.debug("constructor");
    }

    @Override
    protected AbstractSecurityAssertion cloneAssertion(final Policy nestedPolicy) {
        LOGGER.debug("cloneAssertion");
        return new ECDSAAlgorithmSuite(this.getVersion(), nestedPolicy);
    }

    @Override
    protected void parseCustomAssertion(final Assertion assertion) {
        LOGGER.debug("parseCustomAssertion");
        final String assertionNamespace = assertion.getName().getNamespaceURI();
        if (!ECDSAAlgorithmSuiteLoader.ECDSA_NAMESPACE.equals(assertionNamespace)) {
            return;
        }

        final String assertionName = assertion.getName().getLocalPart();
        if (ECDSAAlgorithmSuiteLoader.ECDSA_ALGORITHM_SUITE_TYPE.equals(assertionName)) {
            LOGGER.debug("parsing our own security policy: {}", assertionName);
            setAlgorithmSuiteType(ALGORITHM_SUITE_TYPES.get(ECDSAAlgorithmSuiteLoader.ECDSA_ALGORITHM_SUITE_TYPE));
            getAlgorithmSuiteType().setNamespace(assertionNamespace);
            setAsymmetricSignature("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1");
        } else if (ECDSAAlgorithmSuiteLoader.ECDSA_ALGORITHM_SUITE_TYPE_256.equals(assertionName)) {
            LOGGER.debug("parsing our own security policy: {}", assertionName);
            setAlgorithmSuiteType(
                    ALGORITHM_SUITE_TYPES.get(ECDSAAlgorithmSuiteLoader.ECDSA_ALGORITHM_SUITE_TYPE_256));
            getAlgorithmSuiteType().setNamespace(assertionNamespace);
            setAsymmetricSignature("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256");
        }
    }
}
