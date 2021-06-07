package be.e_contract.demo;

import java.io.StringWriter;
import java.util.logging.Level;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.ws.security.policy.SPConstants;
import org.apache.cxf.ws.security.policy.custom.AlgorithmSuiteLoader;
import org.apache.cxf.ws.security.policy.custom.DefaultAlgorithmSuiteLoader;
import org.apache.cxf.ws.security.policy.model.AlgorithmSuite;
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
    public AlgorithmSuite getAlgorithmSuite(Element policyElement, SPConstants consts) {
        LOGGER.debug("getAlgorithmSuite");
        try {
            LOGGER.debug("policyElement: {}", toString(policyElement));
        } catch (TransformerException ex) {
            LOGGER.error("toString error: " + ex.getMessage(), ex);
        }
        AlgorithmSuite algorithmSuite = super.getAlgorithmSuite(policyElement, consts);
        if (null != algorithmSuite) {
            return algorithmSuite;
        }
        if (policyElement != null) {
            Element algorithm = DOMUtils.getFirstElement(policyElement);
            if (algorithm != null) {
                if (ECDSA_NAMESPACE.equals(algorithm.getNamespaceURI())) {
                    algorithmSuite = new ECDSAAlgorithmSuite();
                    return algorithmSuite;
                }
            }
        }
        return null;
    }

    private String toString(Element element) throws TransformerConfigurationException, TransformerException {
        TransformerFactory transformerFactory = TransformerFactory
                .newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(element);
        StreamResult result = new StreamResult(new StringWriter());

        transformer.transform(source, result);

        String strObject = result.getWriter().toString();
        return strObject;
    }
}
