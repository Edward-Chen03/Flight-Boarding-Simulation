import java.util.Random;
import java.util.*;
 
public class FlightSimulator {
    private static Flight[] flightTerminals = new Flight[20];
    private static int flightInd = 0;
    private static int airForce = -1;
    private static int ID = 1;
    private static Random randomNumberGenerator = new Random();
    private static boolean airForceLeft = false;
    private static int covidTimer = 0;
    public static boolean getOccurence(float p){
        return randomNumberGenerator.nextFloat() < p;
    }
    public static void main (String[] args){
       Scanner input = new Scanner(System.in);
       System.out.println("Enter a seed for this simulation: ");
       long seed = input.nextLong();
       System.out.println("Enter the probability of a passenger arrival: ");
       float arrivalProb = input.nextFloat();
       System.out.println("Enter the probabiltiy that a passenger is dequeued: ");
       float deQueProb = input.nextFloat();
       System.out.println("Enter the probability that there is a new flight at RFK: ");
       float newProb = input.nextFloat();
       System.out.println("Enter how many miniutes this simulation should take");
       int time = input.nextInt();
       if(time < 0 || newProb < 0 || deQueProb < 0 || arrivalProb < 0) {
    	   System.out.println("Error! Please restart and enter postiive values!");
    	   System.exit(0);
       }
       int counter = time;
       randomNumberGenerator = new Random(seed);
       while (time > 0) {
    	   int currentTime = counter - time;
    	   System.out.println("Minute "+ currentTime);
    	   
    	   System.out.println("Events:");
    	   if(covidTimer > 0) {
    		   System.out.println("Due to covid protocol, All flights were delayed(departure and boarding) by "+ covidTimer +" minute(s)");
    	   }
    	   
    	   if(airForceLeft) {
    		   System.out.println("Air Force 1 has departed! Resuming all other departures and boarding...");
    		   airForceLeft = false;
    	   }
    	   if(airForce > -1) {
    			   if(flightTerminals[airForce] != null) {
    				   if(getOccurence(deQueProb)) {
    					   if(flightTerminals[airForce].getBoardingTime() > 0 && flightTerminals[airForce].getBoardQueue().getSize() > 0) {
   							try {
   								Passenger temp = flightTerminals[airForce].getBoardQueue().dequeuePassenger();
   								flightTerminals[airForce].addToFlight(temp);
   								System.out.println(temp.getPassClass()+" passenger with ID: "+temp.getID()+" has just boarded Air Force Flight "+flightTerminals[airForce].getDestination());
   							} catch (NoPassengerException | NoSpaceException e) {
   								System.out.println(e + " Either the flight was full or we were unable to accomdate your travel class!");
   							}
   					   }
    				   }
    			   }
        			   if(flightTerminals[airForce] != null && flightTerminals[airForce].getBoardingTime() > 0) {
        				   if(getOccurence(arrivalProb)) {
        					   if(getOccurence((float)0.12)) {
        						  try {
    								flightTerminals[airForce].getBoardQueue().enqueuePassenger(new Passenger(ID, currentTime, TravelClass.firstClass));
    								System.out.println(TravelClass.firstClass+" passenger with ID:"+ID+" on flight to "+ flightTerminals[airForce].getDestination()+" has entered the flight's boarding queue!");
    	    						  ID++;
    							} catch (NoRoomException e) {
    								System.out.println(e + " The flight is full and we were unable to downgrade your travel class!("+TravelClass.firstClass+") as the other classes are also full!");
    							}
        					   }
        					   else if(getOccurence((float)0.12)) {
        						   try {
    								flightTerminals[airForce].getBoardQueue().enqueuePassenger(new Passenger(ID, currentTime, TravelClass.businessClass));
    								System.out.println(TravelClass.businessClass+" passenger with ID:"+ID+" on flight to "+ flightTerminals[airForce].getDestination()+" has entered the flight's boarding queue!");
    	     						  ID++;
    							} catch (NoRoomException e) {
    								System.out.println(e + " The flight is full and we were unable to downgrade your travel class!("+TravelClass.businessClass+") as the other classes are also full!");
    							};
         						  
        					   }
        					   else if(getOccurence((float)0.32)) {
        						   try {
    								flightTerminals[airForce].getBoardQueue().enqueuePassenger(new Passenger(ID, currentTime, TravelClass.premiumEconomy));
    								System.out.println(TravelClass.premiumEconomy+" passenger with ID:"+ID+" on flight to "+ flightTerminals[airForce].getDestination()+" has entered the flight's boarding queue!");
    	     						  ID++;
    							} catch (NoRoomException e) {
    								System.out.println(e + " The flight is full and we were unable to downgrade your travel class!("+TravelClass.premiumEconomy+") as the other classes are also full!");
    							}
        					   }
        					   else if(getOccurence((float)0.02)) {
        						   System.out.println("COVID Passenger found trying to board "+flightTerminals[airForce].getDestination()+" Current Air Force 1 depature/boarding extended by 10 mins!");
        							   if(flightTerminals[airForce] != null && flightTerminals[airForce].getDepartingTime() != 0) {
        								   if(flightTerminals[airForce].getBoardingTime() > 0) {
        									   flightTerminals[airForce].setBoardingTime(flightTerminals[airForce].getBoardingTime() + 10);
        								   }
        								   else {
        									   flightTerminals[airForce].setDepartingTime(flightTerminals[airForce].getDepartingTime() + 10);
        								   }
        							   }
        							  
        							   covidTimer += 10;
        						   }
        					   }
        					   else {
        						   try {
    								flightTerminals[airForce].getBoardQueue().enqueuePassenger(new Passenger(ID, currentTime, TravelClass.economy));
    								System.out.println(TravelClass.economy+" passenger with ID: "+ID+" on flight to "+ flightTerminals[airForce].getDestination()+" has entered the flight's boarding queue!");
    	     						  ID++;
    							} catch (NoRoomException e) {
    								System.out.println(e + "The flight is full, sorry ");
    							}
        					   }
        				   }
        			   }
    	   else {
    	   for(int i = 0; i < flightTerminals.length;i++) {
			   if(flightTerminals[i] != null) {
				   if(getOccurence(deQueProb)) {
					   if(flightTerminals[i].getBoardingTime() > 0 && flightTerminals[i].getBoardQueue().getSize() > 0) {
							try {
								Passenger temp = flightTerminals[i].getBoardQueue().dequeuePassenger();
								flightTerminals[i].addToFlight(temp);
								System.out.println(temp.getPassClass()+" passenger with ID: "+temp.getID()+" has just boarded "+flightTerminals[i].getDestination());
							} catch (NoPassengerException | NoSpaceException e) {
								System.out.println(e + " Either the flight was full or we were unable to accomdate your travel class!");
							}
					   }
			   }
			   }
		   }
    	  for(int i = 0; i < flightTerminals.length; i++) {
    			   if(flightTerminals[i] != null && flightTerminals[i].getBoardingTime() > 0) {
    				   if(getOccurence(arrivalProb)) {
    					   if(getOccurence((float)0.12)) {
    						  try {
								flightTerminals[i].getBoardQueue().enqueuePassenger(new Passenger(ID, currentTime, TravelClass.firstClass));
								System.out.println(TravelClass.firstClass+" passenger with ID:"+ID+" on flight to "+ flightTerminals[i].getDestination()+" has entered the flight's boarding queue!");
	    						  ID++;
							} catch (NoRoomException e) {
								System.out.println(e + " The flight is full and we were unable to downgrade your travel class!("+TravelClass.firstClass+") as the other classes are also full!");
							}
    					   }
    					   else if(getOccurence((float)0.12)) {
    						   try {
								flightTerminals[i].getBoardQueue().enqueuePassenger(new Passenger(ID, currentTime, TravelClass.businessClass));
								System.out.println(TravelClass.businessClass+" passenger with ID:"+ID+" on flight to "+ flightTerminals[i].getDestination()+" has entered the flight's boarding queue!");
	     						  ID++;
							} catch (NoRoomException e) {
								System.out.println(e + " The flight is either full or we were unable to downgrade your travel class!("+TravelClass.businessClass+") as the lower classes are also full!");
							};
     						  
    					   }
    					   else if(getOccurence((float)0.32)) {
    						   try {
								flightTerminals[i].getBoardQueue().enqueuePassenger(new Passenger(ID, currentTime, TravelClass.premiumEconomy));
								System.out.println(TravelClass.premiumEconomy+" passenger with ID:"+ID+" on flight to "+ flightTerminals[i].getDestination()+" has entered the flight's boarding queue!");
	     						  ID++;
							} catch (NoRoomException e) {
								System.out.println(e + " The flight is full and we were unable to downgrade your travel class!("+TravelClass.premiumEconomy+") as the lower classes are also full!");
							}
    					   }
    					   else if(getOccurence((float)0.02)) {
    						   System.out.println("COVID Passenger found trying to board "+flightTerminals[i].getDestination()+" All current depatures and boarding extended by 10 mins!");
    						   for(int j = 0; j < flightTerminals.length; j++) {
    							   if(flightTerminals[j] != null && flightTerminals[j].getDepartingTime() != 0) {
    								   if(flightTerminals[j].getBoardingTime() > 0) {
    									   flightTerminals[j].setBoardingTime(flightTerminals[j].getBoardingTime() + 10);
    								   }
    								   else {
    									   flightTerminals[j].setDepartingTime(flightTerminals[j].getDepartingTime() + 10);
    								   }
    							   }
    						   }
    						   
    						   covidTimer += 10;
    					   }
    					   else {
    						   try {
								flightTerminals[i].getBoardQueue().enqueuePassenger(new Passenger(ID, currentTime, TravelClass.economy));
								System.out.println(TravelClass.economy+" passenger with ID: "+ID+" on flight to "+ flightTerminals[i].getDestination()+" has entered the flight's boarding queue!");
	     						  ID++;
							} catch (NoRoomException e) {
								System.out.println(e + "The flight is full, sorry ");
							}
    					   }
    				   }
    			   }
    		   }
    	   }
    	  if(getOccurence(newProb)) {
   		   if(openTerminal() == -1) {
   			   System.out.println("All terminals are full!");
   		   }
   		   else{
   		   if(airForce > -1) {
   			if(getOccurence((float)1)) {
    			   System.out.println("A new flight has appeared! What should be it's destination?");
    			   String dest = input.next();
    			   flightTerminals[flightInd] = new Flight(dest);
    			   flightInd = openTerminal();
    			   System.out.println("A new flight to "+dest+" will begin to board after Air Force 1 has left!");
    		   }
   		   }
   		   else {
   		   if(getOccurence((float)0.95)) {
   			   System.out.println("A new flight has appeared! What should be it's destination?");
   			   String dest = input.next();
   			   flightTerminals[flightInd] = new Flight(dest);
   			   flightInd = openTerminal();
   			   System.out.println("A new flight to "+dest+" has begun boarding!");
   		   }
   		   else {
   			   System.out.println("A new flight has appeared! What should be it's destination?");
   			   String dest = input.next();
   			   flightTerminals[flightInd] = new Flight(dest, true);
   			   airForce = flightInd;
   			   flightInd = openTerminal();
   			   System.out.println("A new flight on Air Force 1 to "+ dest+" has begun boarding!");
   			   System.out.println("All other flights will be postponed!");
   			   
   		   }
   		   }
   		   }
   	   }
    	  
    		   
    		   
    	   
    	   System.out.println("Currently Boarding:");
    	   boolean checkEmpty = true;
    	   for (int i = 0; i < flightTerminals.length; i++) {
    		   if(flightTerminals[i] != null) {
    			   checkEmpty = false;
    		   }
    	   }
    	   if(checkEmpty) {
    		   System.out.println("Nothing to note...");
    	   }
    	   else if(airForce != -1) {
    		   if(flightTerminals[airForce].getBoardingTime() > 0) {
    		   System.out.println(flightTerminals[airForce]);
    		   System.out.println("Pausing all other boarding and departures!");
    		   }
    	   }
    	   else {
    		   for(int i = 0; i < flightTerminals.length;i++) {
    			   if(flightTerminals[i] != null && flightTerminals[i].getBoardingTime() > 0) {
    				   System.out.println(flightTerminals[i]);
    			   }
    		   }
    	   }
    	   
    	   System.out.println("Departing:");
    	   boolean checkDeparting = false;
    	   for(int i = 0; i < flightTerminals.length; i++) {
    		   if(flightTerminals[i] != null && flightTerminals[i].getBoardingTime() == 0) {
    			   checkDeparting = true;
    		   }
    	   }
    	   if(!checkDeparting) {
    		   System.out.println("Nothing to note...");
    	   }
    	   else {
    		   if(airForce != -1) {
    			   if(flightTerminals[airForce].getBoardingTime() == 0 && flightTerminals[airForce].getDepartingTime() > 0 ) {
    			   System.out.println(flightTerminals[airForce]);
    			   System.out.println("Pausing all other boardings and departures!");
    		       }
    		   }
    		   else {
    			  for(int i = 0; i < flightTerminals.length; i++) {
    				  if(flightTerminals[i] != null && flightTerminals[i].getBoardingTime() == 0 && flightTerminals[i].getDepartingTime() > 0) {
    					  System.out.println(flightTerminals[i]);
    				  }
    			  }
    			 }
    	   }
    	   
    	   System.out.println("Final Departures:");
    	   if(airForce > -1) {
    		   if(flightTerminals[airForce].getDepartingTime() == 0) {
    			   System.out.println("Flight RFK -> "+flightTerminals[airForce].getDestination());
    			   System.out.println(flightTerminals[airForce]);
    			   flightTerminals[airForce] = null;
    			   if(openTerminal() == -1) {
    				   flightInd = airForce;
    			   }
    			   airForce = -1;
    			   airForceLeft = true;
    			   
    		   }
    	   }
    	   else {
    	   for(int i = 0; i < flightTerminals.length;i++) {
    		   if(flightTerminals[i] != null && flightTerminals[i].getDepartingTime() == 0) {
    			   System.out.println("Flight RFK -> "+flightTerminals[i].getDestination());
    			   System.out.println(flightTerminals[i]);
    			   flightTerminals[i] = null;
    			   if(openTerminal() == -1) {
    				   flightInd = i;
    			   }
    		   }
    	   }
    	   }
    	   
    	  updateFlights();
    	  if(covidTimer > 0) {
    		  covidTimer--;
    	  }
    	   time--;
       }
    	   
       
       System.out.println("End of Simulation!");
       input.close();
       
    }
    
    public static void updateFlights() {
    	for(int i = 0; i < flightTerminals.length; i++) {
    		if(flightTerminals[i] != null && (flightTerminals[i].getBoardingTime() == 0 || flightTerminals[i].getClassSize() == 15)) {
    			if(flightTerminals[i].getClassSize() == 15 && flightTerminals[i].getBoardingTime() > 0) {
    				System.out.println("All seats have been filled! Flight "+flightTerminals[i].getDestination()+" will now begin to depart! (All Boarding Queue Passengers will have to attend the next one!)");
    			}
    			flightTerminals[i].setBoardingTime(0);
    			flightTerminals[i].setDeparting();
    		}
    	}
    	if(airForce != -1 && flightTerminals[airForce] != null) {
    		if(flightTerminals[airForce].getDeparting() == false) {
    			flightTerminals[airForce].setBoardingTime(flightTerminals[airForce].getBoardingTime()-1);
    		}
    		else {
    			flightTerminals[airForce].setDepartingTime(flightTerminals[airForce].getDepartingTime()-1);
    		}
    	}
    	else {
    	for(int i = 0; i < flightTerminals.length; i++) {
    		if(flightTerminals[i] != null && flightTerminals[i].getDeparting() == false) {
    			flightTerminals[i].setBoardingTime(flightTerminals[i].getBoardingTime()-1);
    		}
    		else if(flightTerminals[i] != null && flightTerminals[i].getDepartingTime() > 0){
    			flightTerminals[i].setDepartingTime(flightTerminals[i].getDepartingTime()-1);
    		}
    	}
    	}
    }
    
    public static int openTerminal() {
    	for(int i = 0; i < flightTerminals.length; i++) {
    		if(flightTerminals[i] == null) {
    			return i;
    		}
    	}
    	return -1;
    }
    
}
