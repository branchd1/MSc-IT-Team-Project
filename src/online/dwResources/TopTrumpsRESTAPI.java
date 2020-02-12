package online.dwResources;

import java.io.IOException;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import online.configuration.TopTrumpsJSONConfiguration;
import toptrumps.DataCard;
import toptrumps.DataGame;
import toptrumps.DataPlayer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Path("/toptrumps") // Resources specified here should be hosted at http://localhost:7777/toptrumps
@Produces(MediaType.APPLICATION_JSON) // This resource returns JSON content
@Consumes(MediaType.APPLICATION_JSON) // This resource can take JSON content as input


/**
 * This is a Dropwizard Resource that specifies what to provide when a user
 * requests a particular URL. In this case, the URLs are associated to the
 * different REST API methods that you will need to expose the game commands
 * to the Web page.
 * 
 * Below are provided some sample methods that illustrate how to create
 * REST API methods in Dropwizard. You will need to replace these with
 * methods that allow a TopTrumps game to be controlled from a Web page.
 */


/**
 * TopTrumpsRESTAPI - 
 * @author Team Try-Catch - Bokyung Lee 2431088l
 * 
 * */
public class TopTrumpsRESTAPI {

	/** A Jackson Object writer. It allows us to turn Java objects
	 * into JSON strings easily. */
	ObjectWriter oWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();
	TopTrumpsJSONConfiguration conf = new TopTrumpsJSONConfiguration(); // added by Arnold
	
	private String deckFile;
	private int numAIPlayers;
	
	private DataGame model;
	
	/**
	 * Constructor method for the REST API. This is called first. It provides
	 * a TopTrumpsJSONConfiguration from which you can get the location of
	 * the deck file and the number of AI players.
	 * @param conf
	 */
	public TopTrumpsRESTAPI(TopTrumpsJSONConfiguration conf) {
		// ----------------------------------------------------
		// Add relevant initalization here
		// ----------------------------------------------------
		this.conf = conf; // added by Arnold
		
		//get the location of the deck file
		deckFile=conf.getDeckFile();
		
		//get the number of AI players.
		numAIPlayers=conf.getNumAIPlayers();
	}
	
	// ----------------------------------------------------
	// Add relevant API methods here
	// ----------------------------------------------------
	
	
	/*
	 * Needed methods from GameScreen.ftl
	 * 
	 * getRoundNumber();					>>getRoundNumber()
	 * shouldHumanSelectCategory();			>>shouldHumanSelectCategory()
	 * getPlayerToChooseRound(); 			???????????
	 * getDeck(); 							>>getDeckFile(), getOriginalDeckAsString() 
	 * getRoundCategory();					>>getRoundCategory()
	 * getRoundCards();						>>getRoundCards() 
	 * getRoundWinner();					>>getRoundWinner(), getRoundWinningCard()
	 * getNumberOfCardInCommonPile();		>>getNumberOfCardsInCommonPile()
	 * wasRoundDraw();						>>getRoundWasDraw() 
	 * getGamePlayers();					>>getActivePlayers(), getRoundActivePlayer() 
	 * getGameScores(); 					>>getGameResult()
	 * getGameStatistics();					>>getStatistics()
	 * getGameWinner(); 					>>getGameWinner()
	 * getPlayerDeck(playerName); 			>>getPlayerDeck(playerName), getNumberOfCardsInDeck():for all players
	 * getNumberOfRoundsInGame();			?????????? Should be same with getRoundNumber()
	 */

	
	/**
	 * Starts the game
	 * @returns 1 if failed, 0 if successful
	 */	
	@GET
	@Path("/game/startGame")
	public int startGame(@QueryParam("numberOfAIPlayers") int numberOfAIPlayers) {
		try {
			this.numAIPlayers = numberOfAIPlayers;
			this.conf.setNumAIPlayers(numberOfAIPlayers);
			this.model = DataGame.resetAndGetInstance(numberOfAIPlayers);
			this.model.startGame();
		} catch(Exception e) {
			return 1;
		}
		return 0;
	}

	
	/**
	 * Get entire deck file when the game starts.
	 * @Controller.js: function displayCard 
	 * @returns String type
	 * @throws IOException
	 */	
	@GET
	@Path("/game/displayCards")
	public String getDeckFile() {
		return deckFile;
	}
	

	
	/**
	 * get original deck as string. before shuffled.
	 * @return 
	 * @throws IOException
	 */
	@GET
	@Path("/game/originalDeckAsString")
	public String getOriginalDeckAsString() throws IOException {
		String originalDeckAsString;
		
		originalDeckAsString=oWriter.writeValueAsString(model.getCompleteDeckAsArrayList());
		return originalDeckAsString;
	}
	
	@GET
 	@Path("/game/categoryMenu")
 	public String getCategoryForMenu() throws IOException{
		List<String> listOfCategory=new ArrayList<String>();
 		for(int i=0;i<model.CATEGORYNAMES.length;i++) {
 			listOfCategory.add(model.CATEGORYNAMES[i]);
 		}		
 		String categoriesInString=oWriter.writeValueAsString(listOfCategory);
 		return categoriesInString;
	}
	
	/**
	 * Get current round number:integer.
	 * @Controller.js: function roundNumber
	 * @returns JSONString type
	 * @throws IOException
	 */	
	@GET
	@Path("/game/roundNumber")
	public String getRoundNumber() throws IOException{
		String roundNumber=oWriter.writeValueAsString(model.getRoundNumber());
		return roundNumber;
	}
	
	
	/**
	 * Get card name, card categories for all active players.
	 * @Controller.js:
	 * @return JSONString type
	 * @throws IOException
	 */	
	@GET
	@Path("/game/roundCards")
	public String getRoundCards() throws IOException{	
		List<DataCard> listOfCards=new ArrayList<DataCard>();
		listOfCards.add(model.getRoundHumanPlayerCard());
		DataCard[] cards = model.getRoundAIPlayerCards();
		for(int i=0;i<cards.length;i++) {
			listOfCards.add(cards[i]);
		}
		
		String everyPlayersCard=oWriter.writeValueAsString(listOfCards);
		return everyPlayersCard;
	}
	
	

	
	@GET
	@Path("/game/playRound")
	public String playRound(@QueryParam("category") String category){
		model.playRound(category);
		if(model.getGameState()!=DataGame.GameState.RUNNING) {
			return "" + model.getGameWinner().getName() + " at round " + model.getRoundNumber();
		}
		model.incrementRound();
		return "running";
	}
	
	
	
	/**
	 * Number of active players
	 * @Controller.js: function activePlayer
	 * @returns integer
	 * @throws IOException
	 */	
	@GET
	@Path("/game/activePlayers")
	public String getActivePlayers() throws IOException{
		List<DataPlayer> activePlayersInList=new ArrayList<DataPlayer>();
		for(int i=0;i<model.getActivePlayers().length;i++) {
			activePlayersInList.add(model.getActivePlayers()[i]);
		}
		
		String activePlayersAsString=oWriter.writeValueAsString(activePlayersInList);
		return activePlayersAsString;
	}
	
	
	/**
	 * get number of cards in common pile. which is number of active players..
	 * @return model.getNumberOfCardsInCommonPile()
	 * @throws IOException
	 * */
	@GET
	@Path("/game/numberOfCardsInCommonPile")
	public int getNumberOfCardsInCommonPile() throws IOException{
		return model.getNumberOfCardsInCommonPile();
	}
	
	
	@GET
	@Path("/game/getRoundActivePlayer")
	public String getRoundActivePlayer(){
		return model.getRoundActivePlayer().getName();
	}
	
	
	/**
	 * Get category chooser of the round:String.
	 * @Controller.js: 
	 * @returns JSONString type
	 * @throws IOException
	 */	
	@GET
	@Path("/game/categoryChooser")
	public String getCategoryChooser() throws IOException{
		String categoryChooser=oWriter.writeValueAsString(model.getCategoryChooser());
		return categoryChooser;
	}
	
	
	/**
	 *Get the category that had selected from the AI players.
	 *@return human: when active player is human, result: when there is a valid result.
	 *@throws IOException
	 * */
	@GET
	@Path("/game/getAIPlayerCategory")
	public String getAIPlayerCategory() throws IOException{
		String result = "";
		
		DataPlayer activePlayer = this.model.getCategoryChooser();
		int category = this.model.getBestCategoryForPlayer(activePlayer);
		
		// sometimes the first player is human and this is not accounted for here
		if(category==0) {
			return "human";
		}
		result = DataGame.CATEGORYNAMES[category-1];
		
		return result;
	}
	
	
	/**
	 * return boolean value as String whether human selects or not.
	 * @Controller.js:
	 * @return JSONString type
	 * @throws IOException
	 */	
	@GET
	@Path("/game/shouldHumanSelectCategory")
	public String shouldHumanSelectCategory() throws IOException{
		String result = "";
		result = oWriter.writeValueAsString(model.shouldHumanChooseCategory());

		return result;
	}
	

	
	
	/**
	 * Get round category:String.
	 * @Controller.js: function AISelectCategory
	 * @returns JSONString type
	 * @throws IOException
	 */	
	@GET
	@Path("/game/roundCategory")
	public String getRoundCategory() throws IOException{
		String roundCategory=oWriter.writeValueAsString(model.getRoundCategory());
		return roundCategory;
	}
	

	
	
	@GET
	@Path("/game/roundWasDraw")
	public boolean getRoundWasDraw() {
		return model.getRoundWasDraw();
	}
	
	

	/**
	 * get playerName's deck number
	 * @return JSONString
	 * @throws IOException*/
	public String getPlayerDeck(@QueryParam("playerName") String playerName) throws IOException{
		int numberOfDeck=0;
		while(playerName!=null) {
			for(int i=0;i<model.getActivePlayers().length;i++) {
				if(model.getActivePlayers()[i].getName().equals(playerName)) {
					numberOfDeck=model.getActivePlayers()[i].getDeck().size();
				}
			}
		}
		String numberOfPlayerDeckAsString=oWriter.writeValueAsString(numberOfDeck);
		return numberOfPlayerDeckAsString;
	}
	
	
	/**
	 * Get the number of cards in deck:integer.
	 * numberOfCardsInDeck[0]=human
	 * numberOfCardsInDeck[1]=ai1
	 * numberOfCardsInDeck[2]=ai2
	 * numberOfCardsInDeck[3]=ai3
	 * numberOfCardsInDeck[4]=ai4
	 * @Controller.js: 
	 * @returns JSONString[] type
	 * @throws IOException
	 */	
	@GET
	@Path("/game/numberOfCardsInDeck")
	public String getNumberOfCardsInDeck() throws IOException{
		List<Integer> numberOfCardsInDeck=new ArrayList<Integer>();
		
		int cardsInDeck;
		for(int i=0;i<model.getActivePlayers().length;i++) {
			if(model.getActivePlayers()[i].toString().contains("human")) {
				cardsInDeck=model.getActivePlayers()[i].getDeck().size();
				numberOfCardsInDeck.add(cardsInDeck);
			}else {//stores the number of ai players cards in deck
				cardsInDeck=model.getActivePlayers()[i].getDeck().size();
				numberOfCardsInDeck.add(cardsInDeck);
			}
		}
		String numberOfCardsInDeckAsString=oWriter.writeValueAsString(numberOfCardsInDeck);
		return numberOfCardsInDeckAsString;
	}
	
	
	@GET
	@Path("/game/roundWinningCards")
	public String getRoundWinningCard() {
		String roundWinningCard=model.getRoundWinningCard().getDescription();
		return roundWinningCard;
	}
	

	
	
	/**
	 * Get round winner's name or 'draw'
	 * @Controller.js: 
	 * @returns String type
	 * @throws IOException
	 */	
	@GET
	@Path("/game/getRoundWinner")
	public String getRoundWinner() throws IOException{
		String result = null;
		ArrayList<DataPlayer> roundWinningPlayers = model.getRoundWinningPlayers();
		if(roundWinningPlayers.size() == 1) {
			result = roundWinningPlayers.get(0).getName();
		} else {
			result = "draw";
		}
		return result;
	}
	
	
	/**
	 * Print description of starting stage of the round. 
	 * such as: "Round 1: Players had drawn their cards".
	 * @Controller.js:
	 * @param RoundNumber:int, CategoryChooser:String
	 * @return JSONString type
	 * @throws IOException
	 */	
	@GET
	@Path("/game/roundDescription1")
	public String getRoundDescription1(@QueryParam("RoundNumber") int RoundNumber,
			@QueryParam("CategoryChooser") String CategoryChooser) 
					throws IOException{
		String description;
		
		description=String.format("Round %d: Players had drawn their cards.", RoundNumber);
		if(CategoryChooser.equals("You")) {
		//		if(model.getCategoryChooser().equals("human")) {
			description+=" Waiting on "+CategoryChooser+" to select category.";
		}else {
			description+=" The Active Player is "+CategoryChooser;
		}
		
		return oWriter.writeValueAsString(description);
	}
	
	/**
	 * Print selected category of the round. 
	 * such as: "Round 1: You selected speed".
	 * @Controller.js:
	 * @param RoundNumber:int, CategoryChooser:String, RoundCategory:String
	 * @return JSONString type
	 * @throws IOException
	 */	
	@GET
	@Path("/game/roundDescription2")
	public String getRoundDescription2(@QueryParam("RoundNumber") int RoundNumber,
			@QueryParam("CategoryChooser") String CategoryChooser,
			@QueryParam("RoundCategory") String RoundCategory) 
					throws IOException{
		String description;
		
		description=String.format("Round %d: %s selected %s.", RoundNumber, CategoryChooser, RoundCategory);
		return oWriter.writeValueAsString(description);
	}
	
	
	/**
	 * Print winner of the round. 
	 * such as: "Round 1: Player You won this round. Common pile now has 5 cards".
	 * @Controller.js:
	 * @param RoundNumber:int, RoundWinner:String, NumberOfActivePlayer:int, IsDraw:boolean
	 * @return JSONString type
	 * @throws IOException
	 */	
	@GET
	@Path("/game/roundDescription3")
	public String getRoundDescription3(@QueryParam("RoundNumber") int RoundNumber,
			@QueryParam("RoundWinner") String RoundWinner,
			@QueryParam("NumberOfActivePlayer") int NumberOfActivePlayer,
			@QueryParam("IsDraw") boolean IsDraw)
					throws IOException{
		String description;

		description=String.format("Round %d: Player %s won this round.", RoundNumber, RoundWinner);
		
		//if the round was draw
		if(IsDraw) {
		//if(model.getRoundWasDraw()) {
			description=String.format("Round %d: This round was a Draw.", RoundNumber, NumberOfActivePlayer);
		}
		
		description+=" Common pile now has "+NumberOfActivePlayer+" cards.";
		
		return oWriter.writeValueAsString(description);
	}
	
	
	/**
	 * get game winner from DataPlayer
	 * @return game winner as JSONString
	 * @throws IOException
	 * */
	@GET
	@Path("/game/gameWinner")
	public String getGameWinner() throws IOException {
		String gameWinner=oWriter.writeValueAsString(model.getGameWinner());
		return gameWinner;
	}
	
	
	
	/**
	 * Get game end String
	 * @Controller.js:
	 * @return String type
	 */	
	@GET
	@Path("/game/result/gameEnd")
	public String getGameEnd(){		
		return "Game End";
	}	
	
	
	/**
	 * Print game winner
	 * @Controller.js:
	 * @param GameWinner:String
	 * @return JSONString type
	 * @throws IOException
	 */	
	@GET
	@Path("/game/result/winner")
	public String getWinnerOfTheGame(@QueryParam("GameWinner") String GameWinner) throws IOException{		
		return GameWinner+" won the game";
	}	
	
	
	
	/**
	 * Get game result
	 * includes:
	 * "Player scores
	 * you: score
	 * ai1: score
	 * ai2: score
	 * ai3: score
	 * ai4: score".
	 * @Controller.js:
	 * @return JSONString[] type
	 * @throws IOException
	 */	
	@GET
	@Path("/game/result/scores")
	public String getGameResult() throws IOException{		
		//Categories for the human player
		List<String> gameResult=new ArrayList<String>();
		
		gameResult.add("Player scores");
		for(int i=0;i<model.getAllPlayers().length;i++) {
			gameResult.add(String.format("%s : %d", model.getAllPlayers()[i].getName(), model.getAllPlayers()[i].getScore()));
		}
		
		String gameResultAsString=oWriter.writeValueAsString(gameResult);
		return gameResultAsString;
	}	
	
	
	
	/**
	 * Get game statistics
	 * @Controller.js:
	 * @return JSONString type
	 * number of games
	 * number of human wins
	 * number of ai wins
	 * average of draws
	 * longest game
	 * @throws IOException
	 */	
	@GET
	@Path("/stats/statistics")
	public String getStatistics() throws IOException{		
		List<String> statistics=new ArrayList<String>();
		
		statistics.add(model.getNumberOfGames()+"");
		statistics.add(model.getNumberOfHumanWins()+"");
		statistics.add(model.getNumberOfAIWins()+"");
		statistics.add(model.getAvgNumberOfDraws()+"");
		statistics.add(model.getLongestGame()+"");

		String statisticsAsString=oWriter.writeValueAsString(statistics);
		
		return statisticsAsString;
	}
	
	
//	@GET
//	@Path("/game/cardsInCommonPile")
//	public DataCard[] getCardsInCommonPile() {
//		return model.getCardsInCommonPile();
//	}
	
	


	
//	@GET
//	@Path("/game/allPlayers")
//	public DataPlayer[] getAllPlayers() {
//		return model.getAllPlayers();
//	}
	
	
	
//	@GET
//	@Path("/game/roundLastWinner")
//	public DataPlayer getRoundLastWinner() {
//		return model.getRoundLastWinner();
//	}
	
	

	
//	@GET
//	@Path("/game/roundWinningCardToString")
//	public String getRoundWinningCardToString(@QueryParam("category") String category) {
//		return model.getRoundWinningCardToString(category);
//	}
//	
//	
//	@GET
//	@Path("/game/roundWinningPlayers")
//	public ArrayList<DataPlayer> getRoundWinningPlayers() {
//		return model.getRoundWinningPlayers();
//	}
	
//	@GET
//	@Path("/helloJSONList")
//	/**
//	 * Here is an example of a simple REST get request that returns a String.
//	 * We also illustrate here how we can convert Java objects to JSON strings.
//	 * @return - List of words as JSON
//	 * @throws IOException
//	 */
//	public String helloJSONList() throws IOException {
//		
//		List<String> listOfWords = new ArrayList<String>();
//		listOfWords.add("Hello");
//		listOfWords.add("World!");
//		
//		// We can turn arbatory Java objects directly into JSON strings using
//		// Jackson seralization, assuming that the Java objects are not too complex.
//		String listAsJSONString = oWriter.writeValueAsString(listOfWords);
//		
//		return listAsJSONString;
//	}
//	
//	@GET
//	@Path("/helloWord")
//	/**
//	 * Here is an example of how to read parameters provided in an HTML Get request.
//	 * @param Word - A word
//	 * @return - A String
//	 * @throws IOException
//	 */
//	public String helloWord(@QueryParam("Word") String Word) throws IOException {
//		return "Hello "+Word;
//	}	

}
