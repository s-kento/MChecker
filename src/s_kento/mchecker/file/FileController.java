package s_kento.mchecker.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//ファイルの操作を担当
public class FileController {
	private static List<File> originalList = new ArrayList<File>();
	private static List<File> revisedList = new ArrayList<File>();

	public List<FilePair> makeFilePair(String originalPath, String revisedPath){
		List<FilePair> filePairs = new ArrayList<FilePair>();
		File originalDir = new File(originalPath);
		File revisedDir = new File(revisedPath);
		makeFileList(originalDir, "ORIGINAL");
		makeFileList(revisedDir, "REVISED");
		for(File original:originalList){
			for(int i=0;i<revisedList.size();i++){
				if(original.getName().equals(revisedList.get(i).getName())){
					FilePair filePair = new FilePair(original.getAbsolutePath(),revisedList.get(i).getAbsolutePath());
					filePairs.add(filePair);
					revisedList.remove(i);
					break;
				}
			}
		}
		return filePairs;
	}

	public void makeFileList(File file, String st) {
		if (file.isDirectory()) {
			File[] innerFiles = file.listFiles();
			for (File tmp : innerFiles) {
				makeFileList(tmp, st);
			}
		} else if (file.isFile()) {
			if (file.getName().endsWith(".java")) {
				if(st.equals("ORIGINAL"))
					originalList.add(file);
				else if(st.equals("REVISED"))
					revisedList.add(file);
			}
		}
	}
}
