-- lua script for fixed window rate limiter
-- KEYS[1] - the key for the current window
-- ARGV[1] - the maximum number of requests allowed in the window
-- ARGV[2] - the expiration time for the key in seconds
local current  = redis.call('GET', KEYS[1]) -- get the current count for the window
if current and tonumber(current) >= tonumber(ARGV[1]) then
    return 0 -- rate limit exceeded
else
    current = redis.call('INCR', KEYS[1]) -- increment the count for the window
    if tonumber(current) == 1 then
        redis.call('EXPIRE', KEYS[1], ARGV[2]) -- set the expiration time for the key if it's the first request
    end
    return current -- request allowed
end