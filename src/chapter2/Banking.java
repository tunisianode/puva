package chapter2;

class Account //Konto
{
	private float balance; //Kontostand

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}
}

class Bank {
	private Account[] account;

	public Bank() {
		account = new Account[100];
		for (int i = 0; i < account.length; i++) {
			account[i] = new Account();
		}
	}

	public synchronized void transferMoney(int accountNumber, float amount) {
		float oldBalance = account[accountNumber].getBalance();
		float newBalance = oldBalance + amount;
		account[accountNumber].setBalance(newBalance);
	}

	public void transferMoneyAlternative(int accountNumber, float amount) {
		synchronized (account[accountNumber]) {
			float oldBalance = account[accountNumber].getBalance();
			float newBalance = oldBalance + amount;
			account[accountNumber].setBalance(newBalance);
		}
	}
}

class Clerk extends Thread {
	private Bank bank;

	public Clerk(String name, Bank bank) {
		super(name);
		this.bank = bank;
		start();
	}

	public void run() {
		for (int i = 0; i < 10000; i++) {
            /* Kontonummer einlesen;
               simuliert durch Wahl einer Zufallszahl
               zwischen 0 und 99
            */
			int accountNumber = (int) (Math.random() * 100);

            /* �berweisungsbetrag einlesen;
               simuliert durch Wahl einer Zufallszahl
               zwischen -500 und +499
            */
			float amount = (int) (Math.random() * 1000) - 500;

			bank.transferMoney(accountNumber, amount);
			//oder: bank.transferMoneyAlternative(accountNumber, amount);
		}
	}
}

public class Banking {
	public static void main(String[] args) {
		Bank myBank = new Bank();
		new Clerk("Andrea M�ller", myBank);
		new Clerk("Petra Schmitt", myBank);
	}
}
