package org.spring.boot.wechat.response;

import org.spring.boot.wechat.message.Video;

public class VideoMessage extends BaseMessage {
	private Video Video;

	public Video getVideo() {
		return Video;
	}

	public void setVideo(Video video) {
		Video = video;
	}
}
