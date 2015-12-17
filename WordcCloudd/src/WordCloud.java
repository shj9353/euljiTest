

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.imageio.ImageIO;



import color.ColorCombinations;
import image.CloudImageGenerator;

import words.StringProcessor;
import words.WordCount;

public class WordCloud {
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    public static final int PADDING = 30;

    public static final String TEXT = "aaa.txt";
    public static final String FILTER = "korean_filtering.txt";
    

    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("Word Cloud");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.pack();
        Insets insets = frame.getInsets();
        frame.setSize(calcScreenSize(insets));

        StringProcessor strProcessor = new StringProcessor(readFile(TEXT), filteringList(FILTER));
        CloudImageGenerator generator = new CloudImageGenerator(WIDTH, HEIGHT, PADDING);
        frame.setContentPane(new CloudViewer(generator.generateImage(strProcessor, System.currentTimeMillis())));
        frame.setVisible(true);
        
 
    }
    
  
    
    
   
    	
    

    private static String readFile(String path) throws IOException {
        FileInputStream stream = new FileInputStream(new File(path));
        try {
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            return Charset.defaultCharset().decode(bb).toString();
        } finally {
            stream.close();
        }
    }

    /**
     * This function generates a list of words to be filtered when a cloud is generated
     */
    private static HashSet<String> filteringList(String path) throws IOException {
        HashSet<String> filter = new HashSet<String>();
        Scanner scan = new Scanner(new File(path));
        while (scan.hasNext()) {
            filter.add(scan.next());
        }
        return filter;
    }

    private static Dimension calcScreenSize(Insets insets) {
        int width = insets.left + insets.right + WIDTH + PADDING * 2;
        int height = insets.top + insets.bottom + HEIGHT + PADDING * 2;
        return new Dimension(width, height);
    }
    
    
}
