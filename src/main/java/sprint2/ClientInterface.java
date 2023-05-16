package sprint2;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import sprint1.Database;
import sprint1.User;

public interface ClientInterface extends Remote
{
	

	public String login(String UserName, String Password) throws RemoteException;
	public int Register(String Username, String Password) throws RemoteException;
	public int joinGroup(int uid, int GID) throws RemoteException;
	public boolean EditGroupName(int Groupid, int Uid,String Name) throws RemoteException;
	public boolean setAdmin(int GroupId, int RecId, int SetId)throws RemoteException ;
	public boolean BanUser(int GroupId, int RecId, int SetID) throws RemoteException;
	public boolean KickUser(int GroupId, int RecId, int SetID)throws RemoteException ;
	public boolean CreateGroup(int Uid, String GroupName)throws RemoteException ;
	public boolean AddChannel(int Uid, int GroupID, String Content)throws RemoteException;
	public int JoinGroup(int Uid, int GroupID)throws RemoteException;
	public boolean SendMessage(int Suid, int ChannelId, int GroupId, String Content) throws RemoteException;
	public ArrayList<Integer> GetActives()throws RemoteException;
	public ArrayList<User> getUsers()throws RemoteException;
	public Database getDB() throws RemoteException;
	public void setDB(Database dB) throws RemoteException;
	public void setGroupIDs(ArrayList<Integer> groupIDs) throws RemoteException;
	public void setUsers(ArrayList<User> users) throws RemoteException;
	public int NotifyCalled() throws RemoteException;

	
	
	
	
	
	
	
	
}
