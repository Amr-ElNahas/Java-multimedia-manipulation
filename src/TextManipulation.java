import java.io.BufferedReader;
import java.util.ArrayList;


public interface TextManipulation {
	
	//Read the given file
	public BufferedReader inputFile(String path);
	
	
	// =========================================================
	public String uniformCharactersString(BufferedReader br);
	public int wordCount(String characters);
	
	public int[] letterCount(String input);
	public ArrayList<Integer> letterCountArrayList(String input);
	
	public int[] specialWords(String input);
	public ArrayList<Integer> specialWordsArrayList(String input);
	
	public void writeOutput(String outputPath, String unifromCharactersString,  int wordCount);
	// =========================================================	
	
	/*
	 * Use either block but not BOTH
	 * 
	 */
	
	// =========================================================

	// =========================================================
	
	
	
	
	
	
	
	
	
	
	

}
