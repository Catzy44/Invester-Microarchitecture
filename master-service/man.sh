#!/bin/bash
cd $(dirname -- "$(readlink -f "${BASH_SOURCE}")")
# AUTHOR CATZY44


pid_file="proc.pid"
stat_file="man.sh.status"
screenName="InvesterMasterService"
me=$(whoami)
mem="6G"
jarfile="server.jar"
stopcmd="stop"
restart_sleep="1"
VERSION='1.21.5'

function tmux_sessions() {
	return $(tmux ls | cut -d: -f1)
}

function session_exists() {
        $(tmux ls 2>/dev/null | cut -d: -f1 | grep -q $screenName)
}

function unknw_arg() {
        echo "usage: ./man.sh <argument>"
        echo "available arguments: con,start,stop"
}

function wait_till_die() {
        end=$((SECONDS+30))
        while [[ $SECONDS -lt $end ]] && session_exists
        do
		/bin/sleep 1
        done
}

function status_running() {
    if [ -s $stat_file ] && grep -q "running" $stat_file; then
        return 0  # return code 0 = true
    else
        return 1  # return code other than 0 = exception
    fi
}

##-Dpaper.playerconnection.keepalive=60

function start_binary() {
	gradlew bootRun --console plain --args='--spring.config.location=classpath:/application.properties'
}


if [ "$#" -eq 0 ]
then
  unknw_arg
  exit 1
fi

if [ $1 == "start" ]
then
	if session_exists
	then
		echo "server already running!"
		exit 0
	fi

	echo running > $stat_file

	pid=$(head -n 1 proc.pid)
	kill $pid 2>/dev/null
	tmux new-session -d -s $screenName "./man.sh proc"
	echo "server started!"
elif [ $1 == "stop" ]
then
	echo stopping > $stat_file

	if session_exists
	then
		echo "stopping... [${stopcmd}]"
        	tmux send-keys -l -t $screenName $stopcmd
		tmux send-keys -t $screenName ENTER
	else
		echo "server is offline!"
		exit 0
        fi

	wait_till_die

	if session_exists
	then
		tmux kill-session -t $screenName
	fi
	echo "server stopped!"
elif [ $1 == "s_proc" ]
then
	start_binary
elif [ $1 == "proc" ]
then
	while status_running
	do
    	start_binary
	    if status_running
		then
			sleep $restart_sleep
		fi
	done
	echo stopped > $stat_file
elif [ $1 == "con" ]
then
	if ! session_exists
	then
		echo "server is offline!"
		exit 0
	fi
	tmux attach -t $screenName
else
	unknw_arg
fi
