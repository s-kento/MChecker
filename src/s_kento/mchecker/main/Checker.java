package s_kento.mchecker.main;

import java.util.ArrayList;
import java.util.List;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;

//修正パターンを抽出
public class Checker extends DiffUtils{
	public void checkModification(List<Sentence> original, List<Sentence> revised) {
		//diffをとり，patchに格納
		Patch patch = DiffUtils.diff(toListString(original), toListString(revised));
		List<Modification> modifications = new ArrayList<Modification>();
		//各変更をModificationクラスに格納
		for(Delta delta : patch.getDeltas()){
			Modification m = new Modification(delta.getType().toString(), delta.getOriginal().getLines(), delta.getRevised().getLines());
			m.setOriginalLine(original.get(delta.getOriginal().getPosition()).getLine());
			m.setRevisedLine(revised.get(delta.getRevised().getPosition()).getLine());
			modifications.add(m);
		}
		printModification(modifications);

	}

	public List<String> toListString(List<Sentence> s) {
		List<String> st = new ArrayList<String>();
		for (Sentence sentence : s) {
			st.add(sentence.getSentence());
		}
		return st;
	}

	public void printModification(List<Modification> modifications){
		for (Modification modification : modifications) {
			System.out.println(modification.getModification());
			System.out.println(String.format("[変更前(%d)行目]", modification.getOriginalLine()));
			for (Object line : modification.getOriginal()) {
				System.out.println(line);
			}

			System.out.println("　↓");

			System.out.println(String.format("[変更後(%d)行目]", modification.getRevisedLine()));
			for (Object line : modification.getRevised()) {
				System.out.println(line);
			}
			System.out.println();
		}
	}

}
