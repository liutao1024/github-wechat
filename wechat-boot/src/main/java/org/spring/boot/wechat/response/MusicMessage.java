package org.spring.boot.wechat.response;

import org.spring.boot.wechat.message.Music;
import org.spring.boot.wechat.request.BaseMessage;

public class MusicMessage extends BaseMessage {
	// 音乐
	private Music Music;

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}
}
