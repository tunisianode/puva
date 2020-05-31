package chapter4.mvp;

public class Presenter {
	private static final int MAX_TRIES = 3;

	private Model model;
	private View view;
	private int tries;

	public void setModelAndView(Model model, View view) {
		this.model = model;
		this.view = view;
	}

	public void choose() {
		String word = model.choose();
		view.showNewWord(word);
		view.eraseMessage();
		tries = 0;
	}

	public void check(String lang1, String lang2) {
		lang2 = lang2.trim();
		if (lang2.length() == 0) {
			view.showNoInputMessage();
			return;
		}
		if (model.check(lang1, lang2)) {
			view.showOkayMessage();
		} else {
			tries++;
			if (tries < MAX_TRIES) {
				view.showContinuationErrorMessage(tries);
			} else {
				choose();
				view.showFinalErrorMessage(MAX_TRIES);
			}
		}
	}
}
