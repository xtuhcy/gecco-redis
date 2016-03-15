# gecco-redis
Gecko crawler supports distributed by redis

gecco爬虫通过将初始的start request放入redis的队列进行分布式抓取，sprider线程首先从redis中获取初始抓取请求，在线程内完成子请求的抓取任务。完成后再从redis中获取下一个初始请求。这样实现了初始请求的深度优先策略，子请求的广度优先策略。

建议分布式抓取策略下，抓取线程的数量要小于等于初始请求（start request）的数量。

##Download

	<dependency>
	    <groupId>com.geccocrawler</groupId>
	    <artifactId>gecco-redis</artifactId>
	    <version>1.0.6</version>
	</dependency>

##QuickStart

	//每台服务器启动1个GeccoEngine服务
	//启动GeccoEngine时，设置RedisStartScheduler
	GeccoEngine.create()
	.scheduler(new RedisStartScheduler("127.0.0.1:6379"))
	.classpath("com.geccocrawler.gecco.demo")
	//开始抓取的页面地址
	.start("https://github.com/xtuhcy/gecco")
	//开启几个爬虫线程
	.thread(1)
	//单个爬虫每次抓取完一个请求后的间隔时间
	.interval(2000)
	.run();

##DEMO
参考源代码中测试用例src/test