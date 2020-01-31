package commandline;

import toptrumps.Controller;
import toptrumps.DataGame;
import toptrumps.ViewCLI;

/**
 * Top Trumps command line application
 */
public class TopTrumpsCLIApplication {

	/**
	 * This main method is called by TopTrumps.java when the user specifies that they want to run in
	 * command line mode. The contents of args[0] is whether we should write game logs to a file.
	 * @param args
	 */
	public static void main(String[] args) {

		boolean writeGameLogsToFile = false; // Should we write game logs to file?
		if (args[0].equalsIgnoreCase("true")) writeGameLogsToFile=true; // Command line selection

		// State
		boolean userWantsToQuit = false; // flag to check whether the user wants to quit the application

		// Loop until the user wants to exit the game
		while (!userWantsToQuit) {

			// ----------------------------------------------------
			// Add your game logic here based on the requirements
			// ----------------------------------------------------

			DataGame model = DataGame.resetAndGetInstance(4);
			ViewCLI view = new ViewCLI(model); // we need to pass in view here
			Controller controller = new Controller(model, view, writeGameLogsToFile);
			
			int userChoice = controller.startGame();
			
			if (userChoice == 1) {
				userWantsToQuit=true;
			}

			//			userWantsToQuit=true; // use this when the user wants to exit the game

		}


	}

}
