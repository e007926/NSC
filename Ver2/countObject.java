package Ver2;
public class countObject {
	String[] form = {".*void ", ".*static double ", ".*static int ", ".*static float ", ".*static long ",
            ".*static byte ", ".*static short ", ".*static char ", ".*static String ",
            ".*static boolean ", ".*static ", ".*double ", ".*int ", ".*float ", ".*long ",
            ".*byte ", ".*short ", ".*char ", ".*String ", ".*boolean ", ".*static "};
	String[] keyword = {".*new[\\s].*"};
    String ch="";
    
    int countObjNew(int count,char[] c,String s){
        
        for(String str:keyword){
             
			if(s.matches(str)){
		     if(str.equals(".*new[\\s].*")){
		                     String sp[]=s.split(" ");
			     for(int i=0;i<sp.length;i++){
			             if(sp[i].matches("new") || sp[i].matches(".*[\\W]new")){
			            	 count++;
			                 System.out.println(sp[i]);
			             }
			     }
		     }
		                   
		    }
		
		}//End of for
           
        return count;
    }//End of method

	int countObjPara(int count,String s,char[] c){
	
		for(String st:form){
		        
		if(s.matches(st+ ".*(.*).*") && !s.matches(".*;.*") && !s.matches(".*for(.*:.*).*")){
		  System.out.println(s);
		 
		 for(int i=0;i<s.length();i++){
		      
			  if(c[i]=='('){
				   int j=i;
				   do{
				       i++;
				   }while(c[i]!=')' && i<c.length-1);
				   
				   ch=s.substring(j,i+1);
				   ch=ch.replace("(","");
				   ch=ch.replace(")","").trim();
				   if(ch.matches(".*,.*")){
				      String clare[] =ch.split(",");
				      for(int k=0;k<clare.length;k++){ 
				         if(isObject(clare[k])){
				             count++;
				             System.out.println(clare[k]); 
				         }
				         else{
				             count+=0;
				         }
				      }
				   }
				   else{
				     if(isObject(ch)){
				         count++;
				         System.out.println(ch);
				     }
				     else{
				         count+=0;
				     }
				    
				   }  
			  }
		   
		 }
		break; 
		}    
		}
		return count;
	}

	private static boolean isObject(String name){
		return name.matches(".*new[\\s].*")||!(name.matches(".*float.*") || name.matches(".*double.*") || name.matches(".*int.*") ||
		  name.matches(".*char.*") || name.matches(".*boolean.*")||name.matches(".*byte.*") || name.matches("") ) ;
	}
}
