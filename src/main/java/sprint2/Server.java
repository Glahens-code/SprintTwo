package sprint2;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;


import sprint1.Database;
import sprint1.Group;

import sprint1.User;
import sprint1.Channel;


public class Server extends UnicastRemoteObject implements ServerInterface, Serializable 
{
	private static final long serialVersionUID = -1505390113691601610L;
	private Database DB;
	private ArrayList<Integer> Actives;
	private ArrayList <Integer> GroupIDs;
	private ArrayList <User> Users;
	private ArrayList<ClientInterface> Clients = new ArrayList<ClientInterface>();
	

	
	public Server() throws RemoteException
	{
		
		this.DB = new Database();
		this.Actives = new ArrayList<Integer>();
		this.GroupIDs = DB.getGroupIDs();
		this.Users = DB.getMembers();
	}
	
	
	@Override
	public ArrayList<User> getUsers() throws RemoteException{
		return Users;
	}
	
	@Override
	public void setUsers(ArrayList<User> users) {
		Users = users; 
	}

	@Override
	public String Authorize(String Username, String Password) throws RemoteException 
	{
		ArrayList<User> Users = DB.GetMembers();
		
		for(int i = 0; i < Users.size(); i++)
		{	
			User U = Users.get(i);
			System.out.println(U.getUserName());
			System.out.println(Username);
			if(U.UserName.equals(Username))
			{
				System.out.println("In");
				if(U.getPassword().equals(Password))
				{
					if(Actives.contains(U.getUserId()))
					{
						return "Already In";
					}
							
					Actives.add(U.getUserId());
					
					return "LoggedIn";
				}
				else
				{
					
					return "Wrong Password"; 
				}
			}
			
			
		}
		
		this.StoreToDisk();
		

		return "NotFound";
	}

	@Override
	public boolean SendMessage(int Suid, int channelid, int GroupId,String Content) throws RemoteException 
	{ 
		if(Actives.contains(Suid))
		{
			System.out.println("In function");
			//check if group is there
			if (DB.getGroupIDs().contains(GroupId))
			{
				Group G = DB.getGroup(GroupId);
				

				
				if (G.getChannels().contains(channelid))
				{
					
					System.out.println("In before creating message");

					Channel C = DB.getChannel(channelid);
					
					C.SendMessage(GroupId, Suid, DB, Content);
					
					
					return true;
					
					
					
				}
			}

		}
		this.StoreToDisk();
		return false;
	}
	
	@Override
	public boolean EditGroupName(int GroupID, int Uid, String Name) throws RemoteException
	{
		if (!DB.getGroupIDs().contains(GroupID))
		{
			return false;
			
		}
		
		if (Actives.contains(Uid))
		{
			Group G = DB.getGroup(GroupID);
			
			G.editGroupName(Uid, Name);
			
			return true;
		}
		
		this.StoreToDisk();

		return false;
	}
	

	@Override
	public boolean setAdmin(int GroupId, int RecId, int SetId) throws RemoteException 
	{
		if (!DB.getGroupIDs().contains(GroupId))
		{
			return false;
		}
		if (Actives.contains(SetId))
		{
			Group G = DB.getGroup(GroupId);
			
			G.setAdmin(SetId, RecId);
			return true;
		}
		
		this.StoreToDisk();
		return false; 
		
	}

	@Override
	public boolean BanUser(int GroupId, int Bad, int Boss) throws RemoteException 
	{
		if (!DB.getGroupIDs().contains(GroupId))
		{
			return false;
		}
		if (Actives.contains(Boss))
		{
			Group G = DB.getGroup(GroupId);

					
			G.BanUser(Boss,Bad);
			
			return true;
		}
		this.StoreToDisk();
		return false;
	}

	@Override
	public boolean KickUser(int GroupId, int RecId, int SetID) throws RemoteException 
	{
		if (!DB.getGroupIDs().contains(GroupId))
		{
			return false;
		}
		if (Actives.contains(SetID))
		{
			Group G = DB.getGroup(GroupId);
			
			G.BanUser(SetID,RecId);
			
			this.StoreToDisk();
			
			return true;

		}
		
		this.StoreToDisk();
		return false;
	}

	@Override
	public boolean CreateGroup(int Uid, String GroupName) throws RemoteException 
	{
		if(Actives.contains(Uid))
		{
		
			Group G = new Group();
			
			
			G.setGroupName(GroupName);
			G.getAdmins().add(Uid);
			G.getUserIds().add(Uid);
			DB.InsertGroup(G);
			return true;
		}
		this.StoreToDisk();
		return false;
	}

	@Override
	public boolean AddChannel(int Uid, int GroupID, String Content) throws RemoteException 
	{
		if (Actives.contains(Uid))
		{
			if(GroupIDs.contains(GroupID))
			{	
				Group G = DB.getGroup(GroupID);
				Channel C = new Channel(Content);
				DB.InsertChannel(C);
				
				G.getChannels().add(C.getChannelId());
				
				return true;
				
				
				 
			}
		}
		this.StoreToDisk();
		return false;
	}
	@Override
	public int JoinGroup(int Uid, int GroupID) throws RemoteException 
	{
		
		if((Actives.contains(Uid) && GroupIDs.contains(GroupID)))
		{
			Group G = DB.getGroup(GroupID);
	
			
			G.InsertUser(Uid);
			
			return G.InsertUser(Uid);
		}
		
		
		this.StoreToDisk();
		return -1;
		 
		
	}
	





	@Override
	public int addUser(String Username, String Password) throws RemoteException 
	{
		User U = new User(Username,Password);
		
		
		DB.InsertUser(U);
		
		this.StoreToDisk();
		return U.getUserId();
		

	}
	

	



	@Override
	public Database getDB() {
		return DB;
	}
	@Override
	public void setDB(Database dB) throws RemoteException 
	{
		
		DB = dB;
		this.StoreToDisk();
	}
	@Override
	public ArrayList<Integer> getActives() {
		return Actives;
	}
	
	@Override
	public void setActives(ArrayList<Integer> actives) throws RemoteException
	{
		Actives = actives;
		this.StoreToDisk();
	}
	@Override
	public ArrayList<Integer> getGroupIDs() throws RemoteException 
	{
		
		return GroupIDs;
	}
	
	@Override
	public void setGroupIDs(ArrayList<Integer> groupIDs) throws RemoteException {
		GroupIDs = groupIDs;
		this.StoreToDisk();
	}


	public ArrayList<ClientInterface> getClients() {
		return Clients;
	}


	public void setClients(ArrayList<ClientInterface> clients) {
		Clients = clients;
	}


	@Override
	public void StoreToDisk() throws RemoteException 
	{
		XMLEncoder encoder=null;
		try
		{
			encoder=new XMLEncoder(new BufferedOutputStream(new FileOutputStream("Database.xml")));
		}
		catch(FileNotFoundException fileNotFound)
		{
			System.out.println("ERROR: While Creating or Opening the File Database.xml");
		}
		encoder.writeObject(this);
		encoder.close();
	}


	@Override
	public Server LoadFromDisk() throws RemoteException 
	{
		XMLDecoder decoder=null;
		try 
		{
			decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream("Database.xml")));
			//Server DS =(Server)decoder.readObject();
	
	
		} catch (FileNotFoundException e) 
		
		{
			System.out.println("ERROR: Database.xml File  not found");
		}
		
		
		Server DS =(Server)decoder.readObject();
		
		return DS;
	}


	@Override
	public void addObserver(ClientInterface C) throws RemoteException 
	{
		Clients.add(C);
	}


	@Override
	public void RemoveObserver(ClientInterface C) throws RemoteException 
	{
		Clients.remove(C);
	}
	
	public void notifyClients() throws RemoteException
	{
		for(ClientInterface C : Clients)
		{
			C.NotifyCalled();
		}
	}
	
	


	



	
	
}
