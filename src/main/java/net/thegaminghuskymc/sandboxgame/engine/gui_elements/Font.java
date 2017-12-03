package net.thegaminghuskymc.sandboxgame.engine.gui_elements;

import net.thegaminghuskymc.sandboxgame.engine.Window;
import org.joml.Vector2d;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.*;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_I;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.opengl.GL11.*;

public class Font {
    ArrayList<Character> chars;
    ArrayList<Character> text = new ArrayList<>();
    List<String> strings;
    File fontTga;
    Window window;
    int testID = 32;
    boolean test = false;
    double curTime;
    double timeout = 0.5;

    public Font(Window win){
        window = win;
    }

    public void loadFont(String fontName){
        File fontXml = new File("E:/workspace/Husky-s-Sandbox-Game_works/src/main/resources/fonts/roboto.fnt");
        fontTga = new File("E:/workspace/Husky-s-Sandbox-Game_works/src/main/resources/fonts/roboto.png");
        strings = new ArrayList<>();
        chars = new ArrayList<>();
        try {
            strings = Files.readAllLines(fontXml.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String s : strings){
            if(s.startsWith("char ")){
                String[] split_uncleaned = s.split(" ");
                ArrayList<String> split = new ArrayList<>();
                for(String s1 : split_uncleaned){
                    if(!s1.isEmpty()){
                        split.add(s1);
                    }
                }
//                System.out.println("-"+Integer.valueOf(split.get(1).substring(3))+", "+ Integer.valueOf(split.get(2).substring(2))+", "+ Integer.valueOf(split.get(3).substring(2))+", "+ Integer.valueOf(split.get(4).substring(6))+"-"+Integer.valueOf(split.get(6).substring(8))+", "+ Integer.valueOf(split.get(5).substring(7))+"-"+Integer.valueOf(split.get(7).substring(8)));
//                System.out.println(Integer.valueOf(split.get(1).substring(3))+", "+ Integer.valueOf(split.get(2).substring(2))+", "+ Integer.valueOf(split.get(3).substring(2))+", "+ (Integer.valueOf(split.get(4).substring(6))-Integer.valueOf(split.get(6).substring(8)))+", "+ (Integer.valueOf(split.get(5).substring(7))-Integer.valueOf(split.get(7).substring(8))));
                Character tmp = new Character(Integer.valueOf(split.get(1).substring(3)), Integer.valueOf(split.get(2).substring(2)), Integer.valueOf(split.get(3).substring(2)), Integer.valueOf(split.get(4).substring(6))-Integer.valueOf(split.get(6).substring(8)), Integer.valueOf(split.get(5).substring(7))-Integer.valueOf(split.get(7).substring(8)));
                chars.add(tmp);
            }
        }
    }

    void generateText(String text){
        generateChars();
        this.text = new ArrayList<>();
        for(int i = 0; i < text.length(); i++){
            this.text.add(chars.get(getCharWithID((int)text.charAt(i))));
        }
        //System.out.println("Current text length: "+this.text.size()+"; input string length: "+text.length());
    }

    public void showText(Vector2d pos){
        if(test) {
            if (window.isKeyPressed(GLFW_KEY_I)) {
                if (glfwGetTime() >= curTime) {
                    curTime = glfwGetTime() + timeout;
                    testID++;
                    System.out.println("TestID: "+testID);
                    test();
                }
            }
        }
        int translatedX = 0;
        int translatedY = 0;
        GL11.glPushMatrix();
        GL11.glTranslated(pos.x, pos.y, 0);
        for (Character aText : text) {
            if (translatedX >= window.getWidth()) {
                translatedX = 0;
                GL11.glTranslated(-window.getWidth(), -50, 0);
            }
            GL11.glTranslated(aText.size.x, 0, 0);
            translatedX += aText.size.x;
            aText.display();
        }
        GL11.glPopMatrix();
    }

    private int getCharWithID(int id){
        for(int i = 0; i < chars.size(); i++){
            if(chars.get(i).getID() == id-1){
                //System.out.println("Char with id "+id+" is in index "+i);
                return i;
            }
        }
        return 0;
    }

    public void test(){
        test = true;
        generateChars();
        text = new ArrayList<>();
        if(getCharWithID(testID) == 0){
            testID = 32;
        }
        text.add(chars.get(getCharWithID(testID)));
    }

    private void generateChars(){
        BufferedImage image = null;
        try {
            image = ImageIO.read(fontTga);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (image != null) {
            for (Character aChar : chars) {
                int[] pixels = new int[(int) (aChar.size.x * aChar.size.y)];
                image.getRGB((int) aChar.pos.x, (int) aChar.pos.y, (int) aChar.size.x, (int) aChar.size.y, pixels, 0, (int) aChar.size.x);
                ByteBuffer buffer = BufferUtils.createByteBuffer((int) aChar.size.x * (int) aChar.size.y * 4);
                for (int y = 0; y < (int) aChar.size.y; y++) {
                    for (int x = 0; x < (int) aChar.size.x; x++) {
                        int pixel = pixels[y * (int) aChar.size.x + x];
//                        System.out.println("Current pixel int is: "+pixel);
//                        System.out.println("current pixel is: "+(byte)((pixel >> 16)&0xFF)+","+(byte)((pixel >> 8)&0xFF)+", "+(byte)((pixel)&0xFF)+", "+(byte)((pixel >> 24) & 0xFF));
                        //return;
                        buffer.put((byte) ((pixel >> 16) & 0xFF)); // RED
                        buffer.put((byte) ((pixel >> 8) & 0xFF)); // GREEN
                        buffer.put((byte) (pixel & 0xFF)); // BLUE
                        buffer.put((byte) ((pixel >> 24) & 0xFF)); // ALPHA
                    }
                }
                buffer.flip();
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, (int) aChar.size.x, (int) aChar.size.y, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
                int textureID = glGenTextures();
                glBindTexture(GL_TEXTURE_2D, textureID);
                aChar.setBitmap(textureID);
                //System.out.println("Texture id for "+chars.get(i).getID()+" is "+textureID);
            }
        }else{
            System.out.println("Fonts aren't loaded!");
        }
    }
}
