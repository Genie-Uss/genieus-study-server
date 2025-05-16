if redis.call('EXISTS', KEYS[1]) == 1 then
  -- 토큰이 이미 존재하면 먼저 삭제
  redis.call('DEL', KEYS[1])
  redis.call('SREM', KEYS[2], ARGV[3])
end

-- 토큰 저장
redis.call('SET', KEYS[1], ARGV[1], 'PX', ARGV[2])

-- 사용자 ID와 토큰 ID 매핑 정보 저장 (Set)
redis.call('SADD', KEYS[2], ARGV[3])
redis.call('PEXPIRE', KEYS[2], ARGV[2])

return 1