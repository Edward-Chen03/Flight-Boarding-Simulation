public class Passenger {
   private TravelClass passClass;
   private int passengerID, arrivalTime;
 
   public Passenger(){
        passengerID = 0;
        arrivalTime = 0;
   }
 
   public Passenger(int id, int time, TravelClass p){
       passengerID = id;
       arrivalTime = time;
       passClass = p;
   }
 
   public int getID(){
       return passengerID;
   }
   
   public TravelClass getPassClass() {
	   return passClass;
   }
   
   public void setTravelClass(TravelClass p) {
	   passClass = p;
   }
 
   public int getArrivalTime(){
       return arrivalTime;
   }
 
   public void setArrivalTime(int time){
        arrivalTime = time;
   }
   
   public String toString() {
	   if(passClass.toString().equals("firstClass") || passClass.toString().equals("premiumEconomy") || passClass.toString().equals("businessClass")) {
		   return passClass +"	| "+passengerID+"	| "+arrivalTime;
	   }
	   else {
	   return passClass +"		| "+passengerID+"	| "+arrivalTime;
	   }
   }
}
