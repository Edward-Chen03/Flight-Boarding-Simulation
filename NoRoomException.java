
public class NoRoomException extends Exception {
	public NoRoomException(){
		super("Error! There is currently no more room in the Boarding Queue!");
	}
}
