#!/usr/bin/env python3

import sys
import argparse
import datetime

# The code below configures the argparse module for use with
# assignment #2.
# 
def print_date(event):
    date = event[0].strftime("%B %d, %Y (%a)")
    print(date)
    print('-' * len(date))

def print_time_event(event):
    start_time = event[0]
    end_time = event[1]

    print(start_time.strftime("%-2I:%M %P") + 
        " to " + end_time.strftime("%-2I:%M %P: ")),
    print(event[4] + " [" + event[3] + "]")

def print_events(events, start_date, end_date):
    n = len(events)
    for i in range(n):
        if(events[i][0] > start_date and events[i][0] < end_date):
            if(i == 0 or (events[i][0].date() != events[i-1][0].date())):
                print_date(events[i])
            print_time_event(events[i])
            if(i != n-1 and events[i][0].date() != events[i+1][0].date()):
                print("\n")

def sort(events):
    n = len(events)
    for i in range(n):
        for j in range(0, n-i-1):
            if(events[j][0] > events[j+1][0]):
                events[j], events[j+1] = events[j+1], events[j]

def generate_events(events, num_entries):

    next_week = events[num_entries][0] + datetime.timedelta(weeks = 1)
    if(next_week < events[num_entries][2]):
        events.append([None] * 5)
        num_entries = num_entries + 1
        events[num_entries] = events[num_entries - 1][:]
        events[num_entries][0] = next_week
    
        generate_events(events, num_entries)
    
def get_events(line, events, num_entries):
   
    if(line[:8] == "DTSTART:"):
        start_time = datetime.datetime(int(line[8:12]), int(line[12:14]), int(line[14:16]), 
        int(line[17:19]),int(line[19:21]), int(line[21:]))
        events.append([None] * 5)
        events[num_entries][0] = start_time

    if(line[:6] == "DTEND:"):
        end_time = datetime.datetime(int(line[6:10]), int(line[10:12]), int(line[12:14]),
        int(line[15:17]), int(line[17:19]), int(line[19:]))
        events[num_entries][1] = end_time

    if(line[:6] == "RRULE:"):
        util_time = datetime.datetime(int(line[32:36]), int(line[36:38]), int(line[38:40]),
        int(line[41:43]), int(line[43:45]), int(line[45:47]))
        events[num_entries][2] = util_time

    if(line[:9] == "LOCATION:"):
        location = line[9:-1]
        events[num_entries][3] = location

    if(line[:8] == "SUMMARY:"):
        summary = line[8:-1]
        events[num_entries][4] = summary
        if(events[num_entries][2] != None):
            generate_events(events, num_entries)
            num_entries = len(events) - 1
        return num_entries + 1

    return num_entries
        
def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--file', type=str, help='file to be processed')
    parser.add_argument('--start', type=str, help='start of date range')
    parser.add_argument('--end', type=str, help='end of data range')

    args = parser.parse_args()

    print ('file: {}; start: {}; end: {}'.format( args.file, args.start,
        args.end))

    if not args.file:
        print("Need --file=<ics filename>")

    if not args.start:
        print("Need --start=dd/mm/yyyy")

    if not args.end:
        print("Need --end=dd/mm/yyyy")

    start_date = datetime.datetime.strptime(args.start, '%d/%m/%Y')
    end_date = datetime.datetime.strptime(args.end, '%d/%m/%Y')

    events = []
    num_entries = 0

    inFile = open(args.file, "r")
    for line in inFile:
        num_entries = get_events(line, events, num_entries)
    
    sort(events)

    print_events(events, start_date, end_date)

if __name__ == "__main__":
    main()
