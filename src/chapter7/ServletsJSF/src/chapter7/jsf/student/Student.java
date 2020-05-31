package chapter7.ServletsJSF.src.chapter7.jsf.student;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class Student {
	private String firstName;
	private String lastName;
	private String bornInCountry;
	private String livingInCountry;
	private String progLanguage;
	private String[] otherProgLanguages;

	public Student() {
		// Preselection
//        firstName = "Vorname";
//        lastName = "Nachname";
//        bornInCountry = "Deutschland";
//        livingInCountry = "Deutschland";
//        progLanguage = "Java";
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		System.out.println();
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBornInCountry() {
		return bornInCountry;
	}

	public void setBornInCountry(String bornInCountry) {
		this.bornInCountry = bornInCountry;
	}

	public String getLivingInCountry() {
		return livingInCountry;
	}

	public void setLivingInCountry(String livingInCountry) {
		this.livingInCountry = livingInCountry;
	}

	public String getProgLanguage() {
		return progLanguage;
	}

	public void setProgLanguage(String progLanguage) {
		this.progLanguage = progLanguage;
	}

	public String[] getOtherProgLanguages() {
		return otherProgLanguages;
	}

	public void setOtherProgLanguages(String[] otherProgLanguages) {
		this.otherProgLanguages = otherProgLanguages;
	}

	public String handleRequest() {
		if (firstName.equals("Rainer") && lastName.equals("Oechsle")) {
			return "student-response-special";
		} else {
			return "student-response";
		}
	}
}
