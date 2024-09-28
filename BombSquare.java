import java.util.*;

public class BombSquare extends GameSquare
{
	private boolean thisSquareHasBomb = false;
	public static final int MINE_PROBABILITY = 10;
	private boolean checked = false; 

	public BombSquare(int x, int y, GameBoard board)
	{
		super(x, y, "images/blank.png", board);

		Random r = new Random();
		thisSquareHasBomb = (r.nextInt(MINE_PROBABILITY) == 0);
	}
	
	/**
	* Uses for loops to iterate through each square around the given square
	* Checks if these squares have bombs
	* Returns the number of squares checked that have bombs
	 */
	private int countBombs(int x, int y) {
    	int numOfBombs = 0;
    	for (int row = x - 1; row <= x + 1; row++) {
			for (int col = y - 1; col <= y + 1; col++) {
				BombSquare b = (BombSquare) board.getSquareAt(row, col);
            		if (b != null && b.thisSquareHasBomb){ 
						numOfBombs++;
					}
            }
        }
		return numOfBombs;
    }

	/**
	* If the square has not yet been checked, count num of bombs around it
	* If 0, clear all bombs around it
	* Calls itself through recursion to keep clearing empty squares
	* If not 0, set square to number of bombs around it
	 */
	private void expandEmptySquares(int x, int y){
		BombSquare b = (BombSquare) board.getSquareAt(x, y);
		if (b.checked = false && b != null){
			int num = countBombs(x, y);
			if (num == 0){
				num++; 
				b.checked = true;
				for (int row = x - 1; row <= x + 1; row++) {
					for (int col = y - 1; col <= y + 1; col++) {
						b.setImage("images/0.png");
						expandEmptySquares(row, col);
					}
				}
			}else{
				num++;
				b.checked = true;
				b.setImage("images/" + num + ".png");
				return;
			}
		}
	}
	/**
	* If square user clicked has bomb, show bomb and print game over
	* If not, count number of bombs around it
	* Change icon accordingly
	* If no bombs around it, call expandEmptySquares()
	*/
	public void clicked()
	{
		if(thisSquareHasBomb){
			setImage("images/bomb.png");
			System.out.println("Bomb found, game over");
		} else{
			int numOfBombs = countBombs(xLocation, yLocation);
			setImage("images/" + numOfBombs + ".png");
			if (numOfBombs == 0){
				expandEmptySquares(xLocation, yLocation);
			}

		}
	}
}

