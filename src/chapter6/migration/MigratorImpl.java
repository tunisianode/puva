package chapter6.migration;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@SuppressWarnings("serial")
public class MigratorImpl extends UnicastRemoteObject
		implements Migrator {
	public MigratorImpl() throws RemoteException {
	}

	public Counter migrate(Counter counter) throws RemoteException {
        /*
        int tries = 0;
        int portNumber = 2500;
        while(true)
        {
            tries++;
            portNumber++;
            try
            {
                UnicastRemoteObject.exportObject(counter, portNumber);
                //no exception => stop loop
                break;
            }
            catch(RemoteException e)
            {
                System.out.println("Ausnahme in CounterView-Konstruktor:\n" + e);
                if(tries == 30)
                {
                    throw e;
                }
            }
        }
        */
		try {
			UnicastRemoteObject.exportObject(counter, 0);
		} catch (RemoteException e) {
			System.out.println("Ausnahme beim Exportieren: " + e);
		}
		return counter;
	}
}
