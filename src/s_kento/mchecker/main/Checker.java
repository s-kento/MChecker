package s_kento.mchecker.main;

import java.util.ArrayList;
import java.util.List;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;

//修正パターンを抽出
public class Checker {
	public void checkModification(List<Sentence> s1, List<Sentence> s2) {
		Patch patch = DiffUtils.diff(toListString(s1), toListString(s2));
		List<Modification> modifications = new ArrayList<Modification>();
		for(Delta delta : patch.getDeltas()){
			Modification m = new Modification(delta.getType().toString(), delta.getOriginal().getLines(), delta.getRevised().getLines());
			modifications.add(m);
		}
		for (Delta delta : patch.getDeltas()) {
			System.out.println(delta.getType().toString());
			System.out.println(String.format("[変更前(%d)行目]", delta.getOriginal().getPosition() + 1));
			for (Object line : delta.getOriginal().getLines()) {
				System.out.println(line);
			}

			System.out.println("　↓");

			System.out.println(String.format("[変更後(%d)行目]", delta.getRevised().getPosition() + 1));
			for (Object line : delta.getRevised().getLines()) {
				System.out.println(line);
			}
			System.out.println();
		}
	}

	public List<String> toListString(List<Sentence> s) {
		List<String> st = new ArrayList<String>();
		for (Sentence sentence : s) {
			st.add(sentence.getSentence());
		}
		return st;
	}
}
