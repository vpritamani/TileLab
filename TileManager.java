// Vinay Pritamani, D Block Data Structures
// this is the code for a TileManager object which fulfills the requirements of the assignment 
// - as it does the necessary action when its methods, which are called from TileMain.java, 
// are referenced during a mouse click/keyboard press

import java.util.*;
import java.awt.*;

public class TileManager{
   // is the arraylist that I will reference to and adjust
   private ArrayList<Tile> tileList = new ArrayList<Tile>();
   // constructor
   public TileManager(){
       
   }
   // adds a Tile to the end of the list (and therefore the top Tile)
   public void addTile(Tile rect){
      tileList.add(rect);
   }
   // itereates through each tile and references the Tile.draw(Graphics g) to display Tile on screen
   public void drawAll(Graphics g){
      for(int i = 0; i < tileList.size(); i++){
         tileList.get(i).draw(g);
      }
   }
   // sends tile to end of the list (and top of screen)
   public void raise(int x, int y){
      if(tileList.size() != 0){ // makes sure tile list isn't empty
         int toRaise = topmostTileNumber(x, y);
         if(toRaise != -1){
            // the following 3 lines stores the tile from the spot in 
            // a temporary varialbe, removes that tile from the list, 
            // then adds that temporary variable to the back of the list
            Tile toBackOfList = tileList.get(toRaise);
            tileList.remove(toRaise);
            tileList.add(toBackOfList);
         }
      }
   }
   // sends tile to start of the list (and back of screen)
   public void lower(int x, int y){
      if(tileList.size() != 0){ // makes sure tile list isn't empty
         int toLower = topmostTileNumber(x, y);
         if(toLower != -1){
            // the following 3 lines do the same thing as the raise method
            // however it adds the temporary variable to the start of the list
            Tile toFrontOfList = tileList.get(toLower);
            tileList.remove(toLower);
            tileList.add(0, toFrontOfList);
         }
      }
   }
   // removes top-most tile of clicked pixel from the tile list
   public void delete(int x, int y){
      if(tileList.size() != 0){ // makes sure tile list isn't empty
            int toDelete = topmostTileNumber(x, y);
         if(toDelete != -1){ // checks if there is a value/tile to delete
            tileList.remove(toDelete);
         }
      }
   }
   // removes all tiles that are drawn over the clicked pixel from the tile list
   public void deleteAll(int x, int y){
      boolean done = false;
      while(!done){
         if(tileList.size() == 0){ // stops loop if tile list is empty
            break;
         }
         int toDelete = topmostTileNumber(x, y); // set to delete topmost
         if(toDelete == -1){ // checks if there is a value/tile to delete
            done = true;
         }
         else{
            tileList.remove(toDelete);
            // is not done yet as it should iterate 
            // until all values on that pixel are deleted
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
            tileList.get(i).setX( (int)(Math.random() * (width - tileList.get(i).getWidth())));
            tileList.get(i).setY((int) (Math.random() * (height - tileList.get(i).getHeight())));
         }
      }
   }
   private int topmostTileNumber(int x, int y){
      boolean found = false;
      int tileNumber = tileList.size() - 1; // iterates from end of list to start of list as 
      // we look for topmost on screen (synonymous with end of list)
      while(!found){
         if(withinHorizontal(x, tileNumber) && withinVertical(y, tileNumber)){
            // if the x and y values fall within the horizontal and vertical range of tile
            found = true;
         }
         else if(tileNumber == 0){ 
            // if we have reached end of list, we will return -1, 
            // which is handled by above methods appropriately
            tileNumber = -1;
            found = true;
         }
         else{
            // go to next value and iterate again
            tileNumber--;
         }
      }
      return tileNumber;
   }
   private boolean withinHorizontal(int x, int tileNum){
      // returns true if x is greater than/equal to leftmost value of 
      // x of tile and less than/equal to rightmost value of x of tile
      return tileList.get(tileNum).getX() <= x && tileList.get(tileNum).getX() + tileList.get(tileNum).getWidth() >= x;
   }
   private boolean withinVertical(int y, int tileNum){
      // returns true if y is greater than/equal to bottommost value of 
      // y of tile and less than/equal to topmost value of y of tile
      return tileList.get(tileNum).getY() <= y && tileList.get(tileNum).getY() + tileList.get(tileNum).getHeight() >= y;
   }
}