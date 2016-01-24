# gecco-redis
Gecko crawler supports distributed by redis

##Download

	<dependency>
	    <groupId>com.geccocrawler</groupId>
	    <artifactId>gecco-redis</artifactId>
	    <version>1.0.0</version>
	</dependency>

##QuickStart

	//启动GeccoEngine时，设置RedisStartScheduler
	GeccoEngine.create()
	.scheduler(new RedisStartScheduler("127.0.0.1:6379"))
	.classpath("com.geccocrawler.gecco.demo")
	//爬虫userAgent设置
	.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36")
	//开始抓取的页面地址
	.start("https://github.com/xtuhcy/gecco")
	//开启几个爬虫线程
	.thread(1)
	//单个爬虫每次抓取完一个请求后的间隔时间
	.interval(2000)
	.run();

##DEMO
参考源代码中测试用例src/test