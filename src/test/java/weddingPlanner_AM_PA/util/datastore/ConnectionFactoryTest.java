package weddingPlanner_AM_PA.util.datastore;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import weddingPlanner_AM_PA.util.datasource.ConnectionFactory;

class ConnectionFactoryTest {

	@BeforeEach
	void setUp() throws Exception {
	}


	@Test
	public void test_getConnection_returnValidConnection_givenProvidenCredentials() {
		try (Connection conn = ConnectionFactory.getInstance().getConnection();){
			System.out.println(conn);
			Assert.assertNotNull(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
