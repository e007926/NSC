package Ver2;
import java.util.Stack;


public class countCC {
	
	String[] keyword = {"[\\W]*if(.*).*",".*else if(.*).*",".*else[\\W]*",".*for(.*;.*;.*).*",".*do.*",".*while(.*).*",".*switch(.*).*",".*case.*"};	
	String[] op = {".*>.*",".*<.*",".*==.*",".*!=.*",".*>=.*",".*<=.*"};
	char[] operator = {'&','|'};
	Stack<Character> stack = new Stack<Character>();//存放條件式{
	Stack<Character> fstack = new Stack<Character>();//存放條件式(
	boolean iscondi = false;
	boolean isIf = false;
	boolean isR = false;
	
	int countCondition(int count,char[] c,String s){
				if(count==-1){//初始化成員變數數值
					isIf = false;
					iscondi = false;
					return 0;
				}
				for(int i=0;i<c.length;i++){
					if(c[i]=='}'&& stack.size()>0){
							stack.pop();
						}
					}	
				iscondi = false;//ini
				
				for(String str:keyword){
					if( s.matches(str)){
						iscondi = true;
						
						if(str.equals("[\\W]*if(.*).*")){
							if(isIf == true && stack.size()==0){
								isIf =false;
								return -2;
							}
							isIf = true;
							for(int i=0;i<c.length;i++){
								if(c[i]=='{'){
									stack.push('{');
									}
								else if(c[i]=='('){
									fstack.push('(');
								}
								else if(c[i]==')'){
									fstack.pop();
								}
							}
							
							
								count++;
								
								for(char Op:operator){
									for(int j=0;j<c.length;j++){
										if((c[j] == Op)&&(c[j+1] == Op)){
											count++;
											j++;
										}
										else if(c[j] == Op){
											count++;
										}
									}
								}
						}
						else if(str.equals(".*else if(.*).*")){
							isIf = true;
							for(int i=0;i<c.length;i++){
								if(c[i]=='{'){
									stack.push('{');
									}
								else if(c[i]=='('){
									fstack.push('(');
								}
								else if(c[i]==')' && fstack.size()>0){
									fstack.pop();
								}
							}
								count++;
								
								for(char Op:operator){
									for(int j=0;j<c.length;j++){
										if((c[j] == Op)&&(c[j+1] == Op)){
											count++;
											j++;
										}
										else if(c[j] == Op){
											count++;
										}
									}
								}
						}
						else if(str.matches(".*else[\\W]*")){
							isIf = true;
							for(int i=0;i<c.length;i++){
								if(c[i]=='{'){
									stack.push('{');
									}
							}
						}
						else if(str.equals(".*while(.*).*")){
							isIf = false;
							for(int i=0;i<c.length;i++){
								if(c[i]=='{'){
									stack.push('{');
									}
								else if(c[i]=='('){
									fstack.push('(');
								}
								else if(c[i]==')'){
									fstack.pop();
								}
							}
								count++;
								
								for(char Op:operator){
									for(int j=0;j<c.length;j++){
										if((c[j] == Op)&&(c[j+1] == Op)){
											count++;
											j++;
										}
										else if(c[j] == Op){
											count++;
										}
									}
								}
						}
						else if(str.equals(".*for(.*;.*;.*).*")){
							isIf = false;
								for(int i=0;i<c.length;i++){
									if(c[i]=='{'){
										stack.push('{');
										}
								}
								count++;
						}
						else if(str.matches(".*do.*")){
							isIf = false;
							for(int i=0;i<c.length;i++){
								if(c[i]=='{'){
									stack.push('{');
									}
							}
						}
						else if(str.matches(".*switch(.*).*")){
							isIf = false;
							for(int i=0;i<c.length;i++){
								if(c[i]=='{'){
									stack.push('{');
									}
							}
						}
						else if(str.matches(".*case.*")){
							isIf = false;
								for(int i=0;i<c.length;i++){
									if(c[i]=='{'){
										stack.push('{');
										}
								}
								count++;
						}
						
				}
		}//End of for
		if(s.matches("[\\W]*return.*")){
			isR = true;
		}
				
		if(fstack.size()>0 && iscondi==false){
			for(int j=0;j<c.length;j++){
				if(c[j]=='{'){
					stack.push('{');
					}
				else if(c[j]=='('){
					fstack.push('(');
				}
				else if(c[j]==')'){
					fstack.pop();
				}
			}
			for(char Op:operator){
				for(int j=0;j<c.length;j++){
					if((c[j] == Op)&&(c[j+1] == Op)){
						count++;
						j++;
					}
					else if(c[j] == Op){
						count++;
					}
					
				}
			}
		}
		if(isR==true){
			if(count>0){
				count++;
			}
			else{
				L3:for(String opr:op){
					if(s.matches(opr)){
						count++;
						break L3;
					}
				}
			}
			isR =false;
		}
		
		
		if(stack.size()==0 && isIf ==false && count==0){
			return -1;
		}
		return count;
	}//End of method
	
	
 }
