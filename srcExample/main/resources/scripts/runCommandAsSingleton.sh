#!/bin/bash

# This script is used to run an argument provided script filename as a singleton - meaning
# Run the script only if it's not currently running. If it's running - wait for it to finish
# and then re-run it

# Max time to wait for script to finish running if someone else already triggered it
timeoutLimitSeconds=600


# Make sure needed script name to execute was provided
if [ -z "$1" ]
  then
    >&2 echo "Error! Expecting name of script to execute, but none was provided!"
    exit 1
fi
commandToRun=$1


function executeScript
{
  echo "running $commandToRun"
  bash -c "$commandToRun"
  exitCode=$?
  echo "finished running $commandToRun. Finished with code $exitCode"
  exit $exitCode
}

function waitForScriptBySomeoneElse
{
  echo "waiting for job $commandToRun with PID of $PID that was triggered by some other process to finish"
  while ps -p "$PID" > /dev/null; do
    echo "$(date) still waiting for job $commandToRun with PID of $PID that was triggered by some other process to finish"
    sleep 5
  done
  echo "finished waiting for $commandToRun triggered by other process to finish"
  exit 0
}

# define timestamp when we should stop waiting - current time + max seconds to wait
end=$((SECONDS+$timeoutLimitSeconds))

echo "checking if $1 already running"
while [ $SECONDS -lt $end ]; do
  # pgrep -f        get a list of pid of processes with commandline containing some string
  # xargs           convert input from multiple lines (one for each PID) to one liner with space separators
  # cut -d ' ' -f1  return only the first column in the provided string - delimiter is space character
  PID=$(pgrep -f "bash -c $1" | xargs | cut -d ' ' -f1)
  if [ -z "$PID" ]; then
    echo "$1 process is not currently running... executing it now"
    executeScript
  else
    # if PREV_PID not yet set - do it now
    if [ -z "$PREV_PID" ]; then
      PREV_PID=$PID
    fi

    # if the process currently running has the same PID as before - let's keep waiting for it to finish
    if [ "$PID" == "$PREV_PID" ]; then
      echo "$(date) $1 already running as PID=$PID, waiting 5 seconds and checking again (timeout limit=$timeoutLimitSeconds seconds)"
      sleep 5
    # if the process is running but with a different PID - some other job started the job again and we can use it,
    # which means we can just wait for the new PID to finish and no need to start the job again on our own after that
    else
      echo "Process was running as pid $PREV_PID but now runs as pid $PID - some other process started it again for us, just need to wait for it to finish"
      waitForScriptBySomeoneElse
    fi
  fi
done

>&2 echo "Error! timeout waiting for other copy of $1 process to run in the background"
exit 2
