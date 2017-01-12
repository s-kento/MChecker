package s_kento.mchecker.main;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.SimpleName;

//ASTノードをたどるvisitor
public class MyVisitor extends ASTVisitor {
	private CompilationUnit unit;

	public MyVisitor(CompilationUnit unit) {
		this.unit = unit;
	}



	@Override
	public boolean visit(SimpleName node) {
		IBinding ib = node.resolveBinding();
		if (ib == null) {
			System.out.print("nullだよ      ");
			System.out.println(node.getIdentifier() + ", " + unit.getLineNumber(node.getStartPosition()));
		} else if (ib.getKind() == IBinding.VARIABLE) {
			System.out.println(node.getIdentifier() + ", " + unit.getLineNumber(node.getStartPosition()));
		}
		return true;
	}
}