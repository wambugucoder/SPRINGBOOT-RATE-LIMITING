# SPRINGBOOT-RATE-LIMITING
A Springboot Application that limits the number of requests sent using the _**Token-Bucket-Algorithm**_


## TOKEN-BUCKET-ALGORITHM

###### ![Implementation](https://res.cloudinary.com/practicaldev/image/fetch/s---7rfL-zJ--/c_limit%2Cf_auto%2Cfl_progressive%2Cq_auto%2Cw_880/https://dev-to-uploads.s3.amazonaws.com/i/mkifh9c3ze4i9ir5j4mo.png)


Say that we have a bucket whose capacity is defined as the number of tokens that it can hold. Whenever a consumer wants to access an API endpoint, it must get a token from the bucket. We remove a token from the bucket if it's available and accept the request. On the other hand, we reject a request if the bucket doesn't have any tokens.

As requests are consuming tokens, we are also replenishing them at some fixed rate, such that we never exceed the capacity of the bucket.

Let's consider an API that has a rate limit of 100 requests per minute. We can create a bucket with a capacity of 100, and a refill rate of 100 tokens per minute.

If we receive 70 requests, which is fewer than the available tokens in a given minute, we would add only 30 more tokens at the start of the next minute to bring the bucket up to capacity. On the other hand, if we exhaust all the tokens in 40 seconds, we would wait for 20 seconds to refill the bucket.

### IMPLEMENTATION
1. Add the Following Dependencies:

``` maven
   <dependency>
   <groupId>com.giffing.bucket4j.spring.boot.starter</groupId>
   <artifactId>bucket4j-spring-boot-starter</artifactId>
   </dependency>

   <dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-cache</artifactId>
   </dependency>

   <dependency>
   <groupId>org.ehcache</groupId>
   <artifactId>ehcache</artifactId>
   </dependency>
```

2. Create an ehcache.xml file and add the following configurations:
 
``` xml
   <config xmlns='http://www.ehcache.org/v3'
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xmlns:jsr107="http://www.ehcache.org/v3/jsr107"
   xsi:schemaLocation="http://www.ehcache.org/v3 http://www.ehcache.org/schema/ehcache-core-3.0.xsd
   http://www.ehcache.org/v3/jsr107 http://www.ehcache.org/schema/ehcache-107-ext-3.0.xsd">
   <cache alias="buckets">
   <expiry>
   <ttl unit="seconds">3600</ttl>
   </expiry>
   <heap unit="entries">1000000</heap>
   <jsr107:mbeans enable-statistics="true"/>
   </cache>

</config>
   ```

3.Configure your Application.properties file to add rate-limits:
``` properties
bucket4j.enabled=true
bucket4j.filters[0].filter-method=servlet
bucket4j.filters[0].cache-name=buckets
bucket4j.filters[0].url=.*
bucket4j.filters[0].rate-limits[0].bandwidths[0].capacity=3
bucket4j.filters[0].rate-limits[0].bandwidths[0].time=30
bucket4j.filters[0].rate-limits[0].bandwidths[0].unit=seconds
bucket4j.filters[0].rate-limits[0].bandwidths[0].fixed-refill-interval=1
bucket4j.filters[0].rate-limits[0].bandwidths[0].fixed-refill-interval-unit=minutes
bucket4j.filters[0].http-response-body={ "message": "Too many requests" }
```

_In my case ,you can see that any endpoint has a maximum of 3 requests and when exceeded within 30 seconds,it shall refuse those requests and give them a response of too many requests_


