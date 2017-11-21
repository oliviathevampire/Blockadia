package net.thegaminghuskymc.sandboxgame.engine;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;

import blocks.launcher.annotation.Side;
import blocks.launcher.annotation.SideOnly;

@SideOnly(Side.CLIENT)
public class Screenshot {
	
	private static final String SCREENSHOT_PATH = System.getenv("APPDATA") + "\\blocks\\screenshots\\";
	
	public static void takeScreenshot() {
		glReadBuffer(GL_FRONT);
		int width = Display.getDisplayMode().getWidth();
		int height= Display.getDisplayMode().getHeight();
		int bpp = 4;
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
		glReadPixels(0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd_HH-mm-ss");
				Date date = new Date();
				
				File file = new File(SCREENSHOT_PATH + dateFormat.format(date) + ".png");
				String format = "PNG";
				BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				   
				for(int x = 0; x < width; x++) 
				{
				    for(int y = 0; y < height; y++)
				    {
				        int i = (x + (width * y)) * bpp;
				        int r = buffer.get(i) & 0xFF;
				        int g = buffer.get(i + 1) & 0xFF;
				        int b = buffer.get(i + 2) & 0xFF;
				        image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
				    }
				}
				   
				try {
					file.mkdirs();
					file.createNewFile();
				    ImageIO.write(image, format, file);
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
				
				System.out.println("Screenshot success.");
				System.out.println(file);
			}
			
		}).start();
	}

}
