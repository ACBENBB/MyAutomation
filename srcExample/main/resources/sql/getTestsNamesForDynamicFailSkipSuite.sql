-- Finds list of test which didn't pass last time running (skipped or failed)
SELECT k.testClass, k.testName, k.failureReason FROM
	(SELECT * FROM
		(SELECT *, ROW_NUMBER() OVER (PARTITION BY testName ORDER BY test_id DESC) AS rowNumber from (
			SELECT * FROM testStatus ts WHERE environment = :environment and TIMESTAMPDIFF(HOUR, startTime, now()) < :hoursBackToScan and category in (:testCategories)) k) t
	WHERE t.rowNumber = 1) k
WHERE k.status != 'PASS'


