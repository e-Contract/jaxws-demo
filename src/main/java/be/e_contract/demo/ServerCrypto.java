package be.e_contract.demo;

import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Properties;
import javax.security.auth.callback.CallbackHandler;
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.components.crypto.Crypto;
import org.apache.ws.security.components.crypto.CryptoType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerCrypto implements Crypto {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ServerCrypto.class);

    private final CertificateFactory certificateFactory;

    private X509Certificate clientCertificate;

    public ServerCrypto() {
        LOGGER.debug("constructor");
        try {
            this.certificateFactory = CertificateFactory.getInstance("X.509");
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        }
    }

    public ServerCrypto(Properties map, ClassLoader loader) {
        this();
    }

    @Override
    public byte[] getBytesFromCertificates(X509Certificate[] certs)
            throws WSSecurityException {
        LOGGER.debug("getBytesFromCertificates");
        return null;
    }

    @Override
    public CertificateFactory getCertificateFactory()
            throws WSSecurityException {
        LOGGER.debug("getCertificateFactory");
        return null;
    }

    @Override
    public X509Certificate[] getCertificatesFromBytes(byte[] data)
            throws WSSecurityException {
        LOGGER.debug("getCertificatesFromBytes");
        return null;
    }

    @Override
    public String getCryptoProvider() {
        LOGGER.debug("getCryptoProvider");
        return null;
    }

    @Override
    public String getDefaultX509Identifier() throws WSSecurityException {
        LOGGER.debug("getDefaultX509Identifier");
        return null;
    }

    @Override
    public PrivateKey getPrivateKey(X509Certificate certificate,
            CallbackHandler callbackHandler) throws WSSecurityException {
        LOGGER.debug("getPrivateKey");
        return null;
    }

    @Override
    public PrivateKey getPrivateKey(String identifier, String password)
            throws WSSecurityException {
        LOGGER.debug("getPrivateKey");
        return null;
    }

    @Override
    public byte[] getSKIBytesFromCert(X509Certificate cert)
            throws WSSecurityException {
        LOGGER.debug("getSKIBytesFromCert");
        return null;
    }

    @Override
    public X509Certificate[] getX509Certificates(CryptoType cryptoType)
            throws WSSecurityException {
        LOGGER.debug("getX509Certificates: {}", cryptoType.getType());
        LOGGER.debug("issuer: {}", cryptoType.getIssuer());
        LOGGER.debug("serial: {}", cryptoType.getSerial());
        return new X509Certificate[]{this.clientCertificate};
    }

    @Override
    public String getX509Identifier(X509Certificate cert)
            throws WSSecurityException {
        LOGGER.debug("getX509Identifier");
        return null;
    }

    @Override
    public X509Certificate loadCertificate(InputStream in)
            throws WSSecurityException {
        LOGGER.debug("loadCertificate");
        try {
            return (X509Certificate) this.certificateFactory
                    .generateCertificate(in);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setCertificateFactory(String provider,
            CertificateFactory certFactory) {
        LOGGER.debug("setCertificateFactory");
    }

    @Override
    public void setCryptoProvider(String provider) {
        LOGGER.debug("setCryptoProvider");
    }

    @Override
    public void setDefaultX509Identifier(String identifier) {
        LOGGER.debug("setDefaultX509Identifier");
    }

    @Override
    public boolean verifyTrust(X509Certificate[] certs)
            throws WSSecurityException {
        LOGGER.debug("verifyTrust(certs)");
        return false;
    }

    @Override
    public boolean verifyTrust(PublicKey publicKey) throws WSSecurityException {
        LOGGER.debug("verifyTrust(publicKey)");
        return false;
    }

    @Override
    public boolean verifyTrust(X509Certificate[] certs, boolean enableRevocation)
            throws WSSecurityException {
        LOGGER.debug("verifyTrust(certs, enableRevocation)");
        X509Certificate certificate = certs[0];
        LOGGER.debug("client certificate subject: {}", certificate.getSubjectX500Principal());
        return true;
    }
}
