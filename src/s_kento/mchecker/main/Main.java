package s_kento.mchecker.main;

import java.io.IOException;
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;

public class Main {
	public static void main(String[] args) throws IOException {
		Normalizer norm = new Normalizer();
		CompilationUnit unit=norm.normalize(args[0]);

		PrintVisitor pvisitor = new PrintVisitor(unit);
		unit.accept(pvisitor);
		List<Sentence> sentences=pvisitor.getSentences();
		for(Sentence sentence : sentences){
			System.out.println(sentence.getSentence()+", 行番号："+sentence.getLine());
		}
	}
}
