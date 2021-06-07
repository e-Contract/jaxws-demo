package be.e_contract.demo;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Pattern;
import javax.security.auth.callback.CallbackHandler;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.CryptoType;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerCrypto implements Crypto {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerCrypto.class);

    private final CertificateFactory certificateFactory;

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
    public byte[] getBytesFromCertificates(X509Certificate[] certs) throws WSSecurityException {
        LOGGER.debug("getBytesFromCertificates");
        return null;
    }

    @Override
    public CertificateFactory getCertificateFactory() throws WSSecurityException {
        LOGGER.debug("getCertificateFactory");
        return null;
    }

    @Override
    public X509Certificate[] getCertificatesFromBytes(byte[] data) throws WSSecurityException {
        LOGGER.debug("getCertificatesFromBytes");
        Collection<? extends Certificate> certificates;
        try {
            certificates = this.certificateFactory.generateCertificates(new ByteArrayInputStream(data));
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        }
        X509Certificate[] result = new X509Certificate[certificates.size()];
        int idx = 0;
        Iterator<? extends Certificate> iter = certificates.iterator();
        while (iter.hasNext()) {
            result[idx] = (X509Certificate) iter.next();
            idx++;
        }
        return result;
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
    public PrivateKey getPrivateKey(X509Certificate certificate, CallbackHandler callbackHandler)
            throws WSSecurityException {
        LOGGER.debug("getPrivateKey");
        return null;
    }

    @Override
    public PrivateKey getPrivateKey(String identifier, String password) throws WSSecurityException {
        LOGGER.debug("getPrivateKey");
        return null;
    }

    @Override
    public byte[] getSKIBytesFromCert(X509Certificate cert) throws WSSecurityException {
        LOGGER.debug("getSKIBytesFromCert");
        return null;
    }

    @Override
    public X509Certificate[] getX509Certificates(CryptoType cryptoType) throws WSSecurityException {
        LOGGER.debug("getX509Certificates");
        return new X509Certificate[]{this.certificate};
    }

    @Override
    public String getX509Identifier(X509Certificate cert) throws WSSecurityException {
        LOGGER.debug("getX509Identifier");
        return null;
    }

    private X509Certificate certificate;

    @Override
    public X509Certificate loadCertificate(InputStream in) throws WSSecurityException {
        LOGGER.debug("loadCertificate");
        try {
            return (X509Certificate) this.certificateFactory.generateCertificate(in);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        }
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
    public String getTrustProvider() {
        return null;
    }

    @Override
    public void setTrustProvider(String provider) {

    }

    @Override
    public void setCertificateFactory(CertificateFactory certFactory) {

    }

    @Override
    public PrivateKey getPrivateKey(PublicKey publicKey, CallbackHandler callbackHandler) throws WSSecurityException {
        return null;
    }

    @Override
    public void verifyTrust(X509Certificate[] certs, boolean enableRevocation,
            Collection<Pattern> subjectCertConstraints, Collection<Pattern> issuerCertConstraints)
            throws WSSecurityException {

    }

    @Override
    public void verifyTrust(PublicKey publicKey) throws WSSecurityException {

    }
}
