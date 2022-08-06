package MovieTicket;

public class DisplaySetting {
	private void drawLine() {
		for (int i = 1; i <= 80; i++) {
			System.out.print("-");
		}
		System.out.println();
	}

	private void printHeader(String header) {
		System.out.printf("%" + (40 + (header.length() / 2)) + "s\n", header);
	}

	public void welcomeScreen() {
		drawScreen("Movie Ticket Booking Simulator");
		printMainMenu();
	}

	public void printMainMenu() {
		System.out.println();

		System.out.println("Select any option, Press (0) to exit...");
		drawLine();
		System.out.println("1. View Now Playing.");
		System.out.println("2. Update Now Playing.");
		System.out.println("3. View Seating Arrangement");
		System.out.println("4. View Bookings");
		System.out.println("5. Book Tickets");
		System.out.println("6. View theatre history.");
		System.out.println("0. Exit");
	}

	public void clearScreen() {
		for (int i = 1; i <= 5; i++) {
			System.out.println();
		}
	}

	public void drawMovieHistory() {
		drawScreen("Movie Ticket History");
		System.out.println();
		drawLine();
		System.out.printf("%s\t%s\t%s\t%s\t\t%s\n", "Movie Name", "Star Cast", "Director", "Music", "Show Timing");
		drawLine();
	}

	public void drawScreen(String string) {
		clearScreen();
		drawLine();
		printHeader(string);
		drawLine();
	}

}
