// Fig. 23.16: SynchronizedBuffer.java
// Synchronizing access to shared mutable data using Object
// methods wait and notifyAll.

public class SynchronizedBuffer implements Buffer {

	private int buffer = -1; // shared by producer and consumer threads
	private boolean occupied = false;

	// place value into buffer
	public synchronized void blockingPut(int value)throws InterruptedException {
		// while there are no empty locations, place thread in waiting state
		while (occupied) {
			// output thread information and buffer information, then wait
			System.out.println("Productor intenta escribir."); // for demo only
			displayState("Buffer lleno. Productor en espera."); // for demo only
			wait();
		}

		buffer = value; // set new buffer value

		//indicate producer cannot store another value
		//until consumer retrieves current buffer value
		occupied = true;

		displayState("Productor escribe    " + buffer+"   "); // for demo only

		notifyAll(); // tell waiting thread(s) to enter runnable state
	} // end method blockingPut; releases lock on SynchronizedBuffer

	// return value from buffer
	public synchronized int blockingGet() throws InterruptedException {
		// while no data to read, place thread in waiting state
		while (!occupied) {
			// output thread information and buffer information, then wait
			System.out.println("Consumidor intenta leer."); // for demo only
			displayState("Buffer vacio. Consumidor en espera."); // for demo only
			wait();
		}

		//indicate that producer can store another value
		//because consumer just retrieved buffer value
		occupied = false;

		displayState("Consumidor lee       " + buffer); // for demo only

		notifyAll(); // tell waiting thread(s) to enter runnable state

		return buffer;
	} // end method blockingGet; releases lock on SynchronizedBuffer

	// display current operation and buffer state; for demo only
	private synchronized void displayState(String operation) {
		System.out.printf("%-40s	%d		 %b%n%n", operation, buffer, occupied);
	}
} // end class SynchronizedBuffer