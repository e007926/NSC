package Ver2;
import java.io.File;
import java.io.FilenameFilter;

public class listFile {
	
	String[] listFile(String dirPath){
		//�p�G��󤣥H���j�ŵ����A�۰ʥ[�J���j��
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
