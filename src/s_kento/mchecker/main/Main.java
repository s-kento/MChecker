package s_kento.mchecker.main;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		Normalizer norm = new Normalizer();
		norm.normalize(args[0]);
	}
}
