package chapter7.ServletsJSF.src.chapter7.jsf.student;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ApplicationScoped
public class Options {
	private List<String> countryOptions;
	private List<String> languageOptions;

	public Options() {
		countryOptions = new ArrayList<>();
		countryOptions.add("D�nemark");
		countryOptions.add("Deutschland");
		countryOptions.add("Frankreich");
		countryOptions.add("Gro�britannien");
		countryOptions.add("Italien");
		countryOptions.add("�sterreich");
		countryOptions.add("Portugal");
		countryOptions.add("Schweiz");
		countryOptions.add("Spanien");

		languageOptions = new ArrayList<>();
		languageOptions.add("C++");
		languageOptions.add("C#");
		languageOptions.add("Delphi");
		languageOptions.add("Java");
		languageOptions.add("JavaScript");
		languageOptions.add("Python");
	}

	public List<String> getCountryOptions() {
		return countryOptions;
	}

	public List<String> getLanguageOptions() {
		return languageOptions;
	}
}
