/*  Written By:  Maeve Carr
	 Date:
	 Desc:
	 File:
*/


import java.util.*;

public class HangmanGameTester
{
   public static void main(String[] args)
   {
      WordGenerator myWordGen = new WordGenerator();
      String word = myWordGen.getWord();
   //HangmanGame myGame = new HangmanGame("Hangman");  //for testing
   
      HangmanGame myGame = new HangmanGame(word);
   
      Scanner keyIn = new Scanner(System.in);
      char letter;
      myGame.fillDashes();
      
      do{
         System.out.println(myGame.showDashes());
      	
         System.out.print("\n\nenter letter: ");
         letter = keyIn.next().charAt(0);
      	
         myGame.guessLetter(letter);
      	
         System.out.println("Lives left : " +myGame.getLives());
      }while(!(myGame.gameOver()));	
   	
      if(myGame.getLives() == 0)
         System.out.print("You lost\nSecret word is " + myGame.showSecretWord());	
      else
         System.out.print("you win! You used " +(8 - myGame.getLives()) +" lives");	
   	
   }
}