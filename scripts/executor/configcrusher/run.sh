#!/usr/bin/env bash

# CLASS_DIR=${1}

PROGRAM_NAME=running-example
SRC_DIR=/Users/yvettegwen/Documents/Programs/ConfigDependency/performance-mapper-evaluation/original/running-example/src
CLASS_DIR=/Users/yvettegwen/Documents/Programs/ConfigDependency/performance-mapper-evaluation/original/running-example/target/classes
ENTRY_POINT=edu.cmu.cs.mvelezce.Example
CONFIG_FILE=/Users/yvettegwen/Documents/Projects/ConfigDependency/ConfigCrusher/config/default.json
ITERATIONS=5

M2=$(echo $HOME)/.m2/repository
CC=./target/classes

function run {
    local cc=$1
    local m2=$2
    local program_name=$3
    local src_dir=$4
    local class_dir=$5
    local entry_point=$6
    local config_file=$7

    # echo $cc, $m2, $program_name, $class_dir, $entry_point, $iterations

    java -cp \
      $cc:$m2/commons-io/commons-io/2.5/commons-io-2.5.jar:$m2/org/apache/commons/commons-math3/3.6.1/commons-math3-3.6.1.jar:$m2/commons-cli/commons-cli/1.4/commons-cli-1.4.jar:$m2/com/fasterxml/jackson/core/jackson-core/2.8.9/jackson-core-2.8.9.jar:$m2/com/fasterxml/jackson/core/jackson-databind/2.8.9/jackson-databind-2.8.9.jar:$m2/com/fasterxml/jackson/core/jackson-annotations/2.8.9/jackson-annotations-2.8.9.jar:$m2/log4j/log4j/1.2.17/log4j-1.2.17.jar \
      edu.cmu.cs.mvelezce.evaluation.Run \
      -program $program_name \
      -srcDir $src_dir \
      -classDir $class_dir \
      -entry $entry_point \
      -config $config_file
}

(
# echo $M2
# cd ../../../
run $CC $M2 $PROGRAM_NAME $SRC_DIR $CLASS_DIR $ENTRY_POINT $CONFIG_FILE
)