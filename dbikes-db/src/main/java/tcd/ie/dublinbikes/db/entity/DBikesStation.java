package tcd.ie.dublinbikes.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.driver.core.DataType.Name;

/**
 * 
 * @author arun
 *
 */

@Table("dublin_bikes_json")
public class DBikesStation implements Serializable {
	
	@PrimaryKey
	@CassandraType(type=Name.INT)
	private Integer number;
	
	@CassandraType(type=Name.TIMESTAMP)
	private long last_update;
	
	@CassandraType(type=Name.TEXT)
	private String address;
	
	@CassandraType(type=Name.DECIMAL)
	private BigDecimal available_bike_stands;
	
	@CassandraType(type=Name.TEXT)
	private String available_bikes;
	
	@CassandraType(type=Name.BOOLEAN)
	private boolean banking;
	
	@CassandraType(type=Name.INT)
	private Integer bike_stands;
	
	@CassandraType(type=Name.BOOLEAN)
	private boolean bonus;
	
	@CassandraType(type=Name.TEXT)
	private String name;
	
	@CassandraType(type=Name.UDT, userTypeName="position")
	private Position position;
	
	@CassandraType(type=Name.TEXT)
	private String status;
	
}
