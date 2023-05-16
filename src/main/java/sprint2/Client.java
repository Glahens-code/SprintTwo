package sprint2;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import sprint1.Database;
import sprint1.User;

public class Client extends UnicastRemoteObject implements ClientInterface, Serializable
{


	private static final long serialVersionUID = -7102422310782737547L;
	
	 ServerInterface S;
	 String name;
	 



	protected Client(ServerInterface s) throws RemoteException  
	{
		this.S = s;
		
		s.addObserver(this);
		name = "glahens";
		// TODO Auto-generated constructor stub
	}
	
	 





	@Override
	public String login(String UserName, String Password) throws RemoteException
	{
		
		String Ret = S.Authorize(UserName, Password);
		
		
		return Ret;
		
		
	}
	





	


	@Override
	public int Register(String Username, String Password) throws RemoteException 
	{
		// TODO Auto-generated method stub
		int Ret = S.addUser(Username, Password);
		return Ret;
	}



	@Override
	public int joinGroup(int uid, int GID) throws RemoteException 
	{
		return S.JoinGroup(uid, GID);
	}



	@Override
	public boolean CreateGroup(int uid, String GroupName) throws RemoteException
	{
		// TODO Auto-generated method stub
		
		boolean Ret = S.CreateGroup(uid, GroupName);
		return Ret;
	}



	@Override
	public boolean setAdmin(int gid,int ad, int u) throws RemoteException 
	{
		return S.setAdmin(gid, u, ad);
	}



	@Override
	public boolean EditGroupName(int Groupid, int Uid, String Name) throws RemoteException 
	{
		// TODO Auto-generated method stub
		return S.EditGroupName(Groupid, Uid,Name);
	}



	@Override
	public boolean BanUser(int GroupId, int RecId, int SetID) throws RemoteException {
		// TODO Auto-generated method stub
		return S.BanUser(GroupId, RecId, SetID);
	}



	@Override
	public boolean KickUser(int GroupId, int RecId, int SetID) throws RemoteException {
		// TODO Auto-generated method stub
		return S.KickUser(GroupId, RecId, SetID);
	}



	@Override
	public boolean AddChannel(int Uid, int GroupID, String Content) throws RemoteException 
	{
		// TODO Auto-generated method stub
		
		return S.AddChannel(Uid, GroupID, Content);
		
	}



	@Override
	public int JoinGroup(int Uid, int GroupID) throws RemoteException 
	{
		// TODO Auto-generated method stub
		return S.JoinGroup(Uid, GroupID);
	}










	@Override
	public boolean SendMessage(int Suid, int ChannelId, int GroupId, String Content) throws RemoteException {
		// TODO Auto-generated method stub
		return S.SendMessage(Suid, ChannelId, GroupId, Content);
	}



	@Override
	public ArrayList<Integer> GetActives()  throws RemoteException
	{
		// TODO Auto-generated method stub
		return S.getActives();
	}



	@Override
	public ArrayList<User> getUsers() throws RemoteException {
		// TODO Auto-generated method stub
		return S.getUsers();
	}



	@Override
	public Database getDB() throws RemoteException 
	{
		// TODO Auto-generated method stub
		return S.getDB();
	}



	@Override
	public void setDB(Database dB) throws RemoteException 
	{
		S.setDB(dB);
	}



	@Override
	public void setGroupIDs(ArrayList<Integer> groupIDs) throws RemoteException 
	{
		S.setGroupIDs(groupIDs);
		
		
	}



	@Override
	public void setUsers(ArrayList<User> users) throws RemoteException {
		S.setUsers(users);
		
	}


	
	public int NotifyCalled()
	{

		return 0;
	}








}
