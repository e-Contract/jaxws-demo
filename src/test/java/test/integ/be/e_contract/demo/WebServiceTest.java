package test.integ.be.e_contract.demo;

import be.e_contract.jaxws_demo.ExampleService;
import be.e_contract.jaxws_demo.ExampleServicePortType;
import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.Collections;
import java.util.Map;
import javax.xml.ws.BindingProvider;
import org.apache.cxf.ws.security.SecurityConstants;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.bouncycastle.operator.DefaultSignatureAlgorithmIdentifierFinder;
import org.bouncycastle.operator.bc.BcECContentSignerBuilder;
import org.bouncycastle.operator.bc.BcRSAContentSignerBuilder;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebServiceTest.class);

    @Test
    public void invokeWebService() throws Exception {
        LOGGER.debug("test");

        ExampleService service = new ExampleService();
        ExampleServicePortType port = service.getExampleServicePort();

        KeyPair keyPair = generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        X509Certificate certificate = getCertificate(privateKey, publicKey);

        BindingProvider bindingProvider = (BindingProvider) port;
        Map<String, Object> requestContext = bindingProvider
                .getRequestContext();
        requestContext.put(
                BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "https://localhost/jaxws-demo/example");
        requestContext.put(
                SecurityConstants.SIGNATURE_CRYPTO,
                new ClientCrypto(privateKey, Collections
                        .singletonList(certificate)));
        requestContext.put(SecurityConstants.CALLBACK_HANDLER,
                new PasswordCallbackHandler());

        String result = port.echo("hello world");
        Assertions.assertEquals("hello world", result);
    }

    private KeyPair generateKeyPair() throws Exception {
        return generateKeyPair("RSA");
    }

    private KeyPair generateKeyPair(String keyAlgorithm) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(keyAlgorithm);
        SecureRandom random = new SecureRandom();
        switch (keyAlgorithm) {
            case "RSA":
                keyPairGenerator.initialize(new RSAKeyGenParameterSpec(1024, RSAKeyGenParameterSpec.F4), random);
                break;
            case "EC":
                keyPairGenerator.initialize(new ECGenParameterSpec("secp384r1"));
                break;
        }
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    private static X509Certificate getCertificate(PrivateKey privateKey, PublicKey publicKey) throws Exception {
        X500Name subjectName = new X500Name("CN=Test");
        X500Name issuerName = subjectName; // self-signed
        BigInteger serial = new BigInteger(128, new SecureRandom());
        SubjectPublicKeyInfo publicKeyInfo = SubjectPublicKeyInfo.getInstance(publicKey.getEncoded());
        DateTime notBefore = new DateTime();
        DateTime notAfter = notBefore.plusMonths(1);
        X509v3CertificateBuilder x509v3CertificateBuilder = new X509v3CertificateBuilder(issuerName, serial,
                notBefore.toDate(), notAfter.toDate(), subjectName, publicKeyInfo);
        String signatureAlgo;
        switch (privateKey.getAlgorithm()) {
            case "RSA":
                signatureAlgo = "SHA1withRSA";
                break;
            case "EC":
                signatureAlgo = "SHA1withECDSA";
                break;
            default:
                throw new IllegalArgumentException("unsupported key algo: " + privateKey.getAlgorithm());
        }
        AlgorithmIdentifier sigAlgId = new DefaultSignatureAlgorithmIdentifierFinder().find(signatureAlgo);
        AlgorithmIdentifier digAlgId = new DefaultDigestAlgorithmIdentifierFinder().find(sigAlgId);
        AsymmetricKeyParameter asymmetricKeyParameter = PrivateKeyFactory.createKey(privateKey.getEncoded());

        ContentSigner contentSigner;
        if (privateKey.getAlgorithm().contains("RSA")) {
            contentSigner = new BcRSAContentSignerBuilder(sigAlgId, digAlgId).build(asymmetricKeyParameter);
        } else {
            contentSigner = new BcECContentSignerBuilder(sigAlgId, digAlgId).build(asymmetricKeyParameter);
        }
        X509CertificateHolder x509CertificateHolder = x509v3CertificateBuilder.build(contentSigner);

        byte[] encodedCertificate = x509CertificateHolder.getEncoded();

        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        X509Certificate certificate = (X509Certificate) certificateFactory
                .generateCertificate(new ByteArrayInputStream(encodedCertificate));
        return certificate;
    }
}
