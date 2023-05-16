package sprint2;

import static org.junit.jupiter.api.Assertions.*;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;




class ServerTest 
{


	Server S;
	Client C;
	
	@BeforeEach
	void setUp() throws Exception 
	
	{
		S = new Server();
		

		Registry Registry = LocateRegistry.createRegistry(1099);
		Registry.rebind("SERVER", S);
		

		
		
		
		
	}
	
	


	@Test
	void test() throws RemoteException, MalformedURLException, NotBoundException 
	{
		
		ServerInterface Server  = (ServerInterface) Naming.lookup("rmi://127.0.0.1/SERVER");
		ClientInterface C = new Client(Server);
		
		//Server.addObserver(C);
		ClientInterface C2 = new Client(Server);

		ClientInterface C3 = new Client(Server);
		
		
		
		
		
		//Testing Obeserver pattern
		
		
		
		
		

		//testing observer patter
		
		//S.notifyClients();
	
		
		
		
		
		assertEquals(C.GetActives().size(),0);
		
		assertEquals(C.Register("Glahens","CSC"),1);
	
		assertEquals(C.Register("Christian","BIO"), 2);
		
		assertEquals(C.getUsers().size(),2);
		
		assertEquals(C.GetActives().size(),0);
		//C.Register("Glahens", "CSC");
		
		//C.Register("Christian", "BIO");
		C.Register("Steve", "123");
		//C.Register("Asa", "123");
		
		
		///System.out.println();
		///Testing Login in
		
		assertEquals(C.login("Christian","BI"),"Wrong Password");
		assertEquals(C.login("Glahens", "CSC"),"LoggedIn");
		assertEquals(C.login("GHSHS", "JDJD"), "NotFound");
		//assertEquals()
		
		
		//C.login("Glahens", "CSC");
		
		//assertEquals(S.getActives().size(),1);
		
		System.out.println("Here I am ");
		
		C.login("Christian", "BIO");
		assertEquals(C.login("Christian", "BIO"),"Already In");
		//C.login("Glahens", "CSC");
		
		//C.login("Steve","123");
		
		
		assertEquals(C.GetActives().size(),2);
		
		
		//System.out.println("")
		assertEquals(C.CreateGroup(1, "TestGroup"), true);
		assertEquals(C.CreateGroup(3, "TestBadGroup"), false);
		
	
		System.out.println("Here!!");
		
		
		C.CreateGroup(1, "Other Group");
		
		assertEquals(C.joinGroup(2, 1), 2);
		assertEquals(C.joinGroup(3, 1),-1);
		assertEquals(C.joinGroup(2, 5),-1);
		assertEquals(C.joinGroup(7, 1),-1);
		
		assertEquals(C.AddChannel(1, 1, "Raves"), true);
		assertEquals(C.AddChannel(1, 1000, "Raves"), false);
		assertEquals(C.AddChannel(1000, 1, "Raves"), false);
		System.out.println("Tested Channels");
		
		
		
		C.joinGroup(2, 1);
		C.joinGroup(3, 1);
		
		assertEquals(C.setAdmin(1, 2, 1), true);
		assertEquals(C.setAdmin(1, 3, 2),false);
		assertEquals(C.setAdmin(4, 3, 2),false);
		
		assertEquals(C.EditGroupName(1, 1, "Other"),true);
		assertEquals(C.EditGroupName(4,1,"Noteher"), false);
		assertEquals(C.EditGroupName(1,3, "Nother"), false);
		
		
		assertEquals(C.BanUser(1, 2, 1),true);
		assertEquals(C.BanUser(4, 2, 1),false);
		assertEquals(C.BanUser(1, 1, 4),false);
		
		
		C.CreateGroup(1,"TestGroup3");
		C.joinGroup(1,2);
		//C.joinGroup(1, )
		//C.joinGroup(2,2);
		//assertEquals(C.setAdmin(, 2, 1),true);
		
		assertEquals(C.KickUser(1, 3, 1), true);
		assertEquals(C.KickUser(4, 3, 1), false);
		assertEquals(C.KickUser(2, 2, 4),false);
		assertEquals(C.BanUser(2, 2, 3),false);
		assertEquals(C.BanUser(1, 1, 3),false);
		
		
		assertEquals(C.SendMessage(1, 1, 1, "Hi"),true);
		assertEquals(C.SendMessage(1,2,1,"Hi"),false);
		assertEquals(C.SendMessage(4, 1, 1, "Hi"),false);
		assertEquals(C.SendMessage(1, 2, 4, "hi"),false);
		
		assertEquals(S.getUsers().size(),3);  
		
		
		C2.Register("Preston","Life");
		C3.Register("Ivan", "999");
		
		
		C.SendMessage(1, 1, 1, "Hi");
		
		
		
		
		//testing Banning Users 
		
		//testing XML
		
		
		S.StoreToDisk();
		
		Server DS = S.LoadFromDisk();
		

		//checking actives list
		assertEquals(DS.getActives().size(),2);
		assertEquals(DS.getActives().get(0),1);
		assertEquals(DS.getActives().get(1),2);
		
		//checking user list
		assertEquals(DS.getUsers().size(),5);
		assertEquals(DS.getUsers().get(0).getUserName(), "Glahens");
		assertEquals(DS.getUsers().get(1).getUserName(), "Christian");
		assertEquals(DS.getUsers().get(2).getUserName(), "Steve");
		assertEquals(DS.getUsers().get(3).getUserName(), "Preston");
		assertEquals(DS.getUsers().get(4).getUserName(), "Ivan");
		
		
		//Check Groups list
		assertEquals(DS.getGroupIDs().size(),3);
		assertEquals(DS.getGroupIDs().get(0), 1);
		assertEquals(DS.getGroupIDs().get(1), 2); 
		assertEquals(DS.getGroupIDs().get(2), 3); 
		//Checking Database 
		
		assertEquals(DS.getDB().getGroupIDs().size(),3);
		assertEquals(DS.getDB().getGroupIDs().size(),3);
		assertEquals(DS.getDB().getGroupIDs().get(0), 1);
		assertEquals(DS.getDB().getGroupIDs().get(1), 2); 
		assertEquals(DS.getDB().getGroupIDs().get(2), 3);
		
		assertEquals(DS.getDB().getMembers().size(),5);
		assertEquals(DS.getDB().getMembers().get(0).getUserName(), "Glahens");
		assertEquals(DS.getDB().getMembers().get(1).getPassword(),"BIO");
		assertEquals(DS.getDB().GetMembers().get(2).getUserId(),3);
		
		
		assertEquals(DS.getDB().getChannels().size(),4);
		assertEquals(DS.getDB().getChannels().get(2).getChannelId(),3);
		assertEquals(DS.getDB().getChannels().get(2).getTopic(),"Raves");
		
		assertEquals(DS.getDB().getMessages().size(),2);
		assertEquals(DS.getDB().getMessages().get(0).getMessageID(),1);
		assertEquals(DS.getDB().getMessages().get(1).getContent(),"Hi");

		
	
		//assertEquals(DS.)
		Client TestC = new ClientObserver(Server);
		
		S.notifyClients();
		S.notifyClients();
		S.notifyClients();
		
		
		assertEquals(TestC.NotifyCalled(),4);
		
	}
	


}
