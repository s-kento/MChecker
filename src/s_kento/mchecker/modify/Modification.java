package s_kento.mchecker.modify;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

//変更パターンを格納するクラス
public class Modification {
	private String modification;
	private List<?> original;
	private int originalLine;
	private List<?> revised;
	private int revisedLine;

	public static String hashCode(String source){
		String hash=DigestUtils.md5Hex(source);
		return hash;
	}

	public int getOriginalLine() {
		return originalLine;
	}

	public void setOriginalLine(int originalLine) {
		this.originalLine = originalLine;
	}

	public int getRevisedLine() {
		return revisedLine;
	}

	public void setRevisedLine(int revisedLine) {
		this.revisedLine = revisedLine;
	}

	public Modification(String modification, List<?> original, List<?> revised){
		setModification(modification);
		setOriginal(original);
		setRevised(revised);
	}

	public String getModification() {
		return modification;
	}
	public void setModification(String modification) {
		this.modification = modification;
	}
	public List<?> getOriginal() {
		return original;
	}
	public void setOriginal(List<?> original) {
		this.original = original;
	}
	public List<?> getRevised() {
		return revised;
	}
	public void setRevised(List<?> revised) {
		this.revised = revised;
	}
}
