#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail
shopt -s dotglob
IFS=$'\n\t'

readonly command="${1:-}"
readonly name="${2:-}"
readonly port="${3:-}"
readonly jar_path="${4:-}"
readonly pid_file="${name}.pid"
readonly server_log="${name}.log"

kill_server() {
    local readonly pid="$(cat ${pid_file})"
    if ps -p "$(cat ${pid_file})" >/dev/null; then
        echo "Killing server process ${pid}." >>"${server_log}"
        kill -9 $(cat "${pid_file}")
    fi
    rm "${pid_file}"
}

if [ "${command}" == "start" ]; then
    if [ ! -f "${jar_path}" ]; then
        echo "JAR ${jar_path} not found." >>"${server_log}"
        exit 1
    fi

    if [ -f "${pid_file}" ]; then
        kill_server
    fi

    echo "Starting ${name} server with command 'java -jar ${jar_path} 0.0.0.0 ${port} >>${server_log} 2>>&1 &'." >>"${server_log}"
    nohup java -jar "${jar_path}" 0.0.0.0 "${port}" >>"${server_log}" 2>&1 &
    [ $? == 0 ] && echo $! >"${pid_file}"
elif [ "${command}" == "stop" ]; then
    if [ ! -f "${pid_file}" ]; then
        echo "No running server found." >>"${server_log}"
        exit 1
    fi

    kill_server
else
    echo "Invalid command ${command}." >>"${server_log}"
    exit 1
fi
