INSERT INTO deletedTestStatus SELECT * FROM testStatus WHERE executionId in (:executionIdsToDelete);
DELETE FROM testStatus WHERE executionId in (:executionIdsToDelete);