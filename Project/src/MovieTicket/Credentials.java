package MovieTicket;

public class Credentials {
	
	private final String userName;

	private final String password;

	public Credentials(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Credentials) {
			Credentials credentials = (Credentials) obj;
			return this.userName.equals(credentials.getUserName()) && this.password.equals(credentials.getPassword());
		}
		return false;
	}

}
