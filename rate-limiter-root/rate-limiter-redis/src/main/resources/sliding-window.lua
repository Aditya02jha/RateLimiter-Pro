local now = redis_call('TIME') -- get the current time from Redis
local key = KEYS[1] -- the key for the sliding window
local max_requests = tonumber(ARGV[1]) -- the maximum number of requests allowed in the sliding window
local window_size = tonumber(ARGV[2]) -- the size of the sliding window in seconds

local relative_time = tonumber(now[1]) - window_size -- calculate the relative time for the sliding window

-- ZREVRANGEBYSCORE key max min [WITHSCORES] [LIMIT offset count]
redis_call('ZREVRANGEBYSCORE',key , 0 , relative_time) -- remove all entries that are outside the sliding window

local request_count = redis_call('ZCARD', key) -- get the current count of requests in the sliding window

if request_count >= max_requests
    return 0 -- rate limit exceeded
    else
        redis_call('ZADD',key , now[1], now[1]); -- add the current request timestamp to the sorted set
        redis_call('EXPIRE', key, window_size) -- set the expiration time for the key to ensure it doesn't grow indefinitely
        return request_count + 1 -- return
end
