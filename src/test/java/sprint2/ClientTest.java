package sprint2;

import static org.junit.jupiter.api.Assertions.*;

import java.rmi.RemoteException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClientTest {

	ClientInterface C;
	Server S;
	Server S1;
	
	@BeforeEach
	void setUp() throws Exception 
	{
	
		S = new Server();
		
		S1 = S.LoadFromDisk();
		C = new Client(S1);
		
		
		
		
		
		
	}



	@Test
	void testGetUsers() throws RemoteException {
		assertEquals(C.getUsers().size(),5);
	}

	@Test
	void testGetDB() throws RemoteException 
	{
		
		assertEquals(C.getDB().GetMembers().size(),5);
	}

	@Test
	void testSetDB() throws RemoteException 
	{
		
		C.setDB(S.getDB());
		assertEquals(C.getDB().GetMembers().size(),0);
	}

	@Test
	void testSetGroupIDs() throws RemoteException 
	{
		C.setGroupIDs(S.getGroupIDs());
		assertEquals(C.getDB().GetMembers().size(),5);
	}

	@Test
	void testSetUsers() throws RemoteException {
		C.setUsers(S.getUsers());
	}



}
