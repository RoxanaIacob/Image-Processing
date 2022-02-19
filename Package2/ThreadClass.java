package Package2;
public abstract class ThreadClass extends Thread {
	private boolean consumer;
	
	public abstract void ThreadStart() throws InterruptedException;
	
	// constructor
	public ThreadClass(boolean c){
		super();
		consumer = c;
	}
	
	// afisez cand Thread-ul a inceput sa porneasca si cand s-a terminat executia lui
	// cum am facut la laborator
	public void run(){
		System.out.println("[ThreadClass] Thread-ul a inceput.");
		try{
			this.ThreadStart();
		} catch(InterruptedException e){
			e.printStackTrace();
		}
		System.out.println("[ThreadClass] Thread-ul s-a terminat.");
	}
}
