package tcd.ie.dbikes.clientauth;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;

import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContexts;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import tcd.ie.dbikes.clientauth.controller.ClientObject;

/**
 * 
 * @author arun
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DbikesClientAuthApplication.class)
@WebIntegrationTest(randomPort = true)
@TestPropertySource(locations = "classpath:integration-test.properties")
public class DbikesClientAuthApplicationTests {

    public static final String CLIENT_TRUSTSTORE = "ssl/client_truststore.jks";
    public static final String CLIENT_KEYSTORE = "ssl/client_keystore.jks";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Value("${local.server.port}")
    private int port = 0;

    @Value("${ssl.port}")
    private int sslPort = 0;

    @Value("${ssl.store-password}")
    private String storePassword;

    @Test
    public void testRestOverPlainHttp() throws Exception {
        ClientObject expected = new ClientObject("Success, 646232");

        RestTemplate template = new TestRestTemplate();

        ResponseEntity<ClientObject> responseEntity =
                template.getForEntity("http://localhost:" + port + "/login?id={id}",
                        ClientObject.class, "646232");

        assertThat(responseEntity.getBody().getContent(), equalTo(expected.getContent()));
    }

    @Test
    public void testRestWithMissingClientCert()
            throws Exception {
    	
        SSLContext sslContext = SSLContexts.custom()
                // no key store
                .loadTrustMaterial(
                        getStore(CLIENT_TRUSTSTORE, storePassword.toCharArray()),
                        new TrustSelfSignedStrategy())
                .useProtocol("TLS").build();

        RestTemplate template = getRestTemplateForHTTPS(sslContext);

        thrown.expectCause(IsInstanceOf.instanceOf(SSLHandshakeException.class));
        thrown.expectMessage("Received fatal alert: bad_certificate");

        template.getForEntity("http://localhost:" + port + "/login?id={id}",
                ClientObject.class, "646232");
    }

    @Test
    public void testRestWithUntrustedServerCert()
            throws Exception {
        SSLContext sslContext = SSLContexts.custom()
                .loadKeyMaterial(
                        getStore(CLIENT_KEYSTORE, storePassword.toCharArray()),
                        storePassword.toCharArray())
                        // no trust store
                .useProtocol("TLS").build();

        RestTemplate template = getRestTemplateForHTTPS(sslContext);

        thrown.expectCause(IsInstanceOf.instanceOf(SSLHandshakeException.class));
        thrown.expectMessage("unable to find valid certification path to requested target");

        template.getForEntity("http://localhost:" + port + "/login?id={id}",
                ClientObject.class, "646232");
    }

    @Test
    public void testRestWithTwoWaySSL() throws Exception {
    		ClientObject expected = new ClientObject("Success, 646232");

        SSLContext sslContext = SSLContexts.custom()
                .loadKeyMaterial(
                        getStore(CLIENT_KEYSTORE, storePassword.toCharArray()),
                        storePassword.toCharArray())
                .loadTrustMaterial(
                        getStore(CLIENT_TRUSTSTORE, storePassword.toCharArray()),
                        new TrustSelfSignedStrategy())
                .useProtocol("TLS").build();

        RestTemplate template = getRestTemplateForHTTPS(sslContext);

        ResponseEntity<ClientObject> responseEntity =
        		template.getForEntity("http://localhost:" + port + "/login?id={id}",
                        ClientObject.class, "646232");

        assertThat(responseEntity.getBody().getContent(), equalTo(expected.getContent()));
    }

    protected KeyStore getStore(final String storeFileName, final char[] password) throws
            KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        final KeyStore store = KeyStore.getInstance("jks");
        URL url = getClass().getClassLoader().getResource(storeFileName);
        InputStream inputStream = url.openStream();
        try {
            store.load(inputStream, password);
        } finally {
            inputStream.close();
        }

        return store;
    }

    private RestTemplate getRestTemplateForHTTPS(SSLContext sslContext) {
        SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(sslContext,
                new DefaultHostnameVerifier());

        CloseableHttpClient closeableHttpClient =
                HttpClientBuilder.create().setSSLSocketFactory(connectionFactory).build();

        RestTemplate template = new TestRestTemplate();
        HttpComponentsClientHttpRequestFactory httpRequestFactory =
                (HttpComponentsClientHttpRequestFactory) template.getRequestFactory();
        httpRequestFactory.setHttpClient(closeableHttpClient);
        return template;
    }

}
