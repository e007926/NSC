package Ver2;
import java.io.File;
import java.io.FilenameFilter;

public class listFile {
	
	String[] listFile(String dirPath){
		//如果文件不以分隔符結尾，自動加入分隔符
		if(!dirPath.endsWith(File.separator)){
			dirPath = dirPath+File.separator;
		}
		File dirFile = new File(dirPath);
		if((!dirFile.exists())||(!dirFile.isDirectory())){
			return null;
		}
		FilenameFilter ff = new FilenameFilter(){
			private String filter = ".java";
			
			@Override
			public boolean accept(File dir, String filename) {
			    if(filename.indexOf(filter) != -1)return true;
			    return false;
			}};
		return dirFile.list(ff);
	}
}
