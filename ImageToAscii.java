
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ImageToAscii {
    public static void main (String[] args) {
    
        String filePath = args[0];

        ImageToAscii imageToAscii = new ImageToAscii(filePath);
        imageToAscii.imageToAscii();
    }

    
    File imageFile;
    BufferedImage image;
    char[][] characterMatrix;
    int imageWidth;
    int imageHeight;
    
    public ImageToAscii (String filePath) {
        imageFile = new File(filePath);
        try {
            this.image = ImageIO.read(imageFile);
            if (this.image != null) {
                System.out.println("Image succesfully read.");
                System.out.println();
                imageWidth = this.image.getWidth();
                imageHeight = this.image.getHeight();
                characterMatrix = new char[imageHeight][imageWidth];

            } else {
                System.out.println("Image did not read properly.");
            }
            
        } catch (IOException e) {
            System.out.println("Cant read image, try again.");
        }
        
    }
   

    public void imageToAscii () {
        float currentLuminance;
        for (int currentHeight = 0; currentHeight < imageHeight; currentHeight++) {
            for (int currentWidth = 0; currentWidth < imageWidth; currentWidth++) {
                
                currentLuminance = getLuminanceOfPixel(currentHeight, currentWidth);
                
                writeCharToMatrix(luminanceToChar(currentLuminance), currentWidth, currentHeight);

            }
        }

        String name = imageFile.getName();

        String filePath = name.substring(0, name.indexOf("."))+".txt";
        
       
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (int currentHeight = 0; currentHeight < imageHeight; currentHeight+=2) {
                for (int currentWidth = 0; currentWidth < imageWidth; currentWidth++) {
                    writer.print(characterMatrix[currentWidth][currentHeight]);
                }
                writer.print("\n");
            }
            
            
            
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
        
        System.out.println("Output has been written to " + filePath);



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

    private void writeCharToMatrix (char c, int xPos, int yPos) {
        characterMatrix[yPos][xPos] = c;
    }




}
