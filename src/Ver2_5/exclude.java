
public class exclude {
	String tempStr="";
	boolean isComment = false;//是否為註解
	Object[] arr = {"",false};//回傳strData,isComment
	
	Object exclude(String strData,char[] ch,boolean isCom){
		
		arr[0]="";
		L1:for(int j=0;j<ch.length-1;j++){
			if(ch[j]=='/'&& j<ch.length-1){
				if(ch[j+1]=='/' && isCom==false){
					strData = strData.substring(0, j);
					break L1;
				}
				else if(ch[j+1]=='*'){
					strData = strData.substring(0, j);
					isComment = true;
					//break L1;
				}		                					
			}
			//註解尾巴部分
			else if(ch[j]=='*' && ch[j+1]=='/' && isComment==true){
				if(j<ch.length-2){
					strData = strData.substring(j+2,ch.length-1);
				}
				isComment = false;
			}
			else if(isComment ==true){
				strData="";
			}
			//排除字串
			else if(ch[j]=='"'){
				String space = "";
				int i=j;
				do{
					j++;
				}while((ch[j]!='"') && j<ch.length-1);
				
				tempStr = strData.substring(i, j+1);
				for(int k=0;k<tempStr.length();k++){
					space+=",";
				}
				//System.out.println("StrDataBefore:"+strData);
				strData = strData.replace(tempStr,space);
				//System.out.println("StrDataAfter :"+strData);
			}
			//排除字元
			else if(ch[j]=='\''){
				String space = "";
				int i=j;
				do{
					j++;
				}while((ch[j]!='\'') && j<ch.length-1);
				
				tempStr = strData.substring(i, j+1);
				for(int k=0;k<tempStr.length();k++){
					space+=",";
				}
				//System.out.println("StrDataBefore:"+strData);
				strData = strData.replace(tempStr,space);
				//System.out.println("StrDataAfter :"+strData);
			}
		}
		arr[0] = strData;
		arr[1] = isComment;
		
		return arr;
	}
}
