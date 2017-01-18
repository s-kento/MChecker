package s_kento.mchecker.main;

import org.apache.commons.codec.digest.DigestUtils;

public class Sentence {
	private String sentence="";
	private String hashSentence;
	private int line;

	public Sentence(){
	}
	public Sentence(String sentence, int line){
		setSentence(sentence);
		setLine(line);
	}

	public String getSentence() {
		return sentence;
	}
	public void setSentence(String sentence) {
		this.sentence = sentence;
		this.hashSentence = DigestUtils.md5Hex(sentence);
	}
	public String getHashSentence(){
		return hashSentence;
	}
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public void addSentence(String addition){
		this.sentence+=addition;
	}

}
