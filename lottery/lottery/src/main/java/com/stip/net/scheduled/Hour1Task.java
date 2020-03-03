package com.stip.net.scheduled;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.stip.net.main.MainData;
import com.stip.net.service.TigerService;

@Component
public class Hour1Task {
	private final Logger _log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TigerService tigerService;

	@Scheduled(cron = "0 0 0/1 * * ? ")
	public void execute() {
		updateTotalTop();
	}

	/**
	 * 更新TOP币总量缓存
	 */
	private void updateTotalTop() {
		try {
			BigDecimal total = tigerService.getTotalTop();
			MainData.totalTop = total;
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
	}
}