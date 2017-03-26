package com.geccocrawler.gecco.redis;

import org.redisson.Redisson;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.Scheduler;

public class RedisStartScheduler implements Scheduler {

	private RScoredSortedSet<HttpRequest> set;

	public RedisStartScheduler(String address) {
		Config config = new Config();
		config.useSingleServer().setAddress(address);
		RedissonClient redisson = Redisson.create(config);
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
