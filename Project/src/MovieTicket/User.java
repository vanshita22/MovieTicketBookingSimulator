package MovieTicket;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import MovieTicket.Credentials;
import MovieTicket.Theatre;

public class User {
	public void authenticate() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("Enter Username: ");
		String userName = br.readLine();

		System.out.print("Enter Password: ");
		String password = br.readLine();
		
		Credentials inputCredentials = new Credentials(userName, password);

		Credentials fileCredentials = readCredentials(Theatre.THEARTRE_SETTING, Theatre.USER);

		if (!inputCredentials.equals(fileCredentials)) {
			System.out.println("Invalid username or password.\n");

			authenticate();
		}

	}

	/**
	 * Read file and return credentials
	 * 
	 * @param folderPath
	 * @param fileName
	 * 
	 * @return Credentials
	 */
	private static Credentials readCredentials(String folderPath, String fileName) {
		try (FileReader fileReader = new FileReader(new File(folderPath, fileName));
				BufferedReader bufferedReader = new BufferedReader(fileReader)) {
			return new Credentials(bufferedReader.readLine(), bufferedReader.readLine());
		} catch (FileNotFoundException e) {
			System.out.println("File not found at location : " + folderPath + " and file name : " + fileName);
		} catch (IOException e) {
			System.out.println("File is not able to read at location : " + folderPath + " and file name : " + fileName);
		}
		System.exit(0);
		return null;
	}

}
