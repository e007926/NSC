
public class getMethodName {
	
	String getName(char[] ch){
		String methName = "";
		for(int i=0;i<ch.length;i++){
			if(ch[i]=='('){
				for(int j = i-1 ; j>=0 ; j--){
					if(isValidChar(ch[j])){
						methName = ch[j]+methName;
					}
					else
						j=0;
				}
				return methName;
			}
			
		}
		return methName;
	}
	
	private static boolean isValidChar(char ch) {
        return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9') || ch == '_';
    }
}
