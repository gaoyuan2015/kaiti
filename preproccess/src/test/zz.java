package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class zz {
	public static void main(String[] args) {
         // ֻ������ĸ������        
         // String   regEx  =  "[^a-zA-Z0-9]";                      
            // ��������������ַ�   
   String regEx="[`~!@#$%^&*()+=|{}':;',//[//].<>/?~��@#��%����&*��������+|{}������������������������]";   
   Pattern   p   =   Pattern.compile(regEx);      
   Matcher   m   =   p.matcher("tp35/36");      
   System.out.println( m.replaceAll("").trim()); 
   }      
}
