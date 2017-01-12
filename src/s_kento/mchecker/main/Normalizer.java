package s_kento.mchecker.main;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
//正規化を担当
public class Normalizer {
	public void normalize(String filePath) throws IOException {
		//入力ファイルを文字列データに変換
		String source = Files.lines(Paths.get(filePath),
				Charset.forName("UTF-8")).collect(Collectors.joining(System.getProperty("line.separator")));
		//ASTの構築
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setBindingsRecovery(true);
		parser.setStatementsRecovery(true);
		parser.setResolveBindings(true);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		Map options=JavaCore.getOptions();
		parser.setCompilerOptions(options);
		parser.setEnvironment(Envs.getClassPath(), Envs.getSourcePath(), null, true);
		parser.setSource(source.toCharArray());
		parser.setUnitName("Target.java");
		CompilationUnit unit = (CompilationUnit) parser.createAST(new NullProgressMonitor());
		//System.out.println(unit.getAST().hasResolvedBindings());
		MyVisitor visitor = new MyVisitor(unit);
		unit.accept(visitor);
	}

}