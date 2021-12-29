package beans;

public class Demande {
	// ATTRIBUTS
	private int idRequest;
	private int idUser;
	private int idSource;
	private int idManagerMaint;
	private String state;
	private String description;
	
	// REQUETES
	public int getIdRequest() {
		return idRequest;
	}
	public int getIdUser() {
		return idUser;
	}
	public int getIdSource() {
		return idSource;
	}
	public int getIdManagerMaint() {
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
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public void setIdSource(int idSource) {
		this.idSource = idSource;
	}
	public void setIdManagerMaint(int idManagerMaint) {
		this.idManagerMaint = idManagerMaint;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}