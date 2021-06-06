package test.integ.be.e_contract.demo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.security.auth.callback.CallbackHandler;

import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.components.crypto.Crypto;
import org.apache.ws.security.components.crypto.CryptoType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientCrypto implements Crypto {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientCrypto.class);

    private final PrivateKey privateKey;

    private final List<X509Certificate> certificates;

    public ClientCrypto(PrivateKey privateKey,
            List<X509Certificate> certificates) throws CertificateExpiredException, CertificateNotYetValidException {
        this.privateKey = privateKey;
        this.certificates = certificates;
        LOGGER.debug("constructor");
        this.certificates.get(0).checkValidity();
    }

    @Override
    public byte[] getBytesFromCertificates(X509Certificate[] certs)
            throws WSSecurityException {
        LOGGER.debug("getBytesFromCertificates");
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        for (X509Certificate cert : certs) {
            try {
                output.write(cert.getEncoded());
            } catch (CertificateEncodingException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        return output.toByteArray();
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
        return "client";
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
        return this.privateKey;
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
        LOGGER.debug("getX509Certificates");
        return this.certificates.toArray(new X509Certificate[this.certificates
                .size()]);
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
        return null;
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
        LOGGER.debug("verifyTrust");
        return false;
    }

    @Override
    public boolean verifyTrust(PublicKey publicKey) throws WSSecurityException {
        LOGGER.debug("verifyTrust");
        return false;
    }

    @Override
    public boolean verifyTrust(X509Certificate[] certs, boolean enableRevocation)
            throws WSSecurityException {
        LOGGER.debug("verifyTrust");
        return false;
    }
}
