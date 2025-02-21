-- Used to get a list containing 1 row per test
-- Used to see last execution result of every test
-- Limited by defined environment and 30 days back
SELECT * FROM (
    SELECT *, ROW_NUMBER() OVER (PARTITION BY testName ORDER BY test_id DESC) AS rowNumber from (
        SELECT * FROM testStatus ts WHERE ts.environment = :environment and TIMESTAMPDIFF(DAY, ts.startTime, NOW()) < 30) t) k
WHERE k.rowNumber = 1