package datamodeller;


import com.datastax.driver.core.*;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;

public class Connector {

	public static void main(String[] args) {
		final Cluster.Builder clusterBuilder = Cluster.builder()
			    .addContactPoints(
			        "34.217.151.221", "52.89.113.86", "34.208.172.70" // AWS_VPC_US_WEST_2 (Amazon Web Services (VPC))
			    )
			    .withLoadBalancingPolicy(DCAwareRoundRobinPolicy.builder().withLocalDc("AWS_VPC_US_WEST_2").build()) // your local data centre
			    .withPort(9042)
			    .withAuthProvider(new PlainTextAuthProvider("iccassandra", "bdf8f604c11dc6aae6dd245804157f10"));

			try (final Cluster cluster = clusterBuilder.build()) {
			    final Metadata metadata = cluster.getMetadata();
			    System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());

			    for (final Host host: metadata.getAllHosts()) {
			        System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n", host.getDatacenter(), host.getAddress(), host.getRack());
			    }
			}
	}
}
