package s_kento.mchecker.file;

public class FilePair {
	private String original; //修正前のファイルパス
	private String revised; //修正後のファイルパス

	public FilePair(String original, String revised){
		setOriginal(original);
		setRevised(revised);
	}

	public String getOriginal() {
		return original;
	}
	public void setOriginal(String original) {
		this.original = original;
	}
	public String getRevised() {
		return revised;
	}
	public void setRevised(String revised) {
		this.revised = revised;
	}
}
