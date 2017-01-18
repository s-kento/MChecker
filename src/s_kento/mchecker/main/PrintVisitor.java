package s_kento.mchecker.main;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

public class PrintVisitor extends ASTVisitor {
	private CompilationUnit unit;

	public PrintVisitor(CompilationUnit unit) {
		this.unit = unit;
	}

	@Override
	public boolean visit(ExpressionStatement node) {
		Expression ex = node.getExpression();
		System.out.println(ex.toString() + ", 行番号：" + unit.getLineNumber(node.getStartPosition()));
		return true;
	}

	@Override
	public boolean visit(VariableDeclarationStatement node) {
		List<Modifier> modifiers = node.modifiers();
		Type type = node.getType();
		List<VariableDeclarationFragment> frags = node.fragments();
		for (Modifier modifier : modifiers) {
			System.out.print(modifier.toString() + " ");
		}
		System.out.print(type.toString() + " ");
		for (VariableDeclarationFragment frag : frags) {
			System.out.print(frag + " ");
		}
		System.out.println(", 行番号: " + unit.getLineNumber(node.getStartPosition()));
		return false;
	}

	@Override
	public boolean visit(IfStatement node) {
		Expression ex = node.getExpression();
		System.out.println("if(" + ex.toString() + ")" + ", 行番号: " + unit.getLineNumber(node.getStartPosition()));
		return true;
	}

	@Override
	public boolean visit(ForStatement node) {
		List<Expression> initializers = node.initializers();
		Expression ex = node.getExpression();
		List<Expression> updaters = node.updaters();
		System.out.print("for(");
		for (Expression init : initializers) {
			System.out.print(init.toString());
		}
		System.out.print("; " + ex.toString() + "; ");
		for (Expression updater : updaters) {
			System.out.print(updater.toString());
		}
		System.out.println(")" + ", 行番号: " + unit.getLineNumber(node.getStartPosition()));

		return true;
	}

	@Override
	public boolean visit(EnhancedForStatement node) {
		SingleVariableDeclaration paramter = node.getParameter();
		Expression ex = node.getExpression();
		System.out.println("for(" + paramter.toString() + " : " + ex.toString() + ")" + ", 行番号: "
				+ unit.getLineNumber(node.getStartPosition()));
		return true;
	}

	@Override
	public boolean visit(ReturnStatement node) {
		Expression ex = node.getExpression();
		System.out.println("return " + ex.toString() + ", 行番号: " + unit.getLineNumber(node.getStartPosition()));
		return true;
	}

	@Override
	public boolean visit(FieldDeclaration node) {
		List<Modifier> modifiers = node.modifiers();
		Type type = node.getType();
		List<VariableDeclarationFragment> frags = node.fragments();
		for (Modifier modifier : modifiers) {
			System.out.print(modifier.toString() + " ");
		}
		System.out.print(type.toString() + " ");
		for (VariableDeclarationFragment frag : frags) {
			System.out.print(frag + " ");
		}
		System.out.println(", 行番号: " + unit.getLineNumber(node.getStartPosition()));
		return true;
	}

	@Override
	public boolean visit(PackageDeclaration node) {
		Name name = node.getName();
		System.out.println("package " + name + ", 行番号: " + unit.getLineNumber(node.getStartPosition()));
		return true;
	}

	@Override
	public boolean visit(ImportDeclaration node) {
		System.out.print("import ");
		if (node.isStatic()) {
			System.out.print("static ");
		}
		System.out.print(node.getName());
		if (node.isOnDemand()) {
			System.out.print(".*");
		}
		System.out.println(", 行番号: " + unit.getLineNumber(node.getStartPosition()));
		return true;
	}

	@Override
	public boolean visit(WhileStatement node) {
		Expression ex = node.getExpression();
		System.out.println("while( " + ex.toString() + ")" + ", 行番号: " + unit.getLineNumber(node.getStartPosition()));
		return true;
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		List<IExtendedModifier> modifiers = node.modifiers();
		Type type = node.getReturnType2();
		SimpleName name = node.getName();
		List<SingleVariableDeclaration> parameters = node.parameters();
		List<Type> exceptions = node.thrownExceptionTypes();
		for (int i=0;i<modifiers.size();i++) {
			if (modifiers.get(i).isModifier())
				System.out.print(modifiers.get(i).toString() + " ");
		}
		if (!node.isConstructor()) {
			System.out.print(type.toString() + " ");
		}
		System.out.print(name + "(");
		for (SingleVariableDeclaration parameter : parameters) {
			System.out.print(parameter.toString() + " " );
		}
		System.out.print(")");
		if(exceptions.size()>0)
			System.out.print("throws ");
		for(Type exception : exceptions){
			System.out.print(exception.toString()+" ");
		}
		System.out.println(", 行番号: " + unit.getLineNumber(node.getStartPosition()));
		return true;
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		List<Modifier> modifiers = node.modifiers();
		boolean inter = node.isInterface();
		Name name = node.getName();
		Type type = node.getSuperclassType();
		List<Type> interTypes = node.superInterfaceTypes();
		for (Modifier modifier : modifiers) {
			System.out.print(modifier.toString() + " ");
		}
		if (!inter) {
			System.out.print("class " + name);
		} else {
			System.out.print("interface " + name);
		}
		if (type != null) {
			System.out.print(" extends " + type.toString());
		}
		if (interTypes.size() != 0) {
			System.out.print(" implements ");
			for (Type interType : interTypes) {
				System.out.print(interType.toString());
			}
		}
		System.out.println();
		return true;
	}

	@Override
	public boolean visit(AssertStatement node){
		Expression ex1 = node.getExpression();
		Expression ex2 = node.getMessage();
		System.out.print("assert "+ex1.toString());
		if(ex2!=null){
			System.out.print(" : "+ex2.toString());
		}
		System.out.println( ", 行番号: " + unit.getLineNumber(node.getStartPosition()));
		return true;
	}

	@Override
	public boolean visit(BreakStatement node){
		SimpleName label = node.getLabel();
		System.out.print("break ");
		if(label!=null){
			System.out.print(label);
		}
		System.out.println();
		return true;
	}

	@Override
	public boolean visit(ContinueStatement node){
		SimpleName label = node.getLabel();
		System.out.println("continue ");
		if(label!=null){
			System.out.print(label);
		}
		return true;
	}

	@Override
	public boolean visit(DoStatement node){
		Statement body = node.getBody();
		Expression ex = node.getExpression();
		System.out.println("do");
		body.accept(this);
		System.out.println("while("+ex.toString()+")");
		return false;
	}

	@Override
	public boolean visit(LabeledStatement node){
		Statement body = node.getBody();
		SimpleName label = node.getLabel();
		System.out.println(label+" : ");
		body.accept(this);
		return false;
	}

	@Override
	public boolean visit(SwitchStatement node){
		Expression ex = node.getExpression();
		System.out.println("switch("+ex.toString()+")");
		return true;
	}

	@Override
	public boolean visit(SwitchCase node){
		Expression ex =node.getExpression();
		if(!node.isDefault()){
			System.out.println("case "+ex.toString()+" :");
		}
		else{
			System.out.println("default : ");
		}
		return true;
	}

	@Override
	public boolean visit(SynchronizedStatement node){
		Expression ex = node.getExpression();
		System.out.println("synchronized("+ex.toString()+")");
		return true;
	}

	@Override
	public boolean visit(TryStatement node){
		Block body = node.getBody();
		List<CatchClause> catches = node.catchClauses();
		Block fi = node.getFinally();
		System.out.println("try");
		body.accept(this);
		for(CatchClause c: catches){
			c.accept(this);
		}
		if(fi!=null){
			System.out.println("finally");
			fi.accept(this);
		}
		return false;
	}

	@Override
	public boolean visit(CatchClause node){
		Block body = node.getBody();
		SingleVariableDeclaration ex = node.getException();
		System.out.println("catch("+ex.toString()+")");
		body.accept(this);
		return false;
	}

	@Override
	public boolean visit(ThrowStatement node){
		Expression ex = node.getExpression();
		System.out.println("throw "+ex.toString());
		return true;
	}

	@Override
	public boolean visit(EnumDeclaration node){
		//未実装
		return true;
	}

	@Override
	public boolean visit(AnnotationTypeDeclaration node){
		//未実装
		return true;
	}

	@Override
	public boolean visit(AnonymousClassDeclaration node){
		//未実装
		return true;
	}
}
