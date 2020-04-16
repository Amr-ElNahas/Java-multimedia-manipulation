import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
/*
 * THIS IS THE TEXT MANIPULATION PART OF THE ASSIGMENT
 */
public class InterfaceAttempt implements TextManipulation {

	public InterfaceAttempt() {//constructor
	}

	@Override
	public BufferedReader inputFile(String path) {
		BufferedReader br=null;//initializes buffered reader
		try {
			br= new BufferedReader(new FileReader(path));//stores the text read from the file that's accesed using the file ath
		} catch (FileNotFoundException e) {//exception if file not found
			System.out.println("file not found");//prints file not found to the console
		}
		return br;//returns the buffered reader containing the text from file
	}

	@Override
	public String uniformCharactersString(BufferedReader br) {
		try{
			StringBuilder sb= new StringBuilder();//string builders are like strings which can be modified by invoking methods
			String line = null;//initializes the string that will hold the lines
			try {
				line = br.readLine();//reads next line
			} catch (IOException e) {//exception if there is no line
				System.out.println("no Input");//prints no input to the console
			}
			while(line != null){//the block of code keeps running as long as there is still text to read
				sb.append(line);//adds the read line to the stringbuilder
				sb.append("\n");//adds new line
				try {
					line=br.readLine();//reads next line
				} catch (IOException e) {//exception if there's no line in case while loop condition doesn't work
					System.out.println("no input");//prints no line to the console
				}
			}
			String s="";//initializes new string to hold the uniform characters string
			for(int i=0;i<sb.toString().length();i++){//loops over the string builder
				if((sb.toString().charAt(i)>='A')&&(sb.toString().charAt(i)<='Z')){//checks if letter is capital using ASCII code
					char b=(char) (sb.toString().charAt(i)+32);//changes uppercase letter to lowercase letter by changing ASCII code
					s+=b;//appends character to the result string
				}
				else{
					s+=sb.toString().charAt(i);//appends character to the result string
				}
			}
			return s;//returns the new uniform string
		} finally{
			try {
				br.close();//closes the stream
			} catch (IOException e) {//exception thrown if an IO error occurs
				System.out.println("no input");//prints error to console
			}
		}
	}

	@Override
	public int wordCount(String characters) {
		int a=0;//initializes int to count the words
		for(int i=0;i<characters.length()-1;i++){//loops over string except the last empty line
			if(characters.charAt(i)==' '|| characters.charAt(i)=='\n'|| characters.charAt(i)=='	'){//counts if there is a space, tab or a new line indicating another word
				a++;//increases counter
			}
		}
		return a;//returns counter
	}

	@Override
	public int[] letterCount(String input) {
		int[] a= new int[26];//initializes array of integers of size 26 corresponding to the English language
		for(int i=0;i<input.length();i++){//loops over text by characters
			if(input.charAt(i)>='a'&&input.charAt(i)<='z'){//checks whether characters are instances of the English letters
				a[input.charAt(i)-97]+=1;//updates each letter's corresponding count
			}
		}
		return a;//returns the array of integers corresponding to the english letters
	}

	@Override
	public ArrayList<Integer> letterCountArrayList(String input) {//does exactly what the previous method does only substituting an arrayList to the array of int
		ArrayList<Integer> a=new ArrayList<Integer>();
		for(int i=0;i<input.length();i++){
			if(input.charAt(i)>='a'&&input.charAt(i)<='z'){
				a.set(input.charAt(i)-97,a.get(input.charAt(i)-97)+1);
			}
		}
		return a;
	}

	@Override
	public int[] specialWords(String input) {
		int[] a= new int[7];//initializes an array of size 7 which is the types of special words+ 1 for the other art
		String[] s= input.split("\\s+");//splits string into array of strings whenever a space or a tab or a new line is detected
		for(int i=0;i<s.length;i++){//loops over the array of strings
			if(s[i].equals("the"))//checks if the string equals the
				a[0]++;//increments corresponding value in array
			else if(s[i].equals("a"))//checks if the string equals a
				a[1]++;//increments corresponding value in array
			else if(s[i].equals("and"))//checks if the string equals and
				a[2]++;//increments corresponding value in array
			else if(s[i].equals("is"))//checks if the string equals is
				a[3]++;//increments corresponding value in array
			else if(s[i].equals("are"))//checks if the string equals are
				a[4]++;//increments corresponding value in array
			else if(s[i].equals("this"))//checks if the string equals this
				a[5]++;//increments corresponding value in array
			else
				a[6]++;//increments the other corresponding value in array
		}
		return a;//returns array of integers containing amounts of special words
	}

	@Override
	public ArrayList<Integer> specialWordsArrayList(String input) {//does exactly what previous method does substituting an ArrayList in place of integer array
		ArrayList<Integer> a=new ArrayList<Integer>();
		String[] s= input.split("\\s+");
		for(int i=0;i<s.length;i++){
			if(s[i].equals("the"))
				a.set(0, a.get(0)+1);
			else if(s[i].equals("a"))
				a.set(1, a.get(1)+1);
			else if(s[i].equals("and"))
				a.set(2, a.get(2)+1);
			else if(s[i].equals("is"))
				a.set(3, a.get(3)+1);
			else if(s[i].equals("are"))
				a.set(4, a.get(4)+1);
			else if(s[i].equals("this"))
				a.set(5, a.get(5)+1);
			else
				a.set(6, a.get(6)+1);
		}
		return a;
	}

	@Override
	public void writeOutput(String outputPath, String unifromCharactersString, int wordCount) {
		PrintWriter out = null;//initializes printwriter which buffers output
		try {
			out = new PrintWriter("output2.txt");//outputs to file named output2.txt
		} catch (FileNotFoundException e) {//exception if path does not exist
			System.out.println("File not found");//prints to console file not found in case path doesnt exist
		}
		out.println(unifromCharactersString+"word count: "+wordCount);//prints buffer to file instrad of console. buffer contains the uniform text and word dount
		out.close();//closes text file to save

	}
	static int max(int[] a){//returns maximum number in array of integers. Helper to histogram methods
		int max=-1;//initializes maximum number to a minimum that definitely wont exist in array
		for(int i=0;i<a.length;i++){//loops array
			if(a[i]>=max)//compares current max with current number
				max=a[i];//updates maximum number
		}
		return max;//returns maximum number
	}
	void histogram(int[] a){//creates histogram of asterisks for letter count
		while(max(a)!=1){//as long as maximum number is a positive value there is an asterisk to be drawn somewhere
			for(int i=0;i<a.length;i++){//loops over array corresponding to each letter's count
				if(a[i]==max(a)){//checks if value's equal to the maximum
					System.out.print("*");//prints asterisk
					a[i]--;//decrements the accessed value
				}
				else{
					System.out.print(" ");//prints space if not equal to maximum value
				}
			}
			System.out.println("");//prints a new line after each for loop iteration
		}
		System.out.println("abcdefghijklmnopqrstuvwxyz");//prints x axis titles
	}
	void histogram2(int[] a){//histogram for special words count
		while(max(a)!=1){//uses the same max technique deployed by previous method
			for(int i=0;i<a.length;i++){//as previous
				if(a[i]==max(a)){//as previous
					System.out.print("*    ");//as previous but adjusts space as words are longer in length then characters
					a[i]--;//as previous
				}
				else{
					System.out.print("     ");//as previous but adjusts extra space for longer words
				}
			}
			System.out.println("");//as previous
		}
		System.out.println("the  a   and   is  are this other");//x axis titles
	}

	public static void main(String[] args) {
		InterfaceAttempt a= new InterfaceAttempt();//creates a new instance of the class
		BufferedReader b= a.inputFile("input_assign1.txt");//reads the specified text file using the method defined above
		String uniform=a.uniformCharactersString(b);//stores uniform characters string to variable "uniform" using method above
		System.out.println(uniform);//prints uniform characters to console
		int wordcount=a.wordCount(uniform);//counts word in uniform text using method defined above
		System.out.println("word count: "+wordcount);//prints the phrase word count followed by the text word count
		int[] letterArray= a.letterCount(uniform);//counts instances of each letter using method defined above
		String s="";//string that will transform array of integers to understandable count
		for(int i=0;i<letterArray.length;i++){//loops over array of integers
			switch(i){//checks the array index and labels each index with it's corresponding letter.this spans from this line until line 232
			case 0:s+="a: ";break;
			case 1:s+="b: ";break;
			case 2:s+="c: ";break;
			case 3:s+="d: ";break;
			case 4:s+="e: ";break;
			case 5:s+="f: ";break;
			case 6:s+="g: ";break;
			case 7:s+="h: ";break;
			case 8:s+="i: ";break;
			case 9:s+="j: ";break;
			case 10:s+="k: ";break;
			case 11:s+="l: ";break;
			case 12:s+="m: ";break;
			case 13:s+="n: ";break;
			case 14:s+="o: ";break;
			case 15:s+="p: ";break;
			case 16:s+="q: ";break;
			case 17:s+="r: ";break;
			case 18:s+="s: ";break;
			case 19:s+="t: ";break;
			case 20:s+="u: ";break;
			case 21:s+="v: ";break;
			case 22:s+="w: ";break;
			case 23:s+="x: ";break;
			case 24:s+="y: ";break;
			case 25:s+="z: ";break;
			}
			s+=letterArray[i];//adds the count of each letter in front of the letter
			s+='\n';//new line
		}
		System.out.println("letters count" +'\n'+s);//prints the phrase letter count then leaves a line and prints the actual letter count table
		a.histogram(letterArray);//makes histogram to display letter count
		int[] specialWords=a.specialWords(uniform);//stores special words in the text at variable special words using method defined above
		String r="";//string to hold an understandable version of the special words count
		for(int i=0;i<specialWords.length;i++){//loops over the specialwords array
			switch(i){//checks current index and labels table entry accordingly. this spans until line 249
			case 0: r+="the:	";break;
			case 1: r+="a:	";break;
			case 2: r+="and:	";break;
			case 3: r+="is:	";break;
			case 4: r+="are:	";break;
			case 5: r+="this:	";break;
			case 6: r+="others	";break;
			}
			r+=specialWords[i];//appends the count of the word to the string
			r+='\n';//goes to the next line
		}
		System.out.println("Special word count "+'\n'+r);//prints the phrase "special word count" followed by a new line and then the understandable string displaying a table of special words
		a.histogram2(specialWords);//uses method defined above to draw a histogram displaying the special words count
		a.writeOutput("output 2.txt",uniform, wordcount);//uses method defined above to write the uniform text and word count to an output file labled output2.txt
	}

}
