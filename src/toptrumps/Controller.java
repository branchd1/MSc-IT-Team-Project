package toptrumps;

import toptrumps.DataGame;
import toptrumps.DataPlayer;

import java.util.HashMap;
import java.util.Random;

/**
 * The controller update the Model and View according to user input
 */
public class Controller {
    private DataGame dataGame;
    private ViewCli viewCli;
    private DataPlayer humman;

    public Controller(DataGame dataGame, ViewCli viewCli) {
        this.dataGame = dataGame;
        this.viewCli = viewCli;
        humman = dataGame.getPlayers()[0];
    }

    public void startGame() {
        running();
    }

    public void running() {
        viewCli.chooseDisplay();
        while (viewCli.getStartChoice() == 2 || viewCli.getStartChoice() == 1) {
            if(viewCli.getStartChoice() == 2){
                dataGame.startGame();
                while (!dataGame.isGameOver()){
                    int roundnumber = dataGame.getRoundNumber();
                    viewCli.displayRound(roundnumber);
                    //Player Card
                    if(!humman.getDeck().isEmpty()){
                        viewCli.displayPlayerCard(humman.getDataCard());
                        viewCli.displayNumDeckCards(humman.getNumberOfCardsInDeck());
                    }

                    DataPlayer dataPlayer = dataGame.getLastRoundWinner();
                    if (dataPlayer == null) {
                        int n = new Random().nextInt(5);
                        dataPlayer = dataGame.getPlayers()[n];
                    }
                    //Get attributes and determine if randomness is required
                    int categoryChoice = -1;
                    if (dataPlayer.isHuman()) {
                        viewCli.displayCategorySelection();
                        categoryChoice = viewCli.getCategoryChoice();
                    } else {
                        categoryChoice = new Random().nextInt(5)+1;
                    }

                    //Compare
                    dataGame.playRound(DataCard.CATEGORYNAMES[categoryChoice-1]);

                    //Output result
                    if (dataGame.isLastRoundWasDraw()) {
                        viewCli.displayRoundResult(dataGame.getLastRoundWinner(),roundnumber,dataGame.getLastRoundWinningCards()[0],categoryChoice, dataGame.getNumberOfCardsInCommonPile());
                    } else {
                        viewCli.displayRoundResult(dataGame.getLastRoundWinner(),roundnumber,dataGame.getLastRoundWinningCards()[0],categoryChoice, 0);
                    }
                }
                viewCli.displayStats(dataGame);
            } else if(viewCli.getStartChoice() == 1){
                viewCli.displayStats(dataGame);
            }
            viewCli.chooseDisplay();
        }
        System.out.println("exit");
    }

}

