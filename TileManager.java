// Vinay Pritamani, D Block Data Structures
// this is the code for a TileManager object which fulfills the requirements of the assignment 
// - as it does the necessary action when its methods, which are called from TileMain.java, 
// are referenced during a mouse click/keyboard press

import java.util.*;
import java.awt.*;

public class TileManager{
   // is the arraylist that I will reference to and adjust
   private ArrayList<Tile> tileList = new ArrayList<Tile>();
   // temporary integer that is used throughout to temporarily store values
   private int temporary;
   private Tile tempT; // is used as a temporary tile variable for testing conditions
   public TileManager(){} // constructor
   public void addTile(Tile rect){ // adds a Tile to the end of the list
      tileList.add(rect);
   }
   // itereates through each tile and references the Tile.draw(Graphics g) to display Tile on screen
   public void drawAll(Graphics g){
      for(int i = 0; i < tileList.size(); i++){
         tileList.get(i).draw(g);
      }
   }
   public void raise(int x, int y){ // sends tile to end of the list (and top of screen)
      if(tileList.size() != 0){ // makes sure tile list isn't empty
         temporary = topmostTileNumber(x, y);
         if(temporary != -1){
            // the following 3 lines stores the tile from the spot in 
            // a temporary varialbe, removes that tile from the list, 
            // then adds that temporary variable to the back of the list
            tempT = tileList.get(temporary);
            tileList.remove(temporary);
            tileList.add(tempT); 
         }
      }
   }
   public void lower(int x, int y){ // sends tile to start of the list (and back of screen)
      if(tileList.size() != 0){ // makes sure tile list isn't empty
         temporary = topmostTileNumber(x, y);
         if(temporary != -1){
            // the following 3 lines do the same thing as the raise method
            // however it adds the temporary variable to the start of the list
            tempT = tileList.get(temporary);
            tileList.remove(temporary);
            tileList.add(0, tempT);
         }
      }
   }
   public void delete(int x, int y){ // removes top-most tile of clicked pixel from the tile list
      if(tileList.size() != 0){ // makes sure tile list isn't empty
         temporary = topmostTileNumber(x, y);
         if(temporary != -1){ // checks if there is a value/tile to delete
            tileList.remove(temporary);
         }
      }
   }   
   public void deleteAll(int x, int y){ // removes all tiles that are drawn over the clicked pixel
      while(tileList.size() != 0){ // stops loop if tile list is empty
         temporary = topmostTileNumber(x, y); // set to delete topmost
         if(temporary == -1){ // checks if there is a value/tile to delete
            break;
         }
         else{ // is not done yet as it should iterate until all values on that pixel are deleted
            tileList.remove(temporary);
         }
      }
   } 
   public void shuffle(int width, int height){
      if(tileList.size() != 0){ // makes sure tile list isn't empty
         Collections.shuffle(tileList); // shuffles order
         for(int i = 0; i < tileList.size(); i++){ // iterates through each tile
            // the following get a random value between 0 and 1 (inclusive)
            // then multiply by the acceptable range of x/y values
            // the range is found because the tile cannot go off the screen,
            // so it subtracts screen width by tile width (as we set the top left coordinate)
            tileList.get(i).setX((int) (Math.random() * (width - tileList.get(i).getWidth())));
            tileList.get(i).setY((int) (Math.random() * (height - tileList.get(i).getHeight())));
         }
      }
   }
   private int topmostTileNumber(int x, int y){
      boolean found = false;
      int tileNum = tileList.size() - 1; // iterates from end of list to start of list as 
      // we look for topmost on screen (synonymous with end of list)
      while(!found){
         tempT = tileList.get(tileNum); // set temp tile to current tile being tested
         // following are true if x/y is greater than/equal to leftmost/bottommost value of 
         // x/y of tile and less than/equal to rightmost/topmost value of x of tile
         boolean withinX = tempT.getX() <= x && tempT.getX() + tempT.getWidth() >= x;
         boolean withinY = tempT.getY() <= y && tempT.getY() + tempT.getHeight() >= y;
         if(withinX && withinY){
            found = true; // if the x and y values fall within the horizontal and vertical range of tile
         }
         else if(tileNum == 0){ 
            tileNum = -1; // if we have reached end of list, we will return -1, 
            found = true; // which is handled by above methods appropriately
         }
         else{
            tileNum--; // go to next value and iterate again
         }
      }
      return tileNum;
   }
}
