package commandline.view;

import java.io.BufferedWriter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

import main.model.*;

/**
 * TestLog- creates a log of data created when model updates
 * 
 * @author Team TRY-CATCH - Anne-Marie Gill 2431989G
 */

public class TestLog {
	// class attributes
	private DataGame model;
	private String outF = "testlog.txt";
	private String header = "Description ";

	// constructor sets model for test log and generates header from it
	public TestLog(DataGame model) {
		this.model = model;
		for (int i = 0; i < model.CATEGORYNAMES.length; i++) {
			header += model.CATEGORYNAMES[i] + " ";
		}
	}

	/**
	 * Writes initial unshuffled deck contents to testlog
	 */
	public void writeDeckContents() {

		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(outF);
			bw = new BufferedWriter(fw);
			bw.write("Initial Unshuffled Deck");
			bw.newLine();
			bw.newLine();
			bw.write(header);
			bw.newLine();
			bw.newLine();
			// loop through initial deck to write each member to test log
			for (int i = 0; i < model.getInitialUnshuffledDeck().length; i++) {

				String space = " ";
				String description = model.getInitialUnshuffledDeck()[i].getDescription();
				String size = String.valueOf(model.getInitialUnshuffledDeck()[i].getCategory1());
				String speed = String.valueOf(model.getInitialUnshuffledDeck()[i].getCategory2());
				String range = String.valueOf(model.getInitialUnshuffledDeck()[i].getCategory3());
				String firepower = String.valueOf(model.getInitialUnshuffledDeck()[i].getCategory4());
				String cargo = String.valueOf(model.getInitialUnshuffledDeck()[i].getCategory5());
				bw.write(description);
				bw.write(space);
				bw.write(size);
				bw.write(space);
				bw.write(speed);
				bw.write(space);
				bw.write(range);
				bw.write(space);
				bw.write(firepower);
				bw.write(space);
				bw.write(cargo);
				bw.newLine();
				bw.newLine();
			}
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Writes active player to the test log
	 */
	public void writeActivePlayer() {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(outF, true);
			bw = new BufferedWriter(fw);
			String separator = "------------------------------------------------------------------------";
			bw.write(separator);
			bw.newLine();
			bw.newLine();
			bw.write("The active player is " + model.getCategoryChooser().getName() + "\n");
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * Writes number of cards in each players deck to test log
	 */
	public void writeNumCardsInDeck() {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(outF, true);
			bw = new BufferedWriter(fw);
			String separator = "------------------------------------------------------------------------";
			bw.write(separator);
			bw.newLine();
			// loop through active players to write number of cards in deck to testlog
			for (int i = 0; i < model.getActivePlayers().length; i++) {
				if (model.getActivePlayers()[i].getTypeAsString().equals("human")) {
					bw.write(model.getActivePlayers()[i].getName() + " have "
							+ model.getActivePlayers()[i].getNumberOfCardsInDeck() + " cards in your deck. \n");
				}
				if (model.getActivePlayers()[i].getTypeAsString().equals("ai")) {
					bw.write(model.getActivePlayers()[i].getName() + " has "
							+ model.getActivePlayers()[i].getNumberOfCardsInDeck() + " cards. \n");
				}

			}
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Writes shuffled deck contents to test log
	 */
	public void writeShuffledDeckContents() {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(outF, true);
			bw = new BufferedWriter(fw);
			String separator = "------------------------------------------------------------------------";
			bw.write(separator);
			bw.newLine();
			bw.newLine();
			bw.write("Shuffled Deck");
			bw.newLine();
			bw.newLine();
			bw.write(header);
			bw.newLine();
			bw.newLine();
			// loop through shuffled deck to write each member to test log
			for (int i = 0; i < model.getInitialShuffledDeck().length; i++) {

				String space = " ";
				String description = model.getInitialShuffledDeck()[i].getDescription();
				String size = String.valueOf(model.getInitialShuffledDeck()[i].getCategory1());
				String speed = String.valueOf(model.getInitialShuffledDeck()[i].getCategory2());
				String range = String.valueOf(model.getInitialShuffledDeck()[i].getCategory3());
				String firepower = String.valueOf(model.getInitialShuffledDeck()[i].getCategory4());
				String cargo = String.valueOf(model.getInitialShuffledDeck()[i].getCategory5());
				bw.write(description);
				bw.write(space);
				bw.write(size);
				bw.write(space);
				bw.write(speed);
				bw.write(space);
				bw.write(range);
				bw.write(space);
				bw.write(firepower);
				bw.write(space);
				bw.write(cargo);
				bw.newLine();
				bw.newLine();
			}
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * Writes round number to test log
	 */
	public void writeRoundNumber() {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(outF, true);
			bw = new BufferedWriter(fw);
			String separator = "------------------------------------------------------------------------";
			bw.write(separator);
			bw.newLine();
			bw.newLine();
			String roundNumber = String.valueOf(model.getRound().getRoundNumber());
			bw.write("Round: " + roundNumber + "\n");
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Writes each player deck to test log
	 */

	public void writePlayerDecks() {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(outF, true);
			bw = new BufferedWriter(fw);
			String separator = "------------------------------------------------------------------------";
			bw.write(separator);
			bw.newLine();
			bw.newLine();
			bw.write("Player Decks");
			bw.newLine();
			bw.newLine();

			for (int j = 0; j < model.getActivePlayers().length; j++) {
				String player = model.getActivePlayers()[j].getName();
				bw.write(player);
				bw.newLine();
				bw.newLine();
				bw.write(header);
				bw.newLine();
				bw.newLine();
				// write deck of each active player
				for (int i = 0; i < model.getActivePlayers()[j].getDeck().size(); i++) {
					String space = " ";
					String description = model.getActivePlayers()[j].getDeck().get(i).getDescription();
					String size = String.valueOf(model.getActivePlayers()[j].getDeck().get(i).getCategory1());
					String speed = String.valueOf(model.getActivePlayers()[j].getDeck().get(i).getCategory2());
					String range = String.valueOf(model.getActivePlayers()[j].getDeck().get(i).getCategory3());
					String firepower = String.valueOf(model.getActivePlayers()[j].getDeck().get(i).getCategory4());
					String cargo = String.valueOf(model.getActivePlayers()[j].getDeck().get(i).getCategory5());
					bw.write(description);
					bw.write(space);
					bw.write(size);
					bw.write(space);
					bw.write(speed);
					bw.write(space);
					bw.write(range);
					bw.write(space);
					bw.write(firepower);
					bw.write(space);
					bw.write(cargo);
					bw.newLine();
					bw.newLine();
				}
				if (j != model.getActivePlayers().length - 1) {
					bw.write("----------------------------");
				}
				bw.newLine();

			}

			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Writes each initial player deck to test log
	 */

	public void writePlayerDecksInitial() {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(outF, true);
			bw = new BufferedWriter(fw);
			String separator = "------------------------------------------------------------------------";
			bw.write(separator);
			bw.newLine();
			bw.newLine();
			bw.write("Initial Player Decks");
			bw.newLine();
			bw.newLine();

			for (int j = 0; j < model.getActivePlayers().length; j++) {
				String player = model.getActivePlayers()[j].getName();
				bw.write(player);
				bw.newLine();
				bw.newLine();
				bw.write(header);
				bw.newLine();
				bw.newLine();
				for (int i = 0; i < model.getActivePlayers()[j].getDeck().size(); i++) {
					String space = " ";
					String description = model.getActivePlayers()[j].getDeck().get(i).getDescription();
					String size = String.valueOf(model.getActivePlayers()[j].getDeck().get(i).getCategory1());
					String speed = String.valueOf(model.getActivePlayers()[j].getDeck().get(i).getCategory2());
					String range = String.valueOf(model.getActivePlayers()[j].getDeck().get(i).getCategory3());
					String firepower = String.valueOf(model.getActivePlayers()[j].getDeck().get(i).getCategory4());
					String cargo = String.valueOf(model.getActivePlayers()[j].getDeck().get(i).getCategory5());
					bw.write(description);
					bw.write(space);
					bw.write(size);
					bw.write(space);
					bw.write(speed);
					bw.write(space);
					bw.write(range);
					bw.write(space);
					bw.write(firepower);
					bw.write(space);
					bw.write(cargo);
					bw.newLine();
					bw.newLine();
				}
				if (j != model.getActivePlayers().length - 1) {
					bw.write("----------------------------");
				}
				bw.newLine();

			}

			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Writes contents of communal pile to test log
	 */
	public void writeCommunalPile() {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(outF, true);
			bw = new BufferedWriter(fw);
			String separator = "------------------------------------------------------------------------";
			bw.write(separator);
			bw.newLine();
			bw.write("Communal Pile Contents \n");
			if (model.getCardsInCommonPile().length == 0) {
				bw.write("The communal pile is empty \n");
				bw.newLine();
			}
			if (model.getCardsInCommonPile().length > 0) {
				bw.newLine();
				bw.write("There was a draw, the communal pile now contains");
				bw.newLine();
				bw.write(header);
				bw.newLine();
				bw.newLine();

				for (int i = 0; i < model.getCardsInCommonPile().length; i++) {

					String space = " ";
					String description = model.getCardsInCommonPile()[i].getDescription();
					String size = String.valueOf(model.getCardsInCommonPile()[i].getCategory1());
					String speed = String.valueOf(model.getCardsInCommonPile()[i].getCategory2());
					String range = String.valueOf(model.getCardsInCommonPile()[i].getCategory3());
					String firepower = String.valueOf(model.getCardsInCommonPile()[i].getCategory4());
					String cargo = String.valueOf(model.getCardsInCommonPile()[i].getCategory5());
					bw.write(description);
					bw.write(space);
					bw.write(size);
					bw.write(space);
					bw.write(speed);
					bw.write(space);
					bw.write(range);
					bw.write(space);
					bw.write(firepower);
					bw.write(space);
					bw.write(cargo);
					bw.newLine();
					bw.newLine();
				}
			}

			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * writes current cards in play to test log
	 */
	public void writeCardsInPlay() {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(outF, true);
			bw = new BufferedWriter(fw);
			String separator = "------------------------------------------------------------------------";
			bw.write(separator);
			bw.newLine();
			bw.newLine();
			bw.write("Players' Top Cards");
			bw.newLine();
			bw.newLine();
			// loop through active players to get cards in play
			for (int j = 0; j < model.getActivePlayers().length; j++) {
				String player = model.getActivePlayers()[j].getName();
				bw.write(player);
				bw.newLine();
				bw.newLine();
				bw.write(header);
				bw.newLine();
				bw.newLine();

				String space = " ";
				String description = model.getActivePlayers()[j].getDeck().get(0).getDescription();
				String size = String.valueOf(model.getActivePlayers()[j].getDeck().get(0).getCategory1());
				String speed = String.valueOf(model.getActivePlayers()[j].getDeck().get(0).getCategory2());
				String range = String.valueOf(model.getActivePlayers()[j].getDeck().get(0).getCategory3());
				String firepower = String.valueOf(model.getActivePlayers()[j].getDeck().get(0).getCategory4());
				String cargo = String.valueOf(model.getActivePlayers()[j].getDeck().get(0).getCategory5());
				bw.write(description);
				bw.write(space);
				bw.write(size);
				bw.write(space);
				bw.write(speed);
				bw.write(space);
				bw.write(range);
				bw.write(space);
				bw.write(firepower);
				bw.write(space);
				bw.write(cargo);
				bw.newLine();
				bw.newLine();
				if (j != model.getActivePlayers().length - 1) {
					bw.write("----------------------------");
				}

				bw.newLine();

			}

			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Writes end game scores to test log
	 */
	public void writeGameStats() {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(outF, true);
			bw = new BufferedWriter(fw);
			String separator = "------------------------------------------------------------------------";
			bw.write(separator);
			bw.newLine();
			bw.write("Player scores");
			bw.newLine();
			// write scores for all players
			for (int i = 0; i < model.getAllPlayers().length; i++) {
				bw.write(model.getAllPlayers()[i].getName() + " :" + model.getAllPlayers()[i].getScore() + "\n");
			}

			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Writes winner of round to test log
	 */
	public void writeRoundWinner() {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(outF, true);
			bw = new BufferedWriter(fw);
			String separator = "------------------------------------------------------------------------";
			bw.write(separator);
			bw.newLine();
			bw.write("Round Winner");
			bw.newLine();

			if (!model.getRound().getRoundWasDraw()) {
				String roundWinner = "The winner of the round was ";

				roundWinner += model.getRound().getRoundWinningPlayers().get(0).getName() + " . \n";

				bw.write(roundWinner);
			}

			if (model.getRound().getRoundWasDraw()) {
				String roundWinner = "There was a draw \n";

				bw.write(roundWinner);
			}

			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Writes category selection and who selected to test log
	 */
	public void writeCategorySelected() {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(outF, true);
			bw = new BufferedWriter(fw);
			String separator = "------------------------------------------------------------------------";
			bw.write(separator);
			bw.newLine();
			bw.newLine();
			bw.write("Category Selected");
			bw.newLine();
			bw.newLine();
			String catSelected = "";

			catSelected = model.getRound().getRoundActivePlayer().getName() + " selected "
					+ model.getRound().getRoundCategory() + "\n";

			bw.write(catSelected);
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Writes the winner of the game to the test log
	 */
	public void writeGameWinner() {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(outF, true);
			bw = new BufferedWriter(fw);
			String separator = "------------------------------------------------------------------------";
			bw.write(separator);
			bw.newLine();
			bw.newLine();
			bw.write("Game Winner");
			bw.newLine();
			bw.newLine();
			String gameWinner = model.getGameWinner().getName();
			bw.write(gameWinner + "\n");
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Writes round winning card to test log
	 */
	public void writeRoundWinningCard() {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(outF, true);
			bw = new BufferedWriter(fw);
			String separator = "------------------------------------------------------------------------";
			bw.write(separator);
			bw.newLine();
			bw.newLine();
			bw.write("Round Winning Card");
			bw.newLine();
			bw.newLine();
			// check if round was draw
			if (model.getRound().getRoundWasDraw()) {
				bw.write("There was no winning card this round \n");
			}
			if (!model.getRound().getRoundWasDraw()) {
				String space = " ";
				String header = "Description Size Speed Range Firepower Cargo";
				bw.write(header);
				bw.newLine();
				bw.newLine();
				bw.write(model.getRound().getRoundWinningCard().getDescription());
				bw.write(space);
				bw.write(String.valueOf(model.getRound().getRoundWinningCard().getCategory1()));
				bw.write(space);
				bw.write(String.valueOf(model.getRound().getRoundWinningCard().getCategory2()));
				bw.write(space);
				bw.write(String.valueOf(model.getRound().getRoundWinningCard().getCategory3()));
				bw.write(space);
				bw.write(String.valueOf(model.getRound().getRoundWinningCard().getCategory4()));
				bw.write(space);
				bw.write(String.valueOf(model.getRound().getRoundWinningCard().getCategory5()));
				bw.newLine();
				bw.newLine();
			}

			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
