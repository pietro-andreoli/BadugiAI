import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Badugi500639696 implements BadugiPlayer {
	public int curr_position;
	public int hands_to_go;
	public int current_score;
	String name;
	public Badugi500639696(){
		this.name = "Pietro";
	}
	
	/**
     * The method to tell the agent that a new hand is starting.
     * @param position 0 if the agent is the dealer in this hand, 1 if the opponent.
     * @param handsToGo The number of hands left to play in this heads-up tournament.
     * @param currentScore The current score of the tournament.
     */
	@Override
	public void startNewHand(int position, int handsToGo, int currentScore) {
		// TODO Auto-generated method stub
		curr_position = position;
		hands_to_go = handsToGo;
		current_score = currentScore;
	}
	
	
	@Override
	public int bettingAction(int drawsRemaining, BadugiHand hand, int bets, int pot, int toCall, int opponentDrew) {
		
		
		return 0;
	}
	
	 /**
     * The method to ask the agent which cards it wants to replace in this drawing round.
     * @param drawsRemaining How many draws are remaining, including this drawing round.
     * @param hand The current hand held by this player.
     * @param pot The current size of the pot.
     * @param dealerDrew How many cards the dealer drew in this drawing round. When this method is called
     * for the dealer, this argument will be -1.
     * @return The list of cards in the hand that the agent wants to replace.
     */
	@Override
	public List<Card> drawingAction(int drawsRemaining, BadugiHand hand, int pot, int dealerDrew) {
	//	System.out.println("Current Hand: " + hand.getAllCards().toString());
	//	System.out.println("Active Hand: "+hand.getActiveCards().toString());
		List<Card> return_list = new ArrayList<Card>();
		int highest_acceptable_rank = -1;
		//If we only have a badugi hand of size 2 or less and we have ample time to draw more cards,
		//then we probably want to see if any of the two cards are worth replacing. But we dont have such a large
		//badugi hand so lets set the highest tolerable rank to be pretty high.
		if(drawsRemaining >= 2 ){
			if(hand.getActiveCards().size() <= 2){
				for (int i = 0; i < hand.getActiveCards().size(); i++ ){					
					if(hand.getActiveCards().get(i).getRank() > 8 ){
						return_list.add(hand.getActiveCards().get(i));
					}
				}
			//We have ample time and a playable hand. Lets start to look at what we want and dont want in our badugi
			
			}else if(hand.getActiveCards().size() >= 3){
				int threshold = (int)get_average(hand.getActiveCards());
				//if the average is less than 5 then we prob have a decent hand. lets only get rid of the garbage. 
				if(threshold < 5) highest_acceptable_rank = 6;
				else highest_acceptable_rank = threshold;
				for (int i = 0; i < hand.getActiveCards().size(); i++ ){					
					if(hand.getActiveCards().get(i).getRank() > highest_acceptable_rank ){
						return_list.add(hand.getActiveCards().get(i));
					}
				}
			}
		}else if(drawsRemaining < 2){
			if(hand.getActiveCards().size() <= 2){
				for (int i = 0; i < hand.getActiveCards().size(); i++ ){					
					if(hand.getActiveCards().get(i).getRank() > 8 ){
						return_list.add(hand.getActiveCards().get(i));
					}
				}
			//We have ample time and a playable hand. Lets start to look at what we want and dont want in our badugi
			
			}else if(hand.getActiveCards().size() >= 3){
				int threshold = Math.round(get_average(hand.getActiveCards()));
				//if the average is less than 5 then we prob have a decent hand. lets only get rid of the garbage. 
				if(threshold < 5) highest_acceptable_rank = 6;
				else highest_acceptable_rank = threshold;
				for (int i = 0; i < hand.getActiveCards().size(); i++ ){					
					if(hand.getActiveCards().get(i).getRank() > highest_acceptable_rank ){
						return_list.add(hand.getActiveCards().get(i));
					}
				}
			}
		}
		
		
		//else if(drawsRemaining == 2 && hand.getActiveCards().size() <= 3)
		
		for (int i = 0; i < hand.getInactiveCards().size(); i++){
			for(int j = 0; j < hand.getActiveCards().size() ; j++){
				if(hand.getInactiveCards().get(i).getSuit() == hand.getActiveCards().get(j).getSuit() 
						&& hand.getInactiveCards().get(i).getRank() - hand.getActiveCards().get(j).getRank() >= 3){
					return_list.add(hand.getInactiveCards().get(i));
				}
			}
		}
		//return_list.addAll(hand.getInactiveCards());
	//	System.out.println("Cards I am putting back: "+return_list.toString());
	//	Scanner x = new Scanner(System.in);
	//	String a = x.nextLine();
		return return_list;
	}

	@Override
	public void showdown(BadugiHand yourHand, BadugiHand opponentHand) {
	/*	System.out.println("Opponents Active Hand: "+opponentHand.getActiveCards().toString());
		System.out.println("My Active Hand: "+yourHand.getActiveCards().toString());
		System.out.print("You have ");
		if(yourHand.compareTo(opponentHand) > 0){
			System.out.println("WON");
		}else{
			System.out.println("LOST");
		}
		Scanner x = new Scanner(System.in);
		String a = x.nextLine();*/
	}

	@Override
	public String getAgentName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public String getAuthor() {
		// TODO Auto-generated method stub
		return "Pietro Andreoli 500639696";
	}
	
	public float get_average(List<Card> badugi){
		int numerator = 0;
		for(int i = 0; i < badugi.size(); i ++){
			numerator += badugi.get(i).getRank();
		}
		return numerator/badugi.size();
		
	}

}
