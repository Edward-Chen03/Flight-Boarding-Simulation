public class BoardingQueue {
    private Passenger[] passengerQueue;
    public final int maxCapacity = 10;
    private int front, rear, size;
 
    public BoardingQueue(){
    	front = -1;
    	rear = -1;
    	passengerQueue = new Passenger[10];
    	size = 0;
    }
    public int getSize() {
    	return size;
    }
    
    public boolean isEmpty() {
    	return (front == -1);
    }
 
    public void enqueuePassenger(Passenger newPass) throws NoRoomException{
    	if (rear == maxCapacity) {
    		throw new NoRoomException();
    	}
    	if(front == -1) {
    		front = 0;
    		rear = 0;
    		passengerQueue[rear] = newPass;
    		size++;
    	}
    	else {
    		rear = (rear + 1) % maxCapacity;
    		passengerQueue[rear] = newPass;
    		size++;
    	}
    	}
    
 
    public Passenger dequeuePassenger() throws NoPassengerException{
    	
    	if (front == -1) {
    		throw new NoPassengerException();
    	}
    	
    	Passenger temp = passengerQueue[front];
    	if(front == rear) {
    		front = -1;
    		rear = -1;
    	}
    	else {
    		front = (front +1) % maxCapacity;
    		
    	}
    	size--;
    	return temp;
    }
 
   
}
 
 
 
