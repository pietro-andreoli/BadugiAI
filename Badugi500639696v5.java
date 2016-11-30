import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Badugi500639696v5  implements BadugiPlayer {
	public int curr_position;
	public int hands_to_go;
	public int current_score;
	boolean bluff = false;
	boolean just_fold = false;
	String name;
	double draw_odds_min = 0.6;
	int loss_count_conseq = 0;
	//0 is clubs, 1 is diamonds, 2 is hearts, 3 is spades
	ArrayList<ArrayList<Integer>> cards_in_deck = new ArrayList<ArrayList<Integer>>();
	//This version bluffs when it only has a badugi of 3 or less
	
	public Badugi500639696v5(){
		this.name = "V5";
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
		refresh_deck(cards_in_deck);
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
		//System.out.println("Active Hand: "+hand.getActiveCards().toString());
		for(int i = 0; i < cards_in_deck.size(); i++){
			for(Card card : hand.getAllCards()){
				if(card.getSuit() == i){
					cards_in_deck.remove(card.getRank()-1);
				}
			}
		}
		//
		List<Card> return_list = new ArrayList<Card>();
		if(drawsRemaining == 3){
			
			int high_hand = 0;
			
			Card rank[] = get_highest_badugi_card_rank(2, hand, "");
			//System.out.println("Hello");
			for(int i = 0; i < rank.length; i++){
				if( rank[i].getRank() > 10) high_hand++;
				
			}
			
			//System.out.println("ayy lmao"+high_hand);
			if(high_hand == 2){
				just_fold = true;
				//System.out.println("Folding!");
			}else if(high_hand == 1){
				//return_list.add(get_highest_badugi_card(hand));
				double odds = 0;
				Integer temp [] = {0,1,2,3};
				//Getting what suits we have in the hand rn.
				ArrayList<Integer> suit_list = new ArrayList<Integer>(Arrays.asList(temp));
				
				for(int i = 0; i < 3; i++){
					for(Card card : hand.getActiveCards()){
						if(card.getSuit() == i){
							suit_list.set(i, -1);
						}
					}
				}
				//get probability of pulling a card with suit we dont have in badugi and low number
				odds = probability_of_pulling(suit_list, 5);
				if(odds > draw_odds_min){ //0.6
					return_list.add(get_highest_badugi_card(hand));
				}
			}
		}
		
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
		
		if(yourHand.compareTo(opponentHand) > 0){
			if(draw_odds_min < 0.7){
				if(draw_odds_min + 0.1 < 0.9)
					draw_odds_min += 0.1;
			}
		}else{
			loss_count_conseq ++;
			if(draw_odds_min > 0.1 && loss_count_conseq > 2)
				draw_odds_min -= 0.1;
			
		}
		//System.out.println(draw_odds_min);
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
			rank[i] = new Card(0, 0);
		}
		for(Card a : hand.getActiveCards()){
			for(int i = 0; i < num; i++){
				if(a.getRank() > rank[i].getRank()){
					rank[i] = a;
					break;
				}
				
			}
			
		}
		return rank;
	}
	
	public ArrayList<ArrayList<Integer>> refresh_deck(ArrayList<ArrayList<Integer>> deck){
		for(ArrayList<Integer> suit : deck){
			suit.clear();
			for(int i = 1; i <= 13; i++){
				suit.add(i);
			}
		}
		return deck;
	}
	
	public int cards_left(){
		int total_cards=0;
		for(ArrayList<Integer> i : cards_in_deck){
			for(int j : i){
				total_cards++;
			}
		}
		return total_cards;
	}
	
	public float probability_of_pulling(ArrayList<Integer> suits, int highest_rank){
		float possible_cards_wanted = 0;
		for(int i : suits){
			if(i != -1){
				for(int j : cards_in_deck.get(i)){
					if (j <= highest_rank) possible_cards_wanted++;
				}
			}
		}
		
		return possible_cards_wanted/((float)cards_left());
	}

}