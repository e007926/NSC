
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;


public class countFileLine {
	int countLine(String filePath){
		int count=0; 
		try{
			LineNumberReader inputFile = null;
			inputFile = new LineNumberReader(new FileReader(filePath));
			
			while((inputFile.readLine())!=null){
			count++;
			}
			inputFile.close();
		}catch(Exception IOException){
			
			}
		return count;
		}
}
