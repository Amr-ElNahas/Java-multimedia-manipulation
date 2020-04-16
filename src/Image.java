import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/*
 * THIS IS THE IMAGE MANIPULATION PART OF THE ASSIGNMENT
 */

public class Image extends JFrame implements ImageManipulation{ //jframe for gui

	public Image() { //constructor
		setSize(1000, 400);//size of window
        setResizable(false);//window dimensions are fixed
        setVisible(true);//window is visible
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//the cross closes the window
        setTitle("before and after greyscaling");//title of the window
	}
	

	@Override
	public BufferedImage convertGreyscale(String path) {
		BufferedImage img = null; //initializing the image holder
		File f = null;//initializing the holder for the file that contains the image
		try{
			  f = new File(path); //finds the file containing the image using the path and stores it in your file holder
			  img = ImageIO.read(f);//reads the image from the file and stores it in our image holder
			}catch(IOException e){// exception in case file not found
			  System.out.println("file not found");// prints file not found
			}
		int width = img.getWidth();//gets width of image
		int height = img.getHeight();//gets height of image
		for(int y = 0; y < height; y++){//loops over every pixel from top to bottom
			  for(int x = 0; x < width; x++){//loops over every pixel from left to right (nested for loops go over every pixel in the image)
				  int p = img.getRGB(x,y); //gets red, green and blue components from pixel
				  int a = (p>>24)&0xff;//extracts alpha component from pixel
				  int r = (p>>16)&0xff;//extracts red component from pixel
				  int g = (p>>8)&0xff;//extracts green component from pixel
				  int b = p&0xff;//extracts blue component from pixel
				  int avg = (r+g+b)/3;// calculates average of rgb colors which returns greyscale value
				  p = (a<<24) | (avg<<16) | (avg<<8) | avg;//calculates the new pixel rgb value
				  img.setRGB(x, y, p);//sets new rgb value to the pixel
			  }
			}
		return img;//returns the greyscaled image
	}

	public static void main(String[] args) {
		//Part a starts
		Image a=new Image();//initializes an instance of the class
		File f=null;//initializes file for output
		try{
			   f = new File("Output image.jpg");//readies output file
			  ImageIO.write(a.convertGreyscale("Image_assign1.jpg"), "jpg", f);//writes the greyscaled image to output file
			}catch(IOException e){//exception thrown if greyscaled image doesn't exist
			  System.out.println(e);//prints exception trace
			}
		BufferedImage img = null;//initializes the before image holder
		try{
			  f = new File("Image_assign1.jpg");//finds file from path
			  img = ImageIO.read(f);//reads image from file
			}catch(IOException e){//exception thrown if filepath doesn't exist
			  System.out.println(e);//prints exception trace
			}
		//part a ends and part b starts
		ImageIcon before=new ImageIcon(img);//before image
		ImageIcon after=new ImageIcon(a.convertGreyscale("Image_assign1.jpg"));//after image
		JLabel imageLabel1 = new JLabel(before);//jlabel which will hold before image
		imageLabel1.setBounds(10, 10, 400, 400);//coordinates and size of the label
		imageLabel1.setVisible(true);//label is not hidden
		a.add(imageLabel1,BorderLayout.WEST);//adds the label to the instance window on the left
		JLabel imageLabel2 = new JLabel(after);//label which will hold after image
		imageLabel2.setBounds(10, 10, 400, 400);//coordinates and size of the label
		imageLabel2.setVisible(true);//label is not hidden
		a.add(imageLabel2,BorderLayout.EAST);//adds the label to the instance window on the right
		a.revalidate();//updates instance window
		
	
		

	}

}
