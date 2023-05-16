package sprint2;

import java.rmi.RemoteException;

public class ClientObserver extends Client
{

	private static final long serialVersionUID = -3257664286896380567L;
	
	 int called_times = 0;
	 //String name = shane;
	 //String name;

	protected ClientObserver(ServerInterface s) throws RemoteException
	{
		super(s);
		name = "Shane";
		
		// TODO Auto-generated constructor stub
		
	}
	
	//public 
	

	public int NotifyCalled()
	{
		setCalled_times(this.called_times +1);
		
		return called_times;
	}

	public int getCalled_times() {
		return called_times;
	}

	public void setCalled_times(int called_times) {
		this.called_times = called_times;
	}

}
