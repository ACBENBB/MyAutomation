-- SQL explained in steps:
-- 1. GET all relevant tests: type=API, category=CICD, environment=X (parameter) and startTime in the last X days (parameter)
-- 2. to those give sequential number to each execution of each test (by testName) from latest getting 1 to older getting 2 - in a new row_num column
-- 3. Filter those to only keep latest Y (parameter) executions of every test and only the Failed runs
-- 4. GROUP by test name to count and only leave test that have failed more than 1 times (parameter)
-- 5. Wrap this all in a join to pull latest row of each failed test to have latest failure reason
SELECT t.testClass, t.testName, t.failureReason from (
SELECT *, MAX(test_id) as maxId FROM (
SELECT t.*, ROW_NUMBER() OVER (PARTITION BY t.testName order by test_id desc) as row_num
FROM (SELECT * FROM testStatus ts WHERE status != 'SKIPPED' and ts.testType = :testType and ts.category in (:testCategories) and ts.environment = :environment and TIMESTAMPDIFF(DAY, startTime, now()) < :maxDaysToScan) t
ORDER BY testName
) t WHERE t.row_num <= :runsToAnalyze and status = :status
GROUP BY t.testName, status
HAVING count(status) >= :minimalAmountOfRunsWithStatus
ORDER BY t.testName, t.test_id desc
) f, testStatus t WHERE t.test_id = f.maxId
order by f.testName