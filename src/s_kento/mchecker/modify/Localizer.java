package s_kento.mchecker.modify;

import java.io.IOException;
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;

import s_kento.mchecker.normalize.Normalizer;
import s_kento.mchecker.normalize.PrintVisitor;
import s_kento.mchecker.normalize.Sentence;

//修正箇所を特定
public class Localizer {

	public void localize(String filePath, List<Modification> modifications) throws IOException {
		List<Sentence> sentences = makeSentences(filePath);
		for (Modification m : modifications) {
			if (m.getModification().equals("INSERT")) {
				insertPattern(sentences, m);
			} else if (m.getModification().equals("CHANGE")) {
				changeOrdeletePattern(sentences, m);
			} else if (m.getModification().equals("DELETE")) {
				changeOrdeletePattern(sentences, m);
			}
		}
	}

	public List<Sentence> makeSentences(String filePath) throws IOException {
		Normalizer norm = new Normalizer();
		CompilationUnit unit = norm.normalize(filePath);
		PrintVisitor pvisitor = new PrintVisitor(unit);
		unit.accept(pvisitor);
		List<Sentence> sfile = pvisitor.getSentences();

		return sfile;
	}

	public void insertPattern(List<Sentence> sentences, Modification m) {
		System.out.println("insertだよ．未実装だよ");
	}

	public void changeOrdeletePattern(List<Sentence> sentences, Modification m) {
		boolean match = true;
		for (int i = 0; i < sentences.size(); i++) {
			match = true;
			if (sentences.get(i).getHashSentence().equals(Modification.hashCode((String) m.getOriginal().get(0)))) {// 修正前コードの一行目とマッチしたら
				if (sentences.size() - i >= m.getOriginal().size()) {
					for (int j = 0; j < m.getOriginal().size(); j++) {
						if (!sentences.get(i + j).getHashSentence()
								.equals(Modification.hashCode((String) m.getOriginal().get(j)))) {
							match = false;
							break;
						}
					}
					if (match) {
						m.setOriginalLine(sentences.get(i).getLine());
						m.setRevisedLine(sentences.get(i).getLine());
						Checker.printModification(m);
						i += m.getOriginal().size() - 1;
					}
				}
			}
		}
	}
}
