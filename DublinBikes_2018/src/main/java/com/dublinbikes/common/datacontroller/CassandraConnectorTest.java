package com.dublinbikes.common.datacontroller;

import static org.junit.Assert.*;
import com.datastax.driver.core.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CassandraConnectorTest {
	private Cluster cluster;
	@Before
	public void setUp() throws Exception {
		try{
		cluster = Cluster.builder()
			    .addContactPoints(
			        "54.191.120.32"
			    )
			    .withPort(9042)
			    .withAuthProvider(new PlainTextAuthProvider("cassandra", "cassandra")).build();
		}
		catch(Exception e){
			System.out.println("Cassandra connection establishment error");
		}
	}

	@After
	public void tearDown() throws Exception {
		cluster.close();
	}

	@Test
	public void testClusterName() {
		final Metadata metadata = cluster.getMetadata();
		assertEquals("Test Cluster", metadata.getClusterName());
	}

}
