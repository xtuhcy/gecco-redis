package com.geccocrawler.gecco.redis;

import org.redisson.Redisson;
import org.redisson.RedissonClient;
import org.redisson.core.RScoredSortedSet;

import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.Scheduler;

public class RedisStartScheduler implements Scheduler {

	private RScoredSortedSet<HttpRequest> set;

	public RedisStartScheduler() {
		RedissonClient redisson = Redisson.create();
		set = redisson.getScoredSortedSet("Gecco-StartScheduler-Redis-ScoreSet");
	}

	@Override
	public HttpRequest out() {
		HttpRequest request = set.pollFirst();
		return request;
	}

	@Override
	public void into(HttpRequest request) {
		set.add(System.nanoTime(), request);
	}

}
