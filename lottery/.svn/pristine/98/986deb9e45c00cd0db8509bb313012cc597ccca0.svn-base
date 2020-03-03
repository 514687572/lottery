package com.stip.net.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stip.net.imessage.IMWebSocketHandler;

import net.sf.json.JSONObject;

public abstract class AbstractJob {
	protected final Logger _log = LoggerFactory.getLogger(this.getClass());

	public abstract void runImpl(IMWebSocketHandler handler, String userId, JSONObject params, Object... object);
}