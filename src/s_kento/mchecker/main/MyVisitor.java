package s_kento.mchecker.main;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;

//ASTノードをたどるvisitor
public class MyVisitor extends ASTVisitor {
	private CompilationUnit unit;
	List<Integer> ignore = Arrays.asList(ASTNode.TYPE_DECLARATION, ASTNode.METHOD_DECLARATION);

	public MyVisitor(CompilationUnit unit) {
		this.unit = unit;
	}

	/*
	 * @Override public boolean visit(SimpleName node) { if
	 * (!ignore.contains(node.getParent().getNodeType()))
	 * System.out.println(node.getIdentifier() + ", " +
	 * unit.getLineNumber(node.getStartPosition())); return super.visit(node); }
	 */

	@Override
	public boolean visit(PackageDeclaration node) {
		return false;
	}

	@Override
	public boolean visit(ImportDeclaration node) {
		return false;
	}

	@Override
	public boolean visit(SimpleType node) {
		return false;
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