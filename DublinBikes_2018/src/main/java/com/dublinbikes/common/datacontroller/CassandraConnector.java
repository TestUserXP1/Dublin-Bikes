package com.dublinbikes.common.datacontroller;

import com.datastax.driver.core.*;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;

public class CassandraConnector {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final Cluster.Builder clusterBuilder = Cluster.builder()
			    .addContactPoints(
			        "54.191.120.32"
			    )
			    .withPort(9042)
			    .withAuthProvider(new PlainTextAuthProvider("cassandra", "cassandra"));

			try (final Cluster cluster = clusterBuilder.build()) {
			    final Metadata metadata = cluster.getMetadata();
			    System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());
			    
			    Session session = cluster.connect("demo");
				String cqlStatement = "select * from demo.test;";
				for (Row row : session.execute(cqlStatement)) {
				  System.out.println(row.toString());
				}
			    
			    for (final Host host: metadata.getAllHosts()) {
			        System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n", host.getDatacenter(), host.getAddress(), host.getRack());
			    }
			}
	}

}
