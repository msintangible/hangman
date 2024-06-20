/*  Written By:  Maeve Carr
	 Date:
	 Desc:
	 File:
*/
///////////////////////////////////////////////////
///*****YOU MUST COMMENT THIS CODE*****////////////
////////////YOU CANNOT CHANGE THIS CODE////////////
///////////////////////////////////////////////////
public class HangmanGame
{
   private String secretWord;
   private char[] dashes;
   private int lives; 
	
	
   public HangmanGame(String wordIn)
   {
      secretWord = wordIn;
      dashes = new char[secretWord.length()];
      fillDashes();
      lives = 8;  //default
   }
   

   public void guessLetter(char letterIn)
   {
      boolean found = false;
      for(int i = 0; i<secretWord.length(); i++)
      {
         if(letterIn == secretWord.charAt(i)){
            dashes[i] = letterIn;
            found = true;
         }	
      }
      if(!found)
         lives--;
   
   }
	

   public boolean gameOver()   
   {
      if(secretWord.equalsIgnoreCase(showDashes()) || lives == 0)
         return true;
      else 
         return false;
   		
   	//return secretWord.equalsIgnoreCase(showDashes()) || lives == 0;
   }
	
	
   public String showSecretWord()
   {
      return secretWord;
   }

   public String showDashes()
   {
      String s ="";
      for(int i = 0; i< dashes.length; i++)
         s +=dashes[i];
   	
      return s;
   }
	
	
   public void fillDashes()
   {
      for(int i = 0; i< dashes.length; i++)
         dashes[i]='-';		
   }
	

   public int getLives()
   {
      return lives;
   }
	
	
   public void setLives(int livesIn)
   {
      lives = livesIn;
   }
}