package MovieTicket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import MovieTicket.DisplaySetting;
import MovieTicket.Theatre;
import MovieTicket.InvalidSeatSelectionException;

public class Booking {
	private static SimpleDateFormat simpleDateFormat;
	private static String movieName;
	private static String starCast;
	private static String directors;
	private static String music;
	private static String[] showTimes;

	static {
		simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		simpleDateFormat.setLenient(true);
		File nowPlayingFile = new File(Theatre.THEARTRE_SETTING, Theatre.NOW_PLAYING);
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(nowPlayingFile))) {
			String line;
			while ((line = bufferedReader.readLine()) != null) {

				String[] inputs = line.split(":", 2);
				switch (inputs[0]) {

				case "Movie Name":
					movieName = inputs[1];
					break;

				case "Star Cast":
					starCast = inputs[1];
					break;

				case "Directors":
					directors = inputs[1];
					break;

				case "Music":
					music = inputs[1];
					break;

				case "Show Time":
					showTimes = inputs[1].split(",");
					break;

				}
			}
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	public void getBookingInfo() throws FileNotFoundException, IOException, InvalidSeatSelectionException {

		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		File file = getBookingFilePath();
		Set<String> seats = getSeatNo(input);
		File copyBooking = new File(file.getParent(), "Booking.1.txt");
		bookTicket(file, copyBooking, seats);
		System.out.println("Booked Successfully");
		file.delete();
		copyBooking.renameTo(file);
	}

	private void bookTicket(File sourceFile, File destFile, Set<String> seats)
			throws FileNotFoundException, IOException, InvalidSeatSelectionException {
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(sourceFile));
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(destFile))) {

			String line = null;
			int lineNumber = -1;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.contains("Price")) {
					bufferedWriter.write(line + "\n");
				} else {
					lineNumber++;
					line = updateSeats((char) (65 + lineNumber), line, seats);
					bufferedWriter.write(line + "\n");
				}
			}
		}
	}

	private String updateSeats(char row, String line, Set<String> seats) throws InvalidSeatSelectionException {
		char[] ch = line.toCharArray();
		for (String seat : seats) {
			if (seat.startsWith(row + "")) {
				int no = Integer.parseInt(seat.substring(1));
				if (no < 1 || no > ch.length) {
					throw new InvalidSeatSelectionException("Invalid Selection.");
				} else {
					for (int index = 0, i = 0; i < ch.length && index < ch.length; index++) {
						if (i == (no - 1)) {
							if (ch[i] == 'B') {
								throw new InvalidSeatSelectionException("Already Booked.");
							} else if (ch[index] == 'X') {
								ch[index] = 'B';
								i++;
							}
						} else if (ch[index] != '-') {
							i++;
						}
					}
				}
			}
		}
		return new String(ch);
	}

	private Set<String> getSeatNo(BufferedReader sc) throws IOException {
		System.out.print("Enter Seat Number: ");
		String seatNos = sc.readLine();
		try {
			return checkSeatSet(seatNos);
		} catch (InvalidSeatSelectionException e) {
			System.out.println(e.getLocalizedMessage());
			return getSeatNo(sc);
		}
	}

	private Set<String> checkSeatSet(String selectedSeats) throws InvalidSeatSelectionException {
		Set<String> seatSet = new HashSet<String>();
		if (selectedSeats.contains("-")) {
			String firstSeat = selectedSeats.split("-")[0];
			String lastSeat = selectedSeats.split("-")[1];
			if (firstSeat.charAt(0) != lastSeat.charAt(0)) {
				throw new InvalidSeatSelectionException("Please select seats in same row.");
			} else {
				int firstSelectedColumn = Integer.parseInt(firstSeat.substring(1));
				int lastSelectedColumn = Integer.parseInt(lastSeat.substring(1));
				if (firstSelectedColumn > lastSelectedColumn) {
					int temp = firstSelectedColumn;
					firstSelectedColumn = lastSelectedColumn;
					lastSelectedColumn = temp;
				}
				for (int i = firstSelectedColumn; i <= lastSelectedColumn; i++) {
					seatSet.add(firstSeat.charAt(0) + "" + i);
				}
			}
		} else {
			String[] selectedSeatsArray = selectedSeats.toUpperCase().split(",");

			for (String currentSeat : selectedSeatsArray) {
				seatSet.add(currentSeat);
			}
		}
		return seatSet;
	}

	private void copyFile(File sourceFile, File destFile) throws FileNotFoundException, IOException {
		destFile.getParentFile().mkdirs();
		try (BufferedReader source = new BufferedReader(new FileReader(sourceFile));
				BufferedWriter target = new BufferedWriter(new FileWriter(destFile))) {

			String line = null;
			while ((line = source.readLine()) != null) {
				target.write(line + "\n");
			}
		}
	}

	private Date getDate(BufferedReader bufferedReader) throws IOException {
		System.out.print("Enter Booking Date (DD/MM/YYYY):");
		try {
			String input = bufferedReader.readLine();
			return simpleDateFormat.parse(input);
		} catch (ParseException e) {
			e.printStackTrace();
			return getDate(bufferedReader);
		}
	}

	public void viewBooking() throws FileNotFoundException, IOException, InvalidSeatSelectionException {
		File bookingFilePath = getBookingFilePath();
		new DisplaySetting().drawScreen("View Bookings");
		Theatre.viewSeatingArragement(bookingFilePath.getParent(), Theatre.BOOKING);
	}

	private File getBookingFilePath() throws IOException {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		Date date = getDate(input);

		System.out.print("Select Show Timing (");
		for (int i = 0; i < showTimes.length; i++) {
			System.out.print((i + 1) + " -" + showTimes[i]);
			if (showTimes.length - 1 != i)
				System.out.print(", ");
		}
		System.out.println(")");

		System.out.print("Select Show Timing: ");
		int show = Integer.parseInt(input.readLine());

		String bookingFilePath = Theatre.BASE_PATH + File.separator + new SimpleDateFormat("dd-MM-yyyy").format(date)
				+ File.separator + movieName + File.separator + showTimes[show - 1].replace(":", "-") + File.separator
				+ "Booking.txt";

		File file = new File(bookingFilePath);
		if (!file.exists()) {
			copyFile(new File(Theatre.THEARTRE_SETTING, Theatre.THEATRE_LAYOUT), file);
		}
		return file;
	}

}
