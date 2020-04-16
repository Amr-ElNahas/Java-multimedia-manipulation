//importing useful libraries
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class AssignmentAttempt implements assignment2 { //implements provided interface

	public AssignmentAttempt() {//constructor for assignment
	}
	public static BufferedImage resize(String inputImagePath, int scaledWidth, int scaledHeight)throws IOException {//resizes images to specified width and height
        // reads input image and stores it
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
 
        // creates output image that will store the scaled image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());
 
        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();
        return outputImage; //returns image
    }

	@Override
	public BufferedImage Blending(String imagePath1, String imagePath2, double r1, double r2) {
		BufferedImage img1 = null; //initializing the image holder for first image
		BufferedImage img2 = null; //initializing the image holder for second image
		File f1 = null;//initializing the holder for the file that contains the first image
		File f2 = null;//initializing the holder for the file that contains the second image
		try{
			  f1 = new File(imagePath1); //finds the file containing the first image using the path and stores it in our first file holder
			  img1 = ImageIO.read(f1);//reads the image from the file and stores it in our image holder
			  f2 = new File(imagePath2); //finds the file containing the second image using the path and stores it in our first file holder
			  img2 = ImageIO.read(f2);//reads the image from the file and stores it in our image holder
			}catch(IOException e){// exception in case file not found
			  System.out.println("file not found");// prints file not found
			}
		int height;//where height of new image will be stored
		int width;//where width of new image will be stored
		if(img2.getHeight()>img1.getHeight()){//checking what the max height of the 2 images is and storing it so no losses occur
			height=img2.getHeight();
		}
		else{
			height=img1.getHeight();
		}
		if(img2.getWidth()>img1.getWidth()){//checking what the max height of the 2 images is and storing it so no losses occur
			width=img2.getWidth();
		}
		else{
			width=img1.getWidth();
		}
		BufferedImage resizedImg1=null;//to store image 1 after resizing
		try {
			resizedImg1=resize(imagePath1,width,height);//resize image 1 and store
		} catch (IOException e) {
			System.out.println("img 1 not found");//prints exception if image1 not found
		}
		BufferedImage resizedImg2=null;//to store image 2 after resizing
		try {
			resizedImg2=resize(imagePath2,width,height);//resize image 2 and store
		} catch (IOException e) {
			System.out.println("img 2 not found");//prints exception if image2 not found
		}
		int imagetype1=img1.getType();//getting type of new image
		BufferedImage resultBlend= new BufferedImage(width, height, imagetype1);//placeholder to store new image
		for(int y=0;y<height;y++){//loops over pixel from top to bottom
			for(int x=0;x<width;x++){//loops over every pixel from left to right (nested for loops go over every pixel in the new image)
				  int img1pixel = resizedImg1.getRGB(x,y); //gets red, green and blue components from pixel for image 1
				  int img1alpha = (img1pixel>>24)&0xff;//extracts alpha component from pixel pixel for image 1
				  int img1red = (img1pixel>>16)&0xff;//extracts red component from pixel pixel for image 1
				  int img1green = (img1pixel>>8)&0xff;//extracts green component from pixel pixel for image 1
				  int img1blue = img1pixel&0xff;//extracts blue component from pixel pixel for image 1
				  int img2pixel = resizedImg2.getRGB(x,y); //gets red, green and blue components from pixel for image 2
				  int img2alpha = (img2pixel>>24)&0xff;//extracts alpha component from pixel pixel for image 2
				  int img2red = (img2pixel>>16)&0xff;//extracts red component from pixel pixel for image 2
				  int img2green = (img2pixel>>8)&0xff;//extracts green component from pixel pixel for image 2
				  int img2blue = img2pixel&0xff;//extracts blue component from pixel pixel for image 2
				  int imgresultalpha=(int) ((img1alpha*r1)+(img2alpha*r2));//calculating new blended alpha component
				  int imgresultred=(int) ((img1red*r1)+(img2red*r2));//calculating new blended red component
				  int imgresultgreen=(int) ((img1green*r1)+(img2green*r2));//calculating new blended green component
				  int imgresultblue=(int) ((img1blue*r1)+(img2blue*r2));//calculating new blended blue component
				  int imgresultpixel=(imgresultalpha<<24) | (imgresultred<<16) | (imgresultgreen<<8) | imgresultblue;//creating pixel with new colours
				  resultBlend.setRGB(x, y, imgresultpixel);//assigning new pixel to created image
			}
		}
		return resultBlend;//returns result image
		
	}

	@Override
	public void downsampling(String inputPath, String outputPath) {
		File f=new File(inputPath);//reads folder containing frames
		ArrayList<BufferedImage> frames=new ArrayList<BufferedImage>();//initializes array list to hold frames
		for (final File fileEntry : f.listFiles()) {//loop to read every file in folder
			try {
				BufferedImage img=ImageIO.read(fileEntry);//reads image
				frames.add(img);//adds image to array list
			} catch (IOException e) {//catches exception if image is not found
				System.out.println("invalid read");//prints invalid read if it is not an image
			}
		}
		ArrayList<BufferedImage> resultframes=new ArrayList<BufferedImage>();//initializes array list to hold new altered frames
		for(BufferedImage images:frames){//loops over all saved frames
			BufferedImage resultimage= new BufferedImage(images.getWidth(), images.getHeight(), images.getType());//initializes an image holder to hold each new image after alteration
			for(int x=0;x<images.getWidth();x+=2){//loops over width of each image in intervals of 2 for spatial sampling
				for(int y=0;y<images.getHeight();y+=2){//loops over height of each image in intervals of 2 for spatial sampling
					int pixel=images.getRGB(x, y);//gets RGB value of current pixel
					resultimage.setRGB(x, y, pixel);//sets RGB value of the same pixel in the result image
					resultimage.setRGB((x+1)<resultimage.getWidth()?x+1:x, y, pixel);//sets RGB value of the next pixel horizontally if it exists in the result image to the previous pixel value
					resultimage.setRGB(x, (y+1)<resultimage.getHeight()?y+1:y, pixel);//sets RGB value of the next pixel vertically if it exists in the result image to the previous pixel value
					resultimage.setRGB((x+1)<resultimage.getWidth()?x+1:x, (y+1)<resultimage.getHeight()?y+1:y, pixel);//sets RGB value of the next pixel horizontally and vertically if they exist in the result image to the previous pixel value
					
				}
			}
			resultframes.add(resultimage);//adds altered image to result frame
		}
		for(int i=0;i<resultframes.size();i++)//loops over altered frames again for temporal down-sampling
			if(i%2==1){//for every second frame
				resultframes.set(i, resultframes.get(i-1));//sets values to previous frame
			}
		for(int j=0;j<resultframes.size();j++){
			File directoryname = new File(outputPath);//readies output file
			directoryname.mkdir();//makes it a directory so we can add multiple files to it
			String fileName = "frame" + j + ".jpg";//name of every new image
			File file = new File(directoryname + "/" + fileName);//creates file to hold each image
			try {
				ImageIO.write(resultframes.get(j), "jpg", file);//writes new frame
			} catch (IOException e) {
				System.out.println("can't write images");//says file cannot be written in console if error occurs
			}
		}
				

	}

	@Override
	public void imageEnhancement(String inputPath, String outputPath, int key) {
		BufferedImage img = null; //initializing the image holder for input image
		File f = null;//initializing the holder for the file that contains the input image
		try{
			  f = new File(inputPath); //finds the file containing the image using the path and stores it in our file holder
			  img = ImageIO.read(f);//reads the image from the file and stores it in our image holder
			}catch(IOException e){// exception in case file not found
			  System.out.println("file not found");// prints file not found
			}
		File output=new File(outputPath);//initializes output file that will contain the enhanced image
		switch(key){//checks which enhancement should be performed
		case 1://+ 50 enhancement
			for(int x=0;x<img.getWidth();x++){//loops over image pixels from left to right
				for(int y=0;y<img.getHeight();y++){//loops over image pixels from top to bottom
					int pixel=img.getRGB(x, y);//gets old RGB values
					int imgalpha = (pixel>>24)&0xff;//extracts alpha component from each pixel from image
					int imgred = (pixel>>16)&0xff;//extracts red component from each pixel from image
					int imggreen = (pixel>>8)&0xff;//extracts green component from each pixel from image
					int imgblue = pixel&0xff;//extracts blue component from each pixel from image 
					if(imgred+50>=255)//checks that the enhanced image's red component is still within correct range after enhancement and corrects it if it isn't
						imgred=255;
					else
						imgred+=50;
					if(imggreen+50>=255)//checks that the enhanced image's green component is still within correct range after enhancement and corrects it if it isn't
						imggreen=255;
					else
						imggreen+=50;
					if(imgblue+50>=255)//checks that the enhanced image's blue component is still within correct range after enhancement and corrects it if it isn't
						imgblue=255;
					else
						imgblue+=50;
					int imgresultpixel=(imgalpha<<24) | (imgred<<16) | (imggreen<<8) | imgblue;//creates new enhanced pixel
					img.setRGB(x, y, imgresultpixel);//sets the result image pixel to its corresponding created pixel
				}
			}
			try {
				ImageIO.write(img, "png", output);//writes image as png to output file
			} catch (IOException e3) {
				System.out.println("cannot write file");//prints error if writing cannot be performed
			}break;//breaks switch 
		case 2://^2 enhancement
			for(int x=0;x<img.getWidth();x++){//loops over image from left to right
				for(int y=0;y<img.getHeight();y++){//loops over image from top to bottom
					int pixel=img.getRGB(x, y);//gets RGB values from pixel
					int imgalpha = (pixel>>24)&0xff;//extracts alpha component from each pixel from image
					int imgred = (pixel>>16)&0xff;//extracts red component from each pixel from image
					int imggreen = (pixel>>8)&0xff;//extracts green component from each pixel from image
					int imgblue = pixel&0xff;//extracts blue component from each pixel from image
					if(imgred*imgred>=255)// enhances the red component and checks if it is still within correct range (0-255) and corrects it if it isn't
						imgred=255;
					else
						imgred*=imgred;
					if(imggreen*imggreen>=255)// enhances the green component and checks if it is still within correct range (0-255) and corrects it if it isn't
						imggreen=255;
					else
						imggreen*=imggreen;
					if(imgblue*imgblue>=255)// enhances the blue component and checks if it is still within correct range (0-255) and corrects it if it isn't
						imgblue=255;
					else
						imgblue*=imgblue;
					int imgresultpixel=(imgalpha<<24) | (imgred<<16) | (imggreen<<8) | imgblue;//creates a new pixel with the enhanced RGB values
					img.setRGB(x, y, imgresultpixel);//replaces the old pixel with the new one
				}
			}
			try {
				ImageIO.write(img, "png", output);//writes image as png to output file
			} catch (IOException e2) {
				System.out.println("cannot write file");//prints error if writing cannot be performed
			}break;//breaks switch
		case 3://sqrt enhancement
			for(int x=0;x<img.getWidth();x++){//loops over image from left to right
				for(int y=0;y<img.getHeight();y++){//loops over image from top to bottom
					int pixel=img.getRGB(x, y);//gets RGB values from pixel
					int imgalpha = (pixel>>24)&0xff;//extracts alpha component from each pixel from image
					int imgred = (pixel>>16)&0xff;//extracts red component from each pixel from image
					int imggreen = (pixel>>8)&0xff;//extracts green component from each pixel from image
					int imgblue = pixel&0xff;//extracts blue component from each pixel from image
					imgred=(int)Math.sqrt(imgred);//enhances the red component of image making sure the new value stay an integer to prevent overflow
					imggreen=(int)Math.sqrt(imggreen);//enhances the green component of image making sure the new value stay an integer to prevent overflow
					imgblue=(int)Math.sqrt(imgblue);//enhances the blue component of image making sure the new value stay an integer to prevent overflow
					int imgresultpixel=(imgalpha<<24) | (imgred<<16) | (imggreen<<8) | imgblue;//creates a new pixel with the enhanced RGB values
					img.setRGB(x, y, imgresultpixel);//replaces the old pixel with the new one
				}
			}
			try {
				ImageIO.write(img, "png", output);//writes enhanced image as png to output file
			} catch (IOException e1) {
				System.out.println("cannot write file");//prints error if writing cannot be performed
			}break;//breaks switch
			default:// if key is not 1 or 2 or 3 nothing is done to the image
			try {
				ImageIO.write(img, "png", output);//writes same image as png to output file
			} catch (IOException e) {
				System.out.println("cannot write file");//prints error if writing cannot be performed
			}break;//breaks switch
		}

	}

	public static void main(String[] args) {
		AssignmentAttempt a=new AssignmentAttempt();//Initializes instance of assignment
		BufferedImage blendedimage=a.Blending("input.png", "text.png", 0.5, 0.5);//part1.1
		File fresult=null;//initializes file for output
		try{
			   fresult = new File("Output image blended.png");//readies output file
			  ImageIO.write(blendedimage, "png", fresult);//writes the blended image to output file
			}catch(IOException e){//exception thrown if blended image doesn't exist
			  System.out.println(e);//prints exception trace
			}
		BufferedImage blendedimage2=a.Blending("input.png", "text.png", 0.6, 0.4);//part1.2 (60% for vegetable image and 40% for watermark image is my optimum choice for ratios)
		File fresult2=null;//initializes file for output
		try{
			   fresult2 = new File("Output image blended2.png");//readies output file
			  ImageIO.write(blendedimage2, "png", fresult2);//writes the blended image to output file
			}catch(IOException e){//exception thrown if blended image doesn't exist
			  System.out.println(e);//prints exception trace
			}
		a.downsampling("ImageSeq", "imageSeqDownsampled1");//part 2 from step 1 to 3
		a.downsampling("imageSeqDownsampled1", "imageSeqDownsampled2");//part 2 step 4
		a.imageEnhancement("input.png", "first enhancement.png", 1);//part3a
		a.imageEnhancement("input.png", "second enhancement.png", 2);//part3b
		a.imageEnhancement("input.png", "third enhancement.png", 3);//part3c
	}

}
