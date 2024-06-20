/*  Written By:  Maeve Carr
	 Date:
	 Desc:
	 File:
*/


import java.util.*;
 
 public class WordGenerator
 {
 		//create an ArrayList of String called myList
		ArrayList<String> myWordList; 
		
		public WordGenerator()
		{
			myWordList = new ArrayList<String>(Arrays.asList("monday", "tuesday", "wednesday", "thursday",
								"friday", "saturday", "sunday", "apple" , "banana", "pear", "pomegranate", 
								"science", "computers", "engineering", "secret", "component", "button",
                        "encylopedia", "jazz", "fizz", "jinx", "programming", "java", "eight", "rhythm", 
                        "quiz", "buzz", "avenue", "genre" ));
		}
	    
		public String getWord()
		{
			Random noGen = new Random();
			int wordIndex = noGen.nextInt(myWordList.size());
			return myWordList.get(wordIndex);
		}  
} 
