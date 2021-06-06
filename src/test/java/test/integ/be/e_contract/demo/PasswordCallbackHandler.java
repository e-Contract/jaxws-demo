package test.integ.be.e_contract.demo;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordCallbackHandler implements CallbackHandler {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(PasswordCallbackHandler.class);

    @Override
    public void handle(Callback[] callbacks) throws IOException,
            UnsupportedCallbackException {
        for (Callback callback : callbacks) {
            LOGGER.debug("callback type: {}", callback.getClass().getName());
            if (callback instanceof WSPasswordCallback) {
                WSPasswordCallback wsPasswordCallback = (WSPasswordCallback) callback;
                String identifier = wsPasswordCallback.getIdentifier();
                if ("username".equals(identifier)) {
                    wsPasswordCallback.setPassword("password");
                }
            }
        }
    }
}
