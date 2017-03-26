package com.geccocrawler.gecco.redis;

import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RLock;
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
		
		RBloomFilter<String> bf = redisson.getBloomFilter("bf");
		bf.add("a");
		RAtomicLong aLong = redisson.getAtomicLong("test");
		aLong.getAndSet(2);
		
		RCountDownLatch rcdl = redisson.getCountDownLatch("cdl");
		rcdl.countDown();
		
		RLock rlock = redisson.getLock("lock");
		rlock.lock();
		rlock.unlock();
		
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
