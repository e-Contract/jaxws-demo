package be.e_contract.demo;

import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.ws.policy.AssertionBuilderRegistry;
import org.apache.cxf.ws.policy.builder.primitive.PrimitiveAssertion;
import org.apache.cxf.ws.policy.builder.primitive.PrimitiveAssertionBuilder;
import org.apache.cxf.ws.security.policy.custom.DefaultAlgorithmSuiteLoader;
import org.apache.neethi.Assertion;
import org.apache.neethi.AssertionBuilderFactory;
import org.apache.neethi.Policy;
import org.apache.neethi.builders.xml.XMLPrimitiveAssertionBuilder;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AlgorithmSuite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class ECDSAAlgorithmSuiteLoader extends DefaultAlgorithmSuiteLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ECDSAAlgorithmSuiteLoader.class);

    public static final String ECDSA_NAMESPACE = "urn:be:e-contract:security-policy";

    public static final String ECDSA_ALGORITHM_SUITE_TYPE = "Basic128ECDSA";

    public static final String ECDSA_ALGORITHM_SUITE_TYPE_256 = "Basic256ECDSA";

    public ECDSAAlgorithmSuiteLoader() {
        LOGGER.debug("constructor");
    }

    @Override
    public AlgorithmSuite getAlgorithmSuite(Bus bus, SPConstants.SPVersion version, Policy nestedPolicy) {
        LOGGER.debug("getAlgorithmSuite");
        AssertionBuilderRegistry reg = bus.getExtension(AssertionBuilderRegistry.class);
        if (reg != null) {
            LOGGER.debug("register");
            final Map<QName, Assertion> assertions = new HashMap<>();
            QName qName = new QName(ECDSA_NAMESPACE, ECDSA_ALGORITHM_SUITE_TYPE);
            assertions.put(qName, new PrimitiveAssertion(qName));
            qName = new QName(ECDSA_NAMESPACE, ECDSA_ALGORITHM_SUITE_TYPE_256);
            assertions.put(qName, new PrimitiveAssertion(qName));

            reg.registerBuilder(new PrimitiveAssertionBuilder(assertions.keySet()) {
                public Assertion build(Element element, AssertionBuilderFactory fact) {
                    if (XMLPrimitiveAssertionBuilder.isOptional(element)
                            || XMLPrimitiveAssertionBuilder.isIgnorable(element)) {
                        return super.build(element, fact);
                    }
                    QName q = new QName(element.getNamespaceURI(), element.getLocalName());
                    return assertions.get(q);
                }
            });
        }
        return new ECDSAAlgorithmSuite(version, nestedPolicy);
    }
}
