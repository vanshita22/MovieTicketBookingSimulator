package MovieTicket;

import MovieTicket.InvalidSeatSelectionException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import MovieTicket.Booking;
import MovieTicket.User;

public class App {
	public static void main(String aregs[]) throws IOException {

		DisplaySetting ds = new DisplaySetting();

		/**
		 * Draw Welcome Screen
		 */
		ds.drawScreen("Movie Ticket Booking Simulator");

		/**
		 * Authenticate user
		 */
		new User().authenticate();

		/**
		 * Creating BufferedReader object for user input
		 */
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			/**
			 * Choice Selection
			 */
			ds.welcomeScreen();

			System.out.print("Enter your choice, say(0-6): ");
			int choice = Integer.parseInt(br.readLine());

			switch (choice) {
			case 1:
				Theatre.viewNowPlaying(Theatre.THEARTRE_SETTING, Theatre.NOW_PLAYING);
				break;
			case 2:
				try {
					Theatre.updateNowPlaying(Theatre.THEARTRE_SETTING, Theatre.NOW_PLAYING);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				break;
			case 3:
				new DisplaySetting().drawScreen("View Seating Arrangement");
				Theatre.viewSeatingArragement(Theatre.THEARTRE_SETTING, Theatre.THEATRE_LAYOUT);
				break;
			case 4:
				try {
					new Booking().viewBooking();
				} catch (IOException | InvalidSeatSelectionException e2) {
					e2.printStackTrace();
					System.out.println(e2.getLocalizedMessage());
				}
				break;
			case 5:
				try {
					new Booking().getBookingInfo();
				} catch (IOException | InvalidSeatSelectionException e) {
					e.printStackTrace();
					System.out.println(e.getLocalizedMessage());
				}
				break;
			case 6:
				try {
					movieList();
				} catch (Exception e1) {
					e1.printStackTrace();
					System.out.println(e1.getLocalizedMessage());
				}
				break;
			case 0:
				System.exit(0);
				break;
			}

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public static void movieList() throws Exception {
		new DisplaySetting().drawMovieHistory();
		BufferedReader br = new BufferedReader(new FileReader(new File(Theatre.THEARTRE_SETTING, Theatre.MOVIES)));
		String line;
		while ((line = br.readLine()) != null) {
			String[] movieDetail = line.split("~");
			for (String detail : movieDetail) {
				System.out.printf("%s\t\t", detail.trim());
			}
			System.out.println();
		}

		br.close();
	}


}
