
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ImageToAscii {
    
    /**
     * Main method handling the input arguments at command line
     * @param args
     */
    public static void main (String[] args) {
        // If user wants help
        if (args[0].equals("help")) {
            printHelp();
        } else {
            String filePath = args[0];
            int scaling;
            if (args.length == 2) {
                scaling = Integer.parseInt(args[1]);
            } else {
                scaling = 1;
            }

            // Create ImageToAscii object
            ImageToAscii imageToAscii = new ImageToAscii(filePath);

            // Call the imageToAscii method to convert the image to ASCII
            imageToAscii.convert(scaling);

            // Print message of where ascii has been written to
            System.out.println("Output has been written to " + filePath.substring(0, filePath.indexOf("."))+".txt");
        }
    }

    /**
     * Prints help message
     */
    private static void printHelp () {
        System.out.println("Usage:");
        System.out.println("java ImageToAscii pathToYourImage.png (downscaling coefficient) <-- has to be int and is optional");
    }
    
    // The file containing the image to be converted
    File imageFile;

    // Where the buffered image will be held to access pixels and their colors
    BufferedImage image;

    // The measurments of the image
    int imageWidth;
    int imageHeight;
    
    /**
     * Constructor for the ImageToAscii object
     * 
     * Takes a file path as parameter for the image to be converted
     * 
     * @param filePath
     */
    public ImageToAscii (String filePath) {
        imageFile = new File(filePath);
        try {
            this.image = ImageIO.read(imageFile);
            if (this.image != null) {
                System.out.println("Image " + filePath + " succesfully read.");
                this.imageWidth = this.image.getWidth();
                this.imageHeight = this.image.getHeight();
            } else {
                System.out.println("Image did not read properly.");
            }
        } catch (IOException e) {
            System.out.println("Cant read image, try again.");
        }
    }
   
    public void convert (int scaling) {
        String name = imageFile.getName();
        String filePath = name.substring(0, name.indexOf("."))+".txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (int currentHeight = 0; currentHeight < imageHeight; currentHeight += 2 * scaling) {
                for (int currentWidth = 0; currentWidth < imageWidth; currentWidth += 1 * scaling) {
                    writer.print(luminanceToChar(getLuminanceOfPixel(currentWidth, currentHeight)));
                }
                writer.print("\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    private float getLuminanceOfPixel (int x, int y) {
        // taken at https://stackoverflow.com/a/21210977
        
        int color = this.image.getRGB(x, y);

        // extract each color component
        int red   = (color >>> 16) & 0xFF;
        int green = (color >>>  8) & 0xFF;
        int blue  = (color >>>  0) & 0xFF;
        
        // calc luminance in range 0.0 to 1.0; using SRGB luminance constants
        return (red * 0.2126f + green * 0.7152f + blue * 0.0722f) / 255;
    }

    private char luminanceToChar (float luminance) {
        if (luminance == 0) {
            return '#';
        } else if (luminance < 0.1) {
            return 'X';
        } else if (luminance >= 0.1 && luminance < 0.2) {
            return '%';
        } else if (luminance >= 0.2 && luminance < 0.3) {
            return '&';
        } else if (luminance >= 0.3 && luminance < 0.4) {
            return '*';
        } else if (luminance >= 0.4 && luminance < 0.5) {
            return '+';
        } else if (luminance >= 0.5 && luminance < 0.6) {
            return '/';
        } else if (luminance >= 0.6 && luminance < 0.7) {
            return '(';
        } else if (luminance >= 0.7 && luminance < 0.8) {
            return '-';
        } else if (luminance >= 0.8 && luminance < 0.9) {
            return '\'';
        } else {
            return ' ';
        }
    }
}
