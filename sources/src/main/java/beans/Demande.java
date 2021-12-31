package beans;

public class Demande {
	// ATTRIBUTS
	private int idRequest;
	private long idUser;
	private long idSource;
	private long idManagerMaint;
	private String state;
	private String description;
	
	// REQUETES
	public int getIdRequest() {
		return idRequest;
	}
	public long getIdUser() {
		return idUser;
	}
	public long getIdSource() {
		return idSource;
	}
	public long getIdManagerMaint() {
		return idManagerMaint;
	}
	public String getState() {
		return state;
	}
	public String getDescription() {
		return description;
	}
	
	// REQUETES
	public void setIdRequest(int idRequest) {
		this.idRequest = idRequest;
	}
	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}
	public void setIdSource(long idSource) {
		this.idSource = idSource;
	}
	public void setIdManagerMaint(long idManagerMaint) {
		this.idManagerMaint = idManagerMaint;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}