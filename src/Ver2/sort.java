
import java.util.ArrayList;


public class sort {

	//將A與B中的方法名稱做比對並將相同的互換
	void swap(ArrayList<Object> nameAll,ArrayList<Double> countAll,ArrayList<String> HighLow,ArrayList<String> nameSubAll){
		//將nameAll與nameSubAll中的方法名稱做比對並將相同的互換
         double valueall;
		 String highlow;
		 String nameall;
		 
		 double vtempall;
		 String hltemp;
		 String ntempall;
        for(int k=0;k<nameAll.size()-1;k++){
       	 for(int l=k;l<nameAll.size();l++){
       		 valueall=countAll.get(k);
       		 highlow = HighLow.get(k);
       		 nameall = nameSubAll.get(k);
       		 
       		 double temp_value=countAll.get(l);
       		 String temp_highlow=HighLow.get(l);
       		 String temp_name=nameSubAll.get(l);
       		 if(String.valueOf(nameAll.get(k).equals(nameSubAll.get(l))).equals("true")){
       			 vtempall=temp_value;
       			 hltemp=temp_highlow;
       			 ntempall=temp_name;
       			 
       			 countAll.set(l,valueall);
       			 HighLow.set(l, highlow);
       			 nameSubAll.set(l, nameall);
       			 
       			 
       			 countAll.set(k,vtempall);
       			 HighLow.set(k, hltemp);
       			 nameSubAll.set(k, ntempall);
       			 break;
       		 }
       	 }
        }
	}
	
	
}
