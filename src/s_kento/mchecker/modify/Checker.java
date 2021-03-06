package s_kento.mchecker.modify;

import java.util.ArrayList;
import java.util.List;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;
import s_kento.mchecker.normalize.Sentence;

//修正パターンを抽出
public class Checker extends DiffUtils{
	public List<Modification> checkModification(List<Sentence> original, List<Sentence> revised) {
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
		//printModifications(modifications);
		return modifications;
	}

	public List<String> toListString(List<Sentence> s) {
		List<String> st = new ArrayList<String>();
		for (Sentence sentence : s) {
			st.add(sentence.getSentence());
		}
		return st;
	}

	public void printModifications(List<Modification> modifications){
		for (Modification modification : modifications) {
			printModification(modification);
		}
	}

	public static void printModification(Modification modification){
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
