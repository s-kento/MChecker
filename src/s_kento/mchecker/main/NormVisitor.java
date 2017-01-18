package s_kento.mchecker.main;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.SimpleName;

//ASTノードをたどるvisitor
public class NormVisitor extends ASTVisitor {
	private CompilationUnit unit;

	public NormVisitor(CompilationUnit unit) {
		this.unit = unit;
	}

	@Override
	public boolean visit(SimpleName node) {
		IBinding ib = node.resolveBinding();
		if (ib != null && ib.getKind()==IBinding.VARIABLE) { //nodeが変数ならば
			node.setIdentifier("$v");
		}
		return true;
	}

}