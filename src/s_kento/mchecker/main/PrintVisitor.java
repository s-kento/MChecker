package s_kento.mchecker.main;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
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
	public List<Sentence> sentences = new ArrayList<Sentence>();

	public PrintVisitor(CompilationUnit unit) {
		this.unit = unit;
	}

	public List<Sentence> getSentences(){
		return sentences;
	}

	public int getLine(ASTNode node) {
		return unit.getLineNumber(node.getStartPosition());
	}

	@Override
	public boolean visit(ExpressionStatement node) {
		Expression ex = node.getExpression();
		Sentence sentence = new Sentence(ex.toString(), getLine(node));
		sentences.add(sentence);
		return true;
	}

	@Override
	public boolean visit(VariableDeclarationStatement node) {
		List<Modifier> modifiers = node.modifiers();
		Type type = node.getType();
		List<VariableDeclarationFragment> frags = node.fragments();
		Sentence sentence = new Sentence();
		for (Modifier modifier : modifiers) {
			sentence.addSentence(modifier.toString() + " ");
		}
		sentence.addSentence(type.toString() + " ");
		for (VariableDeclarationFragment frag : frags) {
			sentence.addSentence(frag + " ");
		}
		sentence.setLine(getLine(node));
		sentences.add(sentence);
		return false;
	}

	@Override
	public boolean visit(IfStatement node) {
		Expression ex = node.getExpression();
		Sentence sentence = new Sentence("if(" + ex.toString() + ")", getLine(node));
		sentences.add(sentence);
		return true;
	}

	@Override
	public boolean visit(ForStatement node) {
		List<Expression> initializers = node.initializers();
		Expression ex = node.getExpression();
		List<Expression> updaters = node.updaters();
		Sentence sentence = new Sentence();
		sentence.addSentence("for(");
		for (Expression init : initializers) {
			sentence.addSentence(init.toString());
		}
		sentence.addSentence("; " + ex.toString() + "; ");
		for (Expression updater : updaters) {
			sentence.addSentence(updater.toString());
		}
		sentence.addSentence(")");
		sentence.setLine(getLine(node));
		sentences.add(sentence);
		return true;
	}

	@Override
	public boolean visit(EnhancedForStatement node) {
		SingleVariableDeclaration paramter = node.getParameter();
		Expression ex = node.getExpression();
		Sentence sentence = new Sentence("for(" + paramter.toString() + " : " + ex.toString() + ")", getLine(node));
		sentences.add(sentence);
		return true;
	}

	@Override
	public boolean visit(ReturnStatement node) {
		Expression ex = node.getExpression();
		Sentence sentence = new Sentence("return " + ex.toString(),getLine(node));
		sentences.add(sentence);
		return true;
	}

	@Override
	public boolean visit(FieldDeclaration node) {
		List<Modifier> modifiers = node.modifiers();
		Type type = node.getType();
		List<VariableDeclarationFragment> frags = node.fragments();
		Sentence sentence= new Sentence();
		for (Modifier modifier : modifiers) {
			sentence.addSentence(modifier.toString() + " ");
		}
		sentence.addSentence(type.toString() + " ");
		for (VariableDeclarationFragment frag : frags) {
			sentence.addSentence(frag + " ");
		}
		sentence.setLine(getLine(node));
		sentences.add(sentence);
		return true;
	}

	@Override
	public boolean visit(PackageDeclaration node) {
		Name name = node.getName();
		Sentence sentence = new Sentence("package " + name , getLine(node));
		sentences.add(sentence);
		return true;
	}

	@Override
	public boolean visit(ImportDeclaration node) {
		Sentence sentence = new Sentence();
		sentence.addSentence("import ");
		if (node.isStatic()) {
			sentence.addSentence("static ");
		}
		sentence.addSentence(node.getName().toString());
		if (node.isOnDemand()) {
			sentence.addSentence(".*");
		}
		sentence.setLine(getLine(node));
		sentences.add(sentence);
		return true;
	}

	@Override
	public boolean visit(WhileStatement node) {
		Expression ex = node.getExpression();
		Sentence sentence = new Sentence("while( " + ex.toString() + ")", getLine(node));
		sentences.add(sentence);
		return true;
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		List<IExtendedModifier> modifiers = node.modifiers();
		Type type = node.getReturnType2();
		SimpleName name = node.getName();
		List<SingleVariableDeclaration> parameters = node.parameters();
		List<Type> exceptions = node.thrownExceptionTypes();
		Sentence sentence = new Sentence();
		for (int i = 0; i < modifiers.size(); i++) {
			if (modifiers.get(i).isModifier())
				sentence.addSentence(modifiers.get(i).toString() + " ");
		}
		if (!node.isConstructor()) {
			sentence.addSentence(type.toString() + " ");
		}
		sentence.addSentence(name.toString() + "(");
		for (SingleVariableDeclaration parameter : parameters) {
			sentence.addSentence(parameter.toString() + " ");
		}
		sentence.addSentence(")");
		if (exceptions.size() > 0)
			sentence.addSentence(" throws ");
		for (Type exception : exceptions) {
			sentence.addSentence(exception.toString() + " ");
		}
		sentence.setLine(getLine(node));
		sentences.add(sentence);
		return true;
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		List<Modifier> modifiers = node.modifiers();
		boolean inter = node.isInterface();
		Name name = node.getName();
		Type type = node.getSuperclassType();
		List<Type> interTypes = node.superInterfaceTypes();
		Sentence sentence = new Sentence();
		for (Modifier modifier : modifiers) {
			sentence.addSentence(modifier.toString() + " ");
		}
		if (!inter) {
			sentence.addSentence("class " + name.toString());
		} else {
			sentence.addSentence("interface " + name.toString());
		}
		if (type != null) {
			sentence.addSentence(" extends " + type.toString());
		}
		if (interTypes.size() != 0) {
			sentence.addSentence(" implements ");
			for (Type interType : interTypes) {
				sentence.addSentence(interType.toString());
			}
		}
		sentence.setLine(getLine(node));
		sentences.add(sentence);
		return true;
	}

	@Override
	public boolean visit(AssertStatement node) {
		Expression ex1 = node.getExpression();
		Expression ex2 = node.getMessage();
		Sentence sentence = new Sentence();
		sentence.addSentence("assert " + ex1.toString());
		if (ex2 != null) {
			sentence.addSentence(" : " + ex2.toString());
		}
		sentence.setLine(getLine(node));
		sentences.add(sentence);
		return true;
	}

	@Override
	public boolean visit(BreakStatement node) {
		SimpleName label = node.getLabel();
		Sentence sentence = new Sentence();
		sentence.addSentence("break ");
		if (label != null) {
			sentence.addSentence(label.toString());
		}
		sentence.setLine(getLine(node));
		sentences.add(sentence);
		return true;
	}

	@Override
	public boolean visit(ContinueStatement node) {
		SimpleName label = node.getLabel();
		Sentence sentence = new Sentence();
		sentence.addSentence("continue ");
		if (label != null) {
			sentence.addSentence(label.toString());
		}
		sentence.setLine(getLine(node));
		sentences.add(sentence);
		return true;
	}

	@Override
	public boolean visit(DoStatement node) {
		Statement body = node.getBody();
		Expression ex = node.getExpression();
		Sentence sentence1 = new Sentence("do", getLine(node));
		sentences.add(sentence1);
		body.accept(this);
		Sentence sentence2 = new Sentence("while(" + ex.toString() + ")", getLine(ex));
		sentences.add(sentence2);
		return false;
	}

	@Override
	public boolean visit(LabeledStatement node) {
		Statement body = node.getBody();
		SimpleName label = node.getLabel();
		Sentence sentence = new Sentence(label.toString()+ " : ", getLine(node));
		sentences.add(sentence);
		body.accept(this);
		return false;
	}

	@Override
	public boolean visit(SwitchStatement node) {
		Expression ex = node.getExpression();
		Sentence sentence = new Sentence ("switch(" + ex.toString() + ")", getLine(node));
		sentences.add(sentence);
		return true;
	}

	@Override
	public boolean visit(SwitchCase node) {
		Expression ex = node.getExpression();
		Sentence sentence = new Sentence();
		if (!node.isDefault()) {
			sentence.addSentence("case " + ex.toString() + " :");
		} else {
			sentence.addSentence("default : ");
		}
		sentence.setLine(getLine(node));
		sentences.add(sentence);
		return true;
	}

	@Override
	public boolean visit(SynchronizedStatement node) {
		Expression ex = node.getExpression();
		Sentence sentence = new Sentence("synchronized(" + ex.toString() + ")", getLine(node));
		sentences.add(sentence);
		return true;
	}

	@Override
	public boolean visit(TryStatement node) {
		Block body = node.getBody();
		List<CatchClause> catches = node.catchClauses();
		Block fi = node.getFinally();
		Sentence sentence1 = new Sentence("try", getLine(node));
		sentences.add(sentence1);
		body.accept(this);
		for (CatchClause c : catches) {
			c.accept(this);
		}
		if (fi != null) {
			Sentence sentence2 = new Sentence("finally", getLine(fi));
			sentences.add(sentence2);
			fi.accept(this);
		}
		return false;
	}

	@Override
	public boolean visit(CatchClause node) {
		Block body = node.getBody();
		SingleVariableDeclaration ex = node.getException();
		Sentence sentence = new Sentence("catch(" + ex.toString() + ")", getLine(node));
		sentences.add(sentence);
		body.accept(this);
		return false;
	}

	@Override
	public boolean visit(ThrowStatement node) {
		Expression ex = node.getExpression();
		Sentence sentence = new Sentence("throw " + ex.toString(), getLine(node));
		sentences.add(sentence);
		return true;
	}

	@Override
	public boolean visit(EnumDeclaration node) {
		// 未実装
		return true;
	}

	@Override
	public boolean visit(AnnotationTypeDeclaration node) {
		// 未実装
		return true;
	}

	@Override
	public boolean visit(AnonymousClassDeclaration node) {
		// 未実装
		return true;
	}
}
