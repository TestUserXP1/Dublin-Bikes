package tcd.ie.dublinbikes.clientauth.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.catalina.connector.Connector;
import org.apache.commons.io.FileUtils;
import org.apache.coyote.http11.Http11Nio2Protocol;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

@Configuration
@ComponentScan
public class ClientServerAuthConfiguration {
	
	@Autowired
    private Environment env;

    @Bean
    public ServletWebServerFactory servletContainer() {
    		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
    		tomcat.addAdditionalTomcatConnectors(createSSLConnector());
            return tomcat;
    }

    protected Connector createSSLConnector() {
    
        Connector connector = new Connector(Http11Nio2Protocol.class.getCanonicalName());
        Http11Nio2Protocol protocol = (Http11Nio2Protocol) connector.getProtocolHandler();

        File keyStore = null;
        File trustStore = null;

        try {
            keyStore = getKeyStoreFile();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot access keystore: [" + keyStore + "] or " +
                    "truststore: [" + trustStore + "]", e);
        }

        trustStore = keyStore;

        /*connector.setPort(env.getRequiredProperty("server.port", Integer.class));
        connector.setScheme("https");
        protocol.setClientAuth(env.getRequiredProperty("server.ssl.client-auth"));
        protocol.setSSLEnabled(env.getRequiredProperty("server.ssl.enabled", Boolean.class));
 
        protocol.setKeyPass(env.getRequiredProperty("server.ssl.key-password"));
        protocol.setKeystoreFile(keyStore.getAbsolutePath());
        protocol.setKeystorePass(env.getRequiredProperty("server.ssl.key-store-password"));
        protocol.setTruststoreFile(trustStore.getAbsolutePath());
        protocol.setTruststorePass(env.getRequiredProperty("server.ssl.key-store-password"));
        protocol.setCiphers(env.getRequiredProperty("server.ssl.ciphers"));*/
        
        connector.setPort(env.getRequiredProperty("ssl.port", Integer.class));
        connector.setScheme(env.getRequiredProperty("ssl.scheme"));
        connector.setSecure(env.getRequiredProperty("ssl.secure", Boolean.class));

        protocol.setClientAuth(env.getRequiredProperty("ssl.client-auth"));
        protocol.setSSLEnabled(env.getRequiredProperty("ssl.enabled", Boolean.class));

        protocol.setKeyPass(env.getRequiredProperty("ssl.key-password"));
        protocol.setKeystoreFile(keyStore.getAbsolutePath());
        protocol.setKeystorePass(env.getRequiredProperty("ssl.store-password"));
        protocol.setTruststoreFile(trustStore.getAbsolutePath());
        protocol.setTruststorePass(env.getRequiredProperty("ssl.store-password"));
        protocol.setCiphers(env.getRequiredProperty("ssl.ciphers"));
    
        return connector;
    }


    private File getKeyStoreFile() throws IOException {
    	
    		ClassPathResource resource = new ClassPathResource(env.getRequiredProperty("ssl.store"));
        //ClassPathResource resource = new ClassPathResource(env.getRequiredProperty("server.ssl.key-store"));
        //System.out.println("Server ssl keystore path" + env.getRequiredProperty("server.ssl.key-store"));
        // Tomcat won't allow reading File from classpath so read as InputStream into temp File
        File jks = File.createTempFile("server_keystore", ".jks");
        InputStream inputStream = resource.getInputStream();
        try {
            FileUtils.copyInputStreamToFile(inputStream, jks);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return jks;
    }

}
