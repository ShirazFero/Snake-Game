package Model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
    
public class AppleObject {
	
	public static BufferedImage image;
	
	/*
	 * To change this template, choose Tools | Templates
	 * and open the template in the editor.
	 */
	public AppleObject() throws IOException {	 
		super();
		this.setPartsToAdd(1);
		this.setPoints(10);
		this.setSecondsDelay(5);
		try {
			image=ImageIO.read(getClass().getResourceAsStream("/apple.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setPoints(int i) {
		// TODO Auto-generated method stub
	}
	
	private void setSecondsDelay(int i) {
		// TODO Auto-generated method stub
	}
	
	private void setPartsToAdd(int i) {
		// TODO Auto-generated method stub
	}
	
	public static BufferedImage getimage() {
		return image;
	}
}