#!/usr/bin/bash

# This script makes several assumptions:
# 
# (1) All of the .ics files are in the same directory as the
#     'calprint4' executable.
# 
# (2) The script itself is executed in the same directory as
      the 'calprint4' executable.
# 
# (3) The script is being run on a machine from which the
#     directory /home/zastre/seng265/a1/tests is visible
#
# If no output other that 'Checking for <sometest>' appears on
# the console, then that test has passed (i.e., there are no
# differences between what the executable produces as output and
# what is expected for that test)


# Uncomment the following 'set' statement if you want to see 
# precisely what 'bash' is doing to execute this script.
# set -x  

EXECUTABLE=./calprint4
EXPECTEDS_DIR=/home/zastre/seng265/a1/tests
DIFFBIN=/usr/bin/diff

declare -a ARGLIST=(
    "--start=18/6/2019 --end=18/6/2019 --file=one.ics":"test01.txt"
    "--start=1/6/2019 --end=1/6/2019 --file=one.ics":"test02.txt"
    "--start=31/5/2019 --end=30/6/2019 --file=one.ics":"test03.txt"
    "--start=1/10/2019 --end=31/10/2019 --file=one.ics":"test04.txt"
    "--start=20/6/2019 --end=20/06/2019 --file=two.ics":"test05.txt"
    "--start=12/6/2019 --end=24/06/2019 --file=two.ics":"test06.txt"
    "--start=1/5/2019 --end=30/5/2019 --file=two.ics":"test07.txt"
    "--start=4/6/2019 --end=04/6/2019 --file=many.ics":"test08.txt"
    "--start=1/6/2019 --end=31/07/2019 --file=many.ics":"test09.txt"
    "--start=1/5/2019 --end=31/5/2019 --file=many.ics":"test10.txt"
    "--start=18/6/2019 --end=18/06/2019 --file=three.ics":"test11.txt"
    "--start=1/5/2019 --end=31/7/2019 --file=three.ics":"test12.txt"
    "--start=23/5/2019 --end=23/5/2019 --file=prudence.ics":"test13.txt"
    "--start=24/5/2019 --end=24/5/2019 --file=prudence.ics":"test14.txt"
    "--start=1/5/2019 --end=31/8/2019 --file=prudence.ics":"test15.txt"
    )

for A in "${ARGLIST[@]}"
do
    IFS=\:; read -a FIELDS <<< "$A"
    TEST_FILE=${FIELDS[1]}
    IFS=\ ; read -a TEST_ARGS <<< ${FIELDS[0]}

    echo "Checking for: $TEST_FILE"

    $EXECUTABLE ${TEST_ARGS[0]} ${TEST_ARGS[1]} ${TEST_ARGS[2]}|  \
        $DIFFBIN - $EXPECTEDS_DIR/$TEST_FILE
done
