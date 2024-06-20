

import javafx.collections.*;
import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.event.*;
import javafx.scene.input.*;



 /**
   hangman design layout 
*/
  

      
public class HangmanGui extends Application
{ 
   //menu headers
   private  Menu menuGame;
   private Menu menuOptions;
   private Menu menuDifficulty;
   private MenuBar menuBar; //creates the menu bar for all the menus to enter
   Stage stage; //refrence of the stage class

    
   //menu game opitons
   private  MenuItem menuItemNewGame; //creates a new game
   private MenuItem menuItemPauseGame; //pause game 
   private MenuItem menuItemQuitGame;//quit game
   private MenuItem menuItemExit;//exits the game by closing the stage
   
   //menu difficulty options  
   private RadioMenuItem menuItemEasy;
   private RadioMenuItem menuItemMedium;
   private RadioMenuItem menuItemHard;

   
 //hangman parts
   private Line bottomBar;
   private Line topBar;
   private Line leftPost;
   private Line connectionLine;
   private Circle head;
   private Line rightArm;
   private Line leftArm;
   private Line body;
   private Line rightLeg;
   private Line leftLeg;
   private Line rightFoot;
   private Line leftFoot;
   private GridPane pane;
   private Pane hbox; 
   
 //game logic references  
   private WordGenerator myWordGen;
   private String word;
   private HangmanGame game;
   private Label gamelbl;
   private  Label wordlbl;
   private  Label  liveCounter;
   private Button pgBtn;
   private Button hintBtn;
   private Label hintlbl;
   // buttons array list refrences
   private  ObservableList<Button> buttons;
   private  ObservableList<Button> buttons2;
   private  ObservableList<Button> buttons3;
 
   @Override
   public void start(Stage primaryStage)
   {
   
      stage = primaryStage; //sets the stage to the primary stage
      myWordGen = new WordGenerator(); //object of wordGenerator
      word = myWordGen.getWord(); //the word return  from the object using the get.Word()method
      game = new HangmanGame(word); //object of the hangman class
      GridPane pane = new GridPane(); //gridpane used  to set the position of the hangman gui
      VBox mainContainer = new VBox();//vbox used set the menu then gridpane
   
      mainContainer.getChildren().addAll(createMenuBar(),pane);
      //positions of the gui features
      pane.add(creratePlayAgainButton(),0,0, 2, 1); 
      pane.add(createHintButton() ,0, 0);
      pane.add(createHangman(game.getLives()),0,1,2,1);            
      pane.add(displayWord(),1,2);
      pane.add(createKeyBoard(),0,3,2,1);
      pane.setHalignment(createKeyBoard(),HPos.CENTER); //centers the keyboard
      
      
      Scene scene = new Scene(mainContainer,600,600); 
      scene.getStylesheets().add("hangman.css");  
      primaryStage.setScene(scene); 
      primaryStage.setTitle("Hangman");
      primaryStage.show();
   }
   
   

   public MenuBar createMenuBar()
   {   
   //Then create one or more menus and add  them to the menu bar.
      menuBar = new MenuBar(createMenuGame(), createMenuOptions(), new Menu("Help"));
       
      return menuBar;
   
   }
   
   public Menu createMenuGame()
   {
      // Create the Game menu
      Menu menuGame = new Menu("_Game");
         
   //create  menuItems for Game menu
      menuItemNewGame = new MenuItem("_New Game");
      menuItemPauseGame = new MenuItem("_Pause Game");
      menuItemQuitGame = new MenuItem("_Quit Game");
      menuItemExit = new MenuItem("E_xit");
      
      //set accelerator key for New Game Ctr+N
      menuItemNewGame.setAccelerator(KeyCombination.keyCombination("Shortcut+N"));
   
   //add eventHandlers to menu items
      menuItemNewGame.setOnAction(e -> restartGame());
      menuItemPauseGame.setOnAction(e -> menuAction(e));
      menuItemQuitGame.setOnAction(e -> menuAction(e));
      menuItemExit.setOnAction(e -> menuItemExitAction());
                 
   
   //or use addAll() to add  menuItems for Game menu
      menuGame.getItems().addAll(menuItemNewGame,menuItemPauseGame, 
                  menuItemQuitGame, new SeparatorMenuItem() ,   menuItemExit);
      
   
   
      return menuGame;
   }
   
   public Menu createMenuOptions()
   {
     // Create the Options menu
      menuOptions = new Menu("_Options"); 
   // Create the check menu items
      CheckMenuItem menuItemSound = new CheckMenuItem("_Sound");
      CheckMenuItem menuItemMusic = new CheckMenuItem("_Music");
   
   // Create the radio menu items
      menuItemEasy = new RadioMenuItem("_Easy");
      menuItemMedium = new RadioMenuItem("_Medium");
      menuItemHard = new RadioMenuItem("_Hard");
      menuItemEasy.setSelected(true); 
      
      menuItemEasy.setOnAction( 
         e -> {
            game.setLives(12);//sets the lives to 12 for easy
            liveCounter.setText("Lives: "+game.getLives());
            menuItemMedium.setDisable(true);
            menuItemHard.setDisable(true);  
         });
      menuItemMedium.setOnAction( 
         e -> {
            game.setLives(10);// sets the lives  to 10 for meduim
            liveCounter.setText("Lives: "+game.getLives());
            menuItemEasy.setDisable(true);
            menuItemHard.setDisable(true);
         
         });
      menuItemHard.setOnAction( 
         e -> 
         {
            game.setLives(8);//sets the lives to 8 for hard
            liveCounter.setText("Lives: "+game.getLives());
            menuItemEasy.setDisable(true);
            menuItemMedium.setDisable(true);
         
         });
         //if the users choose a checkbox the other will be disabled 
      
    //Create and set toggleGroup
      ToggleGroup difficultyGroup = new ToggleGroup();
      menuItemEasy.setToggleGroup(difficultyGroup);
      menuItemMedium.setToggleGroup(difficultyGroup);
      menuItemHard.setToggleGroup(difficultyGroup);
   
   // Create the Difficulty submenu
      menuDifficulty = new Menu("_Difficulty");
      menuDifficulty.getItems().add(menuItemEasy);
      menuDifficulty.getItems().add(menuItemMedium);
      menuDifficulty.getItems().add(menuItemHard);  //or could use addAll()
   
   //add items to menu options
      menuOptions.getItems().addAll(menuItemSound, menuItemMusic, menuDifficulty);
   
      return menuOptions;
   }
   
 
   public void menuAction(ActionEvent e)
   {
      MenuItem item = (MenuItem)e.getSource();
      System.out.println(item.getText());
   }
 
   private void menuItemExitAction()
   {
      stage.close();//close the stage
   }   

   
   
   public VBox createKeyBoard()
   {
      VBox box = new VBox(10); //used to create the keyboard 3 rows
      box.getChildren().addAll(firstRow(),secondRow(),thirdRow());
      box.setAlignment(Pos.CENTER);
      box.setPadding( new Insets(30));     
      return box;
   }
   
   public HBox firstRow() {
   
      buttons = FXCollections.observableArrayList(); //list made to hold all the buttons
      HBox hbox = new HBox(10);
   
      char[] firstrow = {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p'};//first row of chars
   
      for (char s : firstrow) {//enchanced for loop to loop through char array
         Button button = new Button(""+s);//button set with the char
         button.setPrefWidth(50);//set width of the button 
         button.setOnAction(
            e -> {
            
                
                 //update the label
               if(!game.gameOver())// if the game is not over
               {
                  char letter =  button.getText().charAt(0); //get the char or the button when clicked
                  game.guessLetter(letter);//pass the char into guess ltter form the hangman game class
               
                  liveCounter.setText("Lives: "+game.getLives());//label updated
                  wordlbl.setText(game.showDashes());//the chars start to show if the char is correct 
                  updateHangman(game.getLives());//sets the drawing of the hangman with lives
                  System.out.println("Clicked key: " + button.getText() +" lives: " +game.getLives());
                  button.setDisable(true); // Disable button after click
                  buttons.add(button); //adds the button to the list
               
               
               }
               else {
               
                 //when the game is over the word is updated and the sercert word is shown
                  wordlbl.setText("The secert word was: "+word);
                  System.out.println("gameoveer");
                  
                  if(game.getLives()>0)
                  {    //if the lives are greater than 0 u win
                     gamelbl.setText("YOU WIN!!!!!!!!!!");
                     System.out.println("winner");
                  }
                  else
                  { 
                    //else u lose
                     gamelbl.setText("YOU LOST!!!!!!!!!!");
                     System.out.println("loser");
                  
                  }
               
               }
            
                        
            
            });
            
         if(game.gameOver())
         {   
            wordlbl.setText(word);
            System.out.println("gameoveer");
            //when the game is over all the buttons are set to be disabled so user cant continue
            for(Button buttonlist : buttons)
            {
               buttonlist.setDisable(true); 
            }
                   
         }
      
                             
         hbox.getChildren().add(button);
      }
   
      return hbox;
   }

   
 // This method creates the second row of buttons for the keyboard
   public HBox secondRow() {
   
   // Observable list to store buttons in this row
      buttons2 = FXCollections.observableArrayList();
   
   // HBox container for the buttons
      HBox hbox = new HBox(10);
   
   // Character array containing the letters for the second row
      char[] secondRow = {'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l'};
   
   // Loop to create buttons for each character
      for (char s : secondRow) {
         Button bottomButton = new Button("" + s); // Button with the character
         bottomButton.setPrefWidth(51); // Set button width
      
      // Event handler for button click
         bottomButton.setOnAction(
            e -> {
            
            // Check if game is not over
               if (!game.gameOver()) {
                  char letter = bottomButton.getText().charAt(0); // Get the clicked letter
               
               // Call game method to guess the letter
                  game.guessLetter(letter);
               
               // Update labels and visuals based on guess
                  liveCounter.setText("Lives: " + game.getLives());
                  wordlbl.setText(game.showDashes());
                  updateHangman(game.getLives());
                  System.out.println("Clicked key: " + bottomButton.getText() + " lives: " + game.getLives());
               
               // Disable button after click and add it to the list
                  bottomButton.setDisable(true);
                  buttons2.add(bottomButton);
               } else {
               // If game is over, reveal word and display win/lose message
                  wordlbl.setText("The secret word was: " + word);
                  System.out.println("gameover");
               
                  if (game.getLives() > 0) {
                     gamelbl.setText("YOU WIN!!!!!!!!!!");
                     System.out.println("winner");
                  } else {
                     gamelbl.setText("YOU LOST!!!!!!!!!!");
                     System.out.println("loser");
                  }
               }
            });
      
      // Disable buttons if game is already over
         if (game.gameOver()) {
            wordlbl.setText(word);
            System.out.println("gameover");
            for (Button buttonList : buttons) {
               buttonList.setDisable(true);
            }
         }
      
      // Add button to the HBox container
         hbox.getChildren().add(bottomButton);
      }
   
   // Return the HBox containing the buttons
      return hbox;
   }

// This method creates the third row of buttons for the keyboard (similar structure)
   public HBox thirdRow() {
   
   // Observable list to store buttons in this row
      buttons3 = FXCollections.observableArrayList();
   
   // HBox container for the buttons
      HBox hbox = new HBox(15);
   
   // Character array containing the letters for the third row
      char[] thirdRow = {'z', 'x', 'c', 'v', 'b', 'n', 'm'};
   
   // Loop to create buttons for each character (similar logic as secondRow)
      for (char s : thirdRow) {
         Button button = new Button("" + s); // Button with the character
         button.setPrefWidth(60); // Set button width
      
      // Event handler with similar logic to secondRow
         button.setOnAction(
            e -> {
             
               if (!game.gameOver()) {
                  char letter = button.getText().charAt(0); // Get the clicked letter
               
               // Call game method to guess the letter
                  game.guessLetter(letter);
               
               // Update labels and visuals based on guess
                  liveCounter.setText("Lives: " + game.getLives());
                  wordlbl.setText(game.showDashes());
                  updateHangman(game.getLives());
                  System.out.println("Clicked key: " + button.getText() + " lives: " + game.getLives());
               
               // Disable button after click and add it to the list
                  button.setDisable(true);
                  buttons3.add(button);
               } 
               else {
               // If game is over, reveal word and display win/lose message
                  wordlbl.setText("The secret word was: " + word);
                  System.out.println("gameover");
               
                  if (game.getLives() > 0) {
                     gamelbl.setText("YOU WIN!!!!!!!!!!");
                     System.out.println("winner");
                  } else {
                     gamelbl.setText("YOU LOST!!!!!!!!!!");
                     System.out.println("loser");
                  }
               }
            
            
            });
      
      // Disable buttons if game is already over (same logic as secondRow)
         if (game.gameOver()) {
            wordlbl.setText(word);
            System.out.println("gameover");
            for (Button buttonList : buttons) {
               buttonList.setDisable(true);
            }
         }
      
             
      
      // Add button to the HBox container
         hbox.getChildren().add(button);
      }
   
   // Return the HBox containing the buttons
      return hbox;
   }

   
   public HBox creratePlayAgainButton()
   {
      HBox hbox = new HBox(10);
      pgBtn = new Button("Play Again");
      pgBtn.setOnAction( 
         e ->
         {
            restartGame();
         });
      hbox.getChildren().addAll(pgBtn);
      hbox.setAlignment(Pos.CENTER);
      hbox.setPadding(new Insets(10));
      return hbox;
   }
   // This method creates and displays the visual elements for the word being guessed
   public VBox displayWord() {
   
   // VBox container for the labels
      VBox vbox = new VBox(10);
   
   // Call game method to fill the character array with dashes (representing unguessed letters)
      game.fillDashes();
   
   // Label to display the word with dashes (updated based on guesses)
      wordlbl = new Label(game.showDashes());
   
   // Label to display a message about the game state (win/lose)
      gamelbl = new Label(" ");
   
   // Label to display the remaining lives
      liveCounter = new Label("Lives: " + game.getLives());
   
   // Add labels to the VBox container
      vbox.getChildren().addAll(liveCounter, gamelbl, wordlbl);
      vbox.setAlignment(Pos.CENTER);
      vbox.setPadding(new Insets(10));
      return vbox;
   }

// This method creates the visual representation of the hangman based on remaining lives
   public Pane createHangman(int lives) {
   
   // Pane container for the hangman lines and shapes
      hbox = new Pane();
   
   // Line representing the bottom bar of the hangman
      bottomBar = new Line(100, 275, 200, 275);
      bottomBar.setStroke(Color.BLACK);
      bottomBar.setStrokeWidth(5);
   
   // Line representing the top bar of the hangman
      topBar = new Line(150, 0, 250, 0);
      topBar.setStroke(Color.BLACK);
      topBar.setStrokeWidth(5);
   
   // Line representing the left post of the hangman
      leftPost = new Line(150, 0, 150, 275);
      leftPost.setStroke(Color.BLACK);
      leftPost.setStrokeWidth(5);
   
   // Line representing the connection line between the top bar and the head
      connectionLine = new Line(250, 0, 250, 55);
      connectionLine.setStroke(Color.BLACK);
      connectionLine.setStrokeWidth(5);
   
   // Circle representing the head of the hangman
      head = new Circle(250, 80, 25);
      head.setFill(Color.TRANSPARENT); // Initially invisible
      head.setStroke(Color.BLACK);
      head.setStrokeWidth(5);
   
   // Line representing the body of the hangman
      body = new Line(250, 110, 250, 200);
      body.setStroke(Color.BLACK);
      body.setStrokeWidth(5); // Initially invisible
   
   // Line representing the left arm of the hangman
      leftArm = new Line(250, 150, 200, 180);
      leftArm.setStroke(Color.BLACK);
      leftArm.setStrokeWidth(5); // Initially invisible
   
   // Line representing the right arm of the hangman
      rightArm = new Line(250, 150, 300, 180);
      rightArm.setStroke(Color.BLACK);
      rightArm.setStrokeWidth(5); // Initially invisible
   
   // Line representing the left leg of the hangman
      leftLeg = new Line(250, 200, 200, 250);
      leftLeg.setStroke(Color.BLACK);
      leftLeg.setStrokeWidth(5); // Initially invisible
   
   // Line representing the right leg of the hangman
      rightLeg = new Line(250, 200, 300, 250);
      rightLeg.setStroke(Color.BLACK);
      rightLeg.setStrokeWidth(5); // Initially invisible
   
   // Line representing the right foot of the hangman
      rightFoot = new Line(300, 250, 320, 250);
      rightFoot.setStroke(Color.BLACK);
      rightFoot.setStrokeWidth(5); // Initially invisible
   
   // Line representing the left foot of the hangman
      leftFoot = new Line(180, 250, 200,250);
      leftFoot.setStroke(Color.BLACK);
      leftFoot.setStrokeWidth(5);
     
      hbox.getChildren().addAll(bottomBar,topBar,leftPost,connectionLine,head,body,leftArm,rightArm,leftLeg,rightLeg,rightFoot,leftFoot);
     
      hbox.setPadding(new Insets(10));
      updateHangman(lives);    
      return hbox;   
   }
    
// Helper method to control the visibility of hangman parts based on remaining lives
   private void updateHangman(int lives) {
      switch (lives) {
         case 8:
         // Make all lines visible except for the feet (default behavior)
            bottomBar.setVisible(true);
            topBar.setVisible(true);
            leftPost.setVisible(true);
            connectionLine.setVisible(true);
            head.setVisible(false);
            body.setVisible(false);
            rightLeg.setVisible(false);
            leftLeg.setVisible(false);
            leftArm.setVisible(false);
            rightArm.setVisible(false);
            leftFoot.setVisible(false);
            rightFoot.setVisible(false);
  
            break;
         case 7:
         // Make head visible
            head.setVisible(true);
            break;
         case 6:
         // Make body visible
            body.setVisible(true); 
            break;
         case 5:
            rightArm.setVisible(true);
            break;
         case 4:
            leftArm.setVisible(true);
            break;
         case 3:
            rightLeg.setVisible(true);
            break;
         case 2:
            leftLeg.setVisible(true);
            break;
         case 1:
            leftFoot.setVisible(true);
            break;
         case 0:
         // Make both feet visible (last life)
            rightFoot.setVisible(true);
            break;
         default:
         // Handle unexpected lives values (optional)
            break;
      }
   }

   public void restartGame()
   {
      // restarts the game
      String newWord = myWordGen.getWord();
      word = newWord;
      // gets a newword and sets and word in the game
      game = new HangmanGame(word);
      game.fillDashes();
      wordlbl.setText(game.showDashes());
      liveCounter.setText("Lives: "+game.getLives()); //update label
      updateHangman(game.getLives());//updates hangman
    
      //sets hint back to false
      hintBtn.setDisable(false);
      // sets all diffuculty checks to false
      menuItemEasy.setDisable(false);
      menuItemMedium.setDisable(false);
      menuItemHard.setDisable(false);
      
   
    //sets the whole keyboard back to false
      for (Button button : buttons) {
         button.setDisable(false);
      }
      for (Button button : buttons2) {
         button.setDisable(false);
      }
      for (Button button : buttons3) {
         button.setDisable(false);
      }
      
   
    
      gamelbl.setText("");
     
   }
   public VBox createHintButton()
   {
      VBox hbox = new VBox(10);
      hintBtn = new Button("Hint");
      hintlbl = new Label("");
      hintBtn.setOnAction(
         e -> {
            if(!game.gameOver())
            {
               int randomIndex = (int) (Math.random() * word.length());//pull a random  int from the word
               char hintChar = word.charAt(randomIndex) ;//get the rand char of the word
               game.guessLetter(hintChar);//pass into guessletter
               wordlbl.setText(game.showDashes());//shows the letter
               hintlbl.setText("Hint: " + hintChar);
               hintBtn.setDisable(true);// disables button after once use
            }
         });
        
      hbox.getChildren().addAll(hintBtn,hintlbl);
      hbox.setAlignment(Pos.CENTER);
      hbox.setPadding(new Insets(10));
      return hbox;
   }     

}
