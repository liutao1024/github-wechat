package org.spring.boot.wechat.response;

import org.spring.boot.wechat.message.Image;

public class ImageMessage extends NewsMessage {
	private Image Image;

	public Image getImage() {
		return Image;
	}

	public void setImage(Image image) {
		Image = image;
	}
}
