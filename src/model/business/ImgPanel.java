package model.business;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
* @author Cianc
* @version 创建时间：2019年5月2日 下午3:40:43
* @ClassName ImgPanel
* @Description 类描述
*/
public class ImgPanel extends JPanel{
	ImageIcon icon;
	Image img;
	public ImgPanel(String imgPath) {
		if(imgPath != null) {
			icon = new ImageIcon(imgPath);
			img=icon.getImage();
		} else {
			this.setBackground(Color.WHITE);
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0,this.getWidth(), this.getHeight(), this);
	}
}
