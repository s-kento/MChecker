package s_kento.mchecker.main;

import java.io.IOException;
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;

import s_kento.mchecker.modify.Checker;
import s_kento.mchecker.modify.Localizer;
import s_kento.mchecker.modify.Modification;
import s_kento.mchecker.normalize.Normalizer;
import s_kento.mchecker.normalize.PrintVisitor;
import s_kento.mchecker.normalize.Sentence;

public class Main {
	public static void main(String[] args) throws IOException {
		long start=System.currentTimeMillis();
		Normalizer norm = new Normalizer();
		CompilationUnit unit1=norm.normalize(args[0]);
		CompilationUnit unit2 = norm.normalize(args[1]);
		PrintVisitor pvisitor1 = new PrintVisitor(unit1);
		PrintVisitor pvisitor2 = new PrintVisitor(unit2);
		unit1.accept(pvisitor1);
		unit2.accept(pvisitor2);
		List<Sentence> sentences1=pvisitor1.getSentences();
		List<Sentence> sentences2=pvisitor2.getSentences();
		/*for(Sentence sentence : sentences2){
			System.out.println(sentence.getSentence()+", 行番号："+sentence.getLine());
		}*/
		Checker ch = new Checker();
		List<Modification> modifications=ch.checkModification(sentences1, sentences2);
		Localizer localizer = new Localizer();
		localizer.localize(args[2], modifications);
		long end=System.currentTimeMillis();
		System.out.println((end-start)+"ms");
	}
}
