package org.spring.boot.wechat.response;

import org.spring.boot.wechat.entity.Voice;

public class VoiceMessage extends BaseMessage {
	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		Voice = voice;
	}
}
