package chapter4.mvp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class Model {
	private HashMap<String, String> vocabulary;
	private String[] keyWords;

	public Model() {
		vocabulary = new HashMap<>();
		try {
			fillVocab();
		} catch (Exception e) {
			vocabulary.clear();
			simpleFillVocab();
		}
		keyWords = vocabulary.keySet().toArray(new String[0]);
	}

	public String choose() {
		int index = (int) (Math.random() * keyWords.length);
		return keyWords[index];
	}

	public boolean check(String lang1, String lang2) {
		return lang2.equals(vocabulary.get(lang1));
	}

	private void fillVocab() throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(getClass().getResource("german_english.txt").getFile()));

		String line;
		while ((line = in.readLine()) != null) {
			int index1;
			while ((index1 = line.indexOf("(")) != -1) {
				int index2 = line.indexOf(")", index1 + 1);
				line = line.substring(0, index1) + line.substring(index2 + 1);
			}
			int index3 = line.indexOf(",");
			String lang1;
			lang1 = line.substring(0, index3);
			lang1 = lang1.trim();

			int index4 = line.indexOf(";", index3 + 1);
			if (index4 == -1) {
				index4 = line.indexOf(",", index3 + 1);
			}
			String lang2;
			lang2 = line.substring(index3 + 1, index4);
			lang2 = lang2.trim();
			vocabulary.put(lang1, lang2);
		}

		in.close();
	}

	private void simpleFillVocab() {
		vocabulary.put("Hund", "dog");
		vocabulary.put("Katze", "cat");
		vocabulary.put("Modell", "model");
		vocabulary.put("Ansicht", "view");
		vocabulary.put("Presenter", "presenter");
		vocabulary.put("Tisch", "table");
		vocabulary.put("Stuhl", "chair");
		vocabulary.put("Licht", "light");
		vocabulary.put("Sonne", "sun");
		vocabulary.put("Welt", "world");
		vocabulary.put("Mond", "moon");
	}
}
