import static org.junit.Assert.*;



import org.junit.Test;

import Dao.DBconn;

public class TestConnection {

	@Test
	public void test() {

		DBconn.init();
		assertNotEquals(DBconn.conn, null);
		fail("Not yet implemented");
	}

}
