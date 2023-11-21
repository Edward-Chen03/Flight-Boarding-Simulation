public class Flight {
    private String destination;
    private BoardingQueue boardQueue = new BoardingQueue();
    private Passenger[] firstClass = new Passenger[2];
    private Passenger[] businessClass = new Passenger[3];
    private Passenger[] premiumEconomy = new Passenger[4];
    private Passenger[] economy = new Passenger[6];
    private int minutesLeftBoarding,minutesLeftDeparting,firstClassInd,businessClassInd,premiumEconomyInd,economyInd;
    private boolean boarding;
    private boolean airForce;
 
    public Flight(){
    	boarding = false;
    	minutesLeftBoarding = 0;
    	minutesLeftDeparting = 0;
    	firstClassInd = 0;
    	businessClassInd = 0;
    	premiumEconomyInd = 0;
    	economyInd = 0;
    	airForce = false;
    	
    }
 
    public Flight(String d){
        destination = d;
        minutesLeftBoarding = 25;
        minutesLeftDeparting = 5;
        airForce = false;
    }
    
    public Flight(String d, boolean t){
        destination = d;
        minutesLeftBoarding = 25;
        minutesLeftDeparting = 5;
        airForce = true;
    }
   
    public int getClassSize() {
    	return (firstClassInd+businessClassInd+premiumEconomyInd+economyInd);
    }
    public String getDestination() {
    	return destination;
    }
    
    public void setDeparting() {
    	boarding = true;
    }
    
    public boolean getDeparting() {
    	return boarding;
    }
    
    public int getBoardingTime() {
    	return minutesLeftBoarding;
    }
    
    public int getDepartingTime() {
    	return minutesLeftDeparting;
    }
    
    public void setBoardingTime(int time) {
    	minutesLeftBoarding = time;
    }
    
    public void setDepartingTime(int time) {
    	minutesLeftDeparting = time;
    }
    
    public BoardingQueue getBoardQueue() {
    	return boardQueue;
    }
    
    public void addFirstClass(Passenger boarded){
    	firstClass[firstClassInd] = boarded;
    	firstClassInd++;
    }
    
    public void addBusinessClass(Passenger boarded){
    	businessClass[businessClassInd] = boarded;
    	businessClassInd++;
    }
    
    public void addPremEconomy(Passenger boarded){
    	premiumEconomy[premiumEconomyInd] = boarded;
    	premiumEconomyInd++;
    }
    
    
    public void addEconomy(Passenger boarded){
    	economy[economyInd] = boarded;
    	economyInd++;
    }
 
    public void addToFlight(Passenger boardedPass) throws NoSpaceException{
       if (boardedPass.getPassClass().toString().equals("firstClass")) {
    	   if(firstClassInd < 2) {
    	   addFirstClass(boardedPass);
    	   }
    	   else {
    		   if(businessClassInd < 3) {
    			   boardedPass.setTravelClass(TravelClass.businessClass);
    			   addBusinessClass(boardedPass);
    		   }
    		   else {
    			   if(premiumEconomyInd < 4) {
    				   boardedPass.setTravelClass(TravelClass.premiumEconomy);
    				   addPremEconomy(boardedPass);
    			   }
    			   else {
    				   if(economyInd < 6) {
    					   boardedPass.setTravelClass(TravelClass.economy);
    					   addEconomy(boardedPass);
    				   }
    				   else {
    					   throw new NoSpaceException();
    				   }
    			   }
    		   }
    	   }
    	   
       }
       else if(boardedPass.getPassClass().toString().equals("businessClass")) {
    	   if(businessClassInd < 3) {
			   addBusinessClass(boardedPass);
		   }
		   else {
			   if(premiumEconomyInd < 4) {
				   boardedPass.setTravelClass(TravelClass.premiumEconomy);
				   addPremEconomy(boardedPass);
			   }
			   else {
				   if(economyInd < 6) {
				   boardedPass.setTravelClass(TravelClass.economy);
				   addEconomy(boardedPass);
				   }
				   else {
					   throw new NoSpaceException();
				   }
			   }
		   }
	   }
       else if(boardedPass.getPassClass().toString().equals("premiumEconomy")) {
    	   if(premiumEconomyInd < 4) {
			   addPremEconomy(boardedPass);
		   }
		   else {
			   if(economyInd < 6) {
			   boardedPass.setTravelClass(TravelClass.economy);
			   addEconomy(boardedPass);
			   }
			   else {
				   throw new NoSpaceException();
			   }
		   }
	   }
       else if(boardedPass.getPassClass().toString().equals("economy")){
    	   if(economyInd < 6) {
    		   addEconomy(boardedPass);
    	   }
    	   else {
    		   throw new NoSpaceException();
    	   }
       }
    }
    
    public String toString() {
    	if(airForce) {
    		if(minutesLeftBoarding > 0) {
    		return "Air Force 1 flight to "+destination+" has "+minutesLeftBoarding+" minute(s) to board,"+(firstClassInd+businessClassInd+premiumEconomyInd+economyInd)+" passenger(s) and "+boardQueue.getSize()+" people are waiting to board!";
    		}
    		else {
    			if(minutesLeftDeparting == 0) {
    				print();
    				return "";
    			}
    			else {
    				return "Air Force 1 flight to "+destination+" has "+(firstClassInd+businessClassInd+premiumEconomyInd+economyInd)+" passenger(s) and is "+minutesLeftDeparting+" minute(s) away from departure";
    			}
    			
    		}
    	}
    	else if(minutesLeftDeparting == 0) {
    		print();
    		return "";
    	}
    	else if(minutesLeftBoarding == 0) {
    		return "Flight to "+destination+" has "+(firstClassInd+businessClassInd+premiumEconomyInd+economyInd)+" passenger(s) and is "+minutesLeftDeparting+" minute(s) away from departure";
    	}
    	else {
    	return "Flight to "+destination+" has "+minutesLeftBoarding+" minutes to board, "+(firstClassInd+businessClassInd+premiumEconomyInd+economyInd)+" passenger(s) and "+boardQueue.getSize()+" people are waiting to board!";
    	}
    }
    public void print() {
    	System.out.println("Seat Type	| ID	| Arrival Time	");
    	if(firstClassInd > 1) {
			for(int j = 0; j < firstClassInd - 1;j++) {
				if(firstClass[j].getArrivalTime() > firstClass[j+1].getArrivalTime()) {
					Passenger temp = firstClass[j];
					firstClass[j] = firstClass[j+1];
					firstClass[j+1] = temp;
					j = -1;
				}
			}
			}
    	for(int i = 0; i < firstClass.length; i++) {
    		if(firstClass[i] != null) {
    			System.out.println(firstClass[i]);
    		}
    	}
    	if(businessClassInd > 1) {
			for(int j = 0; j < businessClassInd - 1;j++) {
				if(businessClass[j].getArrivalTime() > businessClass[j+1].getArrivalTime()) {
					Passenger temp = businessClass[j];
					businessClass[j] = businessClass[j+1];
					businessClass[j+1] = temp;
					j = -1;
				}
			}
			}
    	for(int i = 0; i < businessClass.length; i++) {
    		if(businessClass[i] != null) {
        		System.out.println(businessClass[i]);
        		}
    	}
    	if(premiumEconomyInd > 1) {
			for(int j = 0; j < premiumEconomyInd - 1;j++) {
				if(premiumEconomy[j].getArrivalTime() > premiumEconomy[j+1].getArrivalTime()) {
					Passenger temp = premiumEconomy[j];
					premiumEconomy[j] = premiumEconomy[j+1];
					premiumEconomy[j+1] = temp;
					j = -1;
				}
			}
			}
			
    	for(int i = 0; i < premiumEconomy.length; i++) {
    		if(premiumEconomy[i] != null) {
        		System.out.println(premiumEconomy[i]);
    			
        		}
    	}
    	if(economyInd > 1) {
			for(int j = 0; j < economyInd - 1;j++) {
				if(economy[j].getArrivalTime() > economy[j+1].getArrivalTime()) {
					Passenger temp = economy[j];
					economy[j] = economy[j+1];
					economy[j+1] = temp;
					j = -1;
				}
			}
			}
    	for(int i = 0; i < economy.length; i++) {
    		if(economy[i] != null) {
    			System.out.println(economy[i]);
    		}
    			}
        		}
    	

    	
    }
    

