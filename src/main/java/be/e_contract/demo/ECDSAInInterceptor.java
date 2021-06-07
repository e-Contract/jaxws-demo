package be.e_contract.demo;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.ws.security.SecurityConstants;
import org.apache.cxf.ws.security.wss4j.policyvalidators.SecurityPolicyValidator;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP12Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ECDSAInInterceptor extends AbstractPhaseInterceptor<Message> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ECDSAInInterceptor.class);

    public ECDSAInInterceptor() {
        super(Phase.RECEIVE);
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        LOGGER.debug("handleMessage");
        Map<QName, SecurityPolicyValidator> policyValidatorMap = new HashMap<>();
        ECDSAAlgorithmSuitePolicyValidator algorithmSuitePolicyValidator = new ECDSAAlgorithmSuitePolicyValidator();
        policyValidatorMap.put(SP11Constants.ALGORITHM_SUITE, algorithmSuitePolicyValidator);
        policyValidatorMap.put(SP12Constants.ALGORITHM_SUITE, algorithmSuitePolicyValidator);
        message.put(SecurityConstants.POLICY_VALIDATOR_MAP, policyValidatorMap);
    }
}
