import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Badugi500639696v4  implements BadugiPlayer {
	public int curr_position;
	public int hands_to_go;
	public int current_score;
	boolean bluff = false;
	boolean just_fold = false;
	String name;
	//This version bluffs when it only has a badugi of 3 or less
	
	public Badugi500639696v4(){
		this.name = "V4";
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
	
	/**
     * The method to ask the agent what betting action it wants to perform.
     * @param drawsRemaining How many draws are remaining after this betting round.
     * @param hand The current hand held by this player.
     * @param bets How many bets and raises there have been in this betting round.
     * @param pot The current size of the pot.
     * @param toCall The cost to call to stay in the pot.
     * @param opponentDrew How many cards the opponent drew in the previous drawing round. In the
     * first betting round, this argument will be -1.
     * @return The desired betting action given as an integer whose sign determines the action. Any negative
     * number means FOLD/CHECK, zero means CHECK/CALL, and any positive number means BET/RAISE.
     */
	@Override
	public int bettingAction(int drawsRemaining, BadugiHand hand, int bets, int pot, int toCall, int opponentDrew) {
		if(bets <= 1){
			if(bluff){
				bluff = false;
				return 10;
			}else if(just_fold){
				if(toCall == 0)
					return -1;
				else{
				just_fold = false;
				return -1;
				}
				
			}
			if(get_highest_badugi_card_rank(hand) >= 9 && drawsRemaining < 2 ){
				if(opponentDrew >=2 && hand.getActiveCards().size() >=2) return 1;
				return 0;
			}
			else if(get_highest_badugi_card_rank(hand) <= 8 && get_highest_badugi_card_rank(hand) >= 5 && drawsRemaining < 2){
				if(opponentDrew >=2 && hand.getActiveCards().size() >=2) return 1;
				return 0;
			}
			else if(get_highest_badugi_card_rank(hand) <= 4 && drawsRemaining < 2){
				if(hand.getActiveCards().size() >=3)return 1;
				return 1;
			}
			
			if(get_highest_badugi_card_rank(hand) >= 5 && drawsRemaining >= 2){
	
				return 0;
			}
			else if(get_highest_badugi_card_rank(hand) <= 4 && drawsRemaining >= 2) return 1;
		}else{
			if(bluff){
				bluff = false;
				return 10;
			}
			if(get_highest_badugi_card_rank(hand) >= 9 && drawsRemaining < 2 ){
				if(opponentDrew >=2 && hand.getActiveCards().size() >=2) return 1;
				return 0;
			}
			else if(get_highest_badugi_card_rank(hand) <= 8 && get_highest_badugi_card_rank(hand) >= 5 && drawsRemaining < 2){
				if(opponentDrew >=2 && hand.getActiveCards().size() >=2) return 1;
				return 0;
			}
			else if(get_highest_badugi_card_rank(hand) <= 4 && drawsRemaining < 2){
				if(hand.getActiveCards().size() >=3)return 1;
				return 1;
			}
			
			if(get_highest_badugi_card_rank(hand) >= 5 && drawsRemaining >= 2){
	
				return 0;
			}
			else if(get_highest_badugi_card_rank(hand) <= 4 && drawsRemaining >= 2) return 1;
		}
		
		return 0;
		//if(get_highest_badugi_card(hand) >= 9 && drawsRemaining < 2 ) return -1;
		//else if(get_highest_badugi_card(hand) <= 8 && get_highest_badugi_card(hand) >= 5 && drawsRemaining < 2) return 0;
		//else if(get_highest_badugi_card(hand) <= 4 && drawsRemaining < 2) return 1;
		//return 0;
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
	/*	if(drawsRemaining == 3){
			int high_hand = 0;
			Card rank[] = get_highest_badugi_card_rank(2, hand, "");
			for(int i = 0; i < rank.length; i++){
				if( rank[i].getRank() > 10) high_hand++;
					
			}
			if(high_hand == 2){
				just_fold = true;
			}else if(high_hand == 1){
				return_list.add(get_highest_badugi_card(hand));
			}
		}*/
		//or change the hand size to be less than 3
		if(hand.getActiveCards().size() <= 2 && drawsRemaining <= 2) bluff = true;
		else
			return_list.addAll(hand.getInactiveCards());
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
		//System.out.println("Current Score: " +current_score);
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
	
	public Card get_highest_badugi_card(BadugiHand hand){
		Card highest = null;
		for(Card a : hand.getActiveCards()){
			if(highest ==  null) highest = a;
			else if(a.getRank() > highest.getRank()) highest = a;
		}
		if(highest.getRank() == -1){
			System.out.println("Error");
			return null;
		}else return highest;
	}
	
	public int get_highest_badugi_card_rank(BadugiHand hand){
		int highest = -1;
		for(Card a : hand.getActiveCards()){
			if(a.getRank() > highest) highest = a.getRank();
		}
		if(highest == -1){
			System.out.println("Error");
			return -1;
		}else return highest;
	}
	
	public int[] get_highest_badugi_card_rank(int num, BadugiHand hand){
		int[] rank = new int[num];
		for(int i = 0; i < num; i++){
			rank[i] = -1;
		}
		for(Card a : hand.getActiveCards()){
			for(int i = 0; i < num; i++){
				if(a.getRank() > rank[i]) rank[i] = a.getRank();
				break;
			}
			
		}
		return rank;
	}
	
	public Card[] get_highest_badugi_card_rank(int num, BadugiHand hand, String x){
		Card[] rank = new Card[num];
		for(int i = 0; i < num; i++){
			rank[i] = new Card(-1, 'x');
		}
		for(Card a : hand.getActiveCards()){
			for(int i = 0; i < num; i++){
				if(a.getRank() > rank[i].getRank()) rank[i] = a;
				break;
			}
			
		}
		return rank;
	}

}