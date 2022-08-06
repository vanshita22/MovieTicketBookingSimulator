package MovieTicket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Theatre {
	
	public static final String BASE_PATH = "D:\\Movie";
	public static final String THEARTRE_SETTING = BASE_PATH + "\\Theatre-Setting";
	public static final String NOW_PLAYING = "now-playing.txt";
	public static final String THEATRE_LAYOUT = "theatre-layout.txt";
	public static final String USER = "user.txt";
	public static final String MOVIES = "movies.txt";
	public static final String BOOKING = "Booking.txt";

	public static void viewSeatingArragement(String folder, String fileName) throws IOException {
		System.out.println();
		File file = new File(folder, fileName);
		try {
			Scanner scn = new Scanner(file);
			char character = 'A';
			System.out.println("\t" + "1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20");
			System.out.println("");

			while (scn.hasNextLine()) {

				String data = scn.nextLine();
				if (data.charAt(0) != '-') {

					data = data.replace("", "  ").trim();
					System.out.println(character + "\t" + data + "\t" + character);
					character++;

				} else {
					if (character != 'A') {
						System.out.println("");
					}
					System.out.println(data);
					System.out.println("");
				}

			}
			scn.close();
			System.out.println("");
			System.out.println("\t" + "*  *  *  *  *  *  *  S  C  R  E  E  N  *  *  *  *  *  *  *");
		}

		catch (IOException e) {
			System.out.println("An error has occurred.");
			e.printStackTrace();

		}
	}

	public static void viewNowPlaying(String basePath, String nowPlaying) throws FileNotFoundException, IOException {
		new DisplaySetting().drawScreen("Now Playing");
		try (BufferedReader br = new BufferedReader(new FileReader(new File(basePath, nowPlaying)))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		}
	}

	public static void updateNowPlaying(String basePath, String nowPlaying) throws Exception {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter Movie Name: ");
		String movieName = input.readLine();
		System.out.println("Enter Star Cast: ");
		String starCast = input.readLine();
		System.out.println("Enter Director: ");
		String director = input.readLine();
		System.out.println("Enter Music: ");
		String music = input.readLine();
		System.out.println("Enter Show Times: ");
		String showTime = input.readLine();
		updateNowPlaying(movieName, starCast, director, music, showTime);
	}

	public static void updateNowPlaying(String movieName, String starCast, String director, String music,
			String showTime) throws Exception {
		BufferedWriter movieWriter = new BufferedWriter(
				new FileWriter(new File(Theatre.THEARTRE_SETTING, Theatre.MOVIES), true));

		BufferedReader nowPlayingReader = new BufferedReader(
				new FileReader(new File(Theatre.THEARTRE_SETTING, Theatre.NOW_PLAYING)));
		String line, prevMovieName = "", prevStarCast = "", prevDirectors = "", prevMusic = "", prevShowTimes = "";
		int lineCount = 0;

		while ((line = nowPlayingReader.readLine()) != null) {
			switch (lineCount) {
			case 0:
				prevMovieName = line.split(":")[1];
				break;
			case 1:
				prevStarCast = line.split(":")[1];
				break;
			case 2:
				prevDirectors = line.split(":")[1];
				break;
			case 3:
				prevMusic = line.split(":")[1];
				break;
			case 4:
				prevShowTimes = line.split(":", 2)[1];
				break;
			}
			lineCount++;
		}

		String movieDetail = prevMovieName + "~" + prevStarCast + "~" + prevDirectors + "~" + prevMusic + "~"
				+ prevShowTimes + "\n";
		movieWriter.write(movieDetail);

		movieWriter.close();
		nowPlayingReader.close();

		/** Update now playing movie Detail */

		BufferedWriter nowPlayWriter = new BufferedWriter(
				new FileWriter(new File(Theatre.THEARTRE_SETTING, Theatre.NOW_PLAYING)));

		nowPlayWriter.write("Movie Name: " + movieName + "\n");
		nowPlayWriter.write("Start Cast: " + starCast + "\n");
		nowPlayWriter.write("Directors: " + director + "\n");
		nowPlayWriter.write("Music: " + music + "\n");
		nowPlayWriter.write("Show Time: " + showTime + "\n");

		nowPlayWriter.close();
	}

}
