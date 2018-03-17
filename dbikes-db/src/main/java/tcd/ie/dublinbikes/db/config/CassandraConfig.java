package tcd.ie.dublinbikes.db.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.ClusterBuilderConfigurer;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DropKeyspaceSpecification;

import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.PlainTextAuthProvider;

/**
 * 
 * @author arun
 *
 */
@Configuration
@PropertySource({"classpath:application.properties"})
public class CassandraConfig extends AbstractCassandraConfiguration {

	  @Autowired
	  private Environment environment;
	  
	  @Bean
	  public CassandraClusterFactoryBean cluster() {
			CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
			
			cluster.setClusterBuilderConfigurer(new ClusterBuilderConfigurer() {
			  
			@Override
			public Builder configure(Builder clusterBuilder) {
				clusterBuilder.addContactPoint(CassandraConfig.this.environment.getProperty("spring.data.cassandra.contact-points"));
				clusterBuilder.withPort(Integer.parseInt(CassandraConfig.this.environment.getProperty("spring.data.cassandra.port")));
				clusterBuilder.withAuthProvider(new PlainTextAuthProvider(CassandraConfig.this.environment.getProperty("spring.data.cassandra.username"), 
				CassandraConfig.this.environment.getProperty("spring.data.cassandra.password")));
				return clusterBuilder;
			}});
			return cluster;
	  }
	  
	  public SchemaAction getSchemaAction() {
	    return SchemaAction.CREATE_IF_NOT_EXISTS;
	  }
	  
	  protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
	    CreateKeyspaceSpecification specification = CreateKeyspaceSpecification.createKeyspace(this.environment
	      .getProperty("spring.data.cassandra.keyspace-name"));
	    
	    return Arrays.asList(new CreateKeyspaceSpecification[] { specification });
	  }
	  
	  protected List<DropKeyspaceSpecification> getKeyspaceDrops() {
	    return Arrays.asList(new DropKeyspaceSpecification[] { DropKeyspaceSpecification.dropKeyspace(this.environment.getProperty("spring.data.cassandra.keyspace-name")) });
	  }
	  
	  protected String getKeyspaceName() {
	    return this.environment.getProperty("spring.data.cassandra.keyspace-name");
	  }
	  
	  public String[] getEntityBasePackages() {
	    return new String[] { "tcd.ie.dublinbikes.db.entity" };
	  }
}
