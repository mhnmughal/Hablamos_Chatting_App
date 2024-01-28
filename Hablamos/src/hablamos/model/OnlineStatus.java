package hablamos.model;

public enum OnlineStatus {
	OFFLINE("Offline"),
	ONLINE("Online"), 
	BUSY("Busy"),
	    AWAY("Away"),
	    DND("Do Not Disturb"),
	    INVISIBLE("Invisible"),
	    CUSTOM("Custom");

	

    private String status;

    OnlineStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

	