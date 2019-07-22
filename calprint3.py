#!/usr/bin/env python3
import datetime
import re

class Calprint:
    """A class contains all events in an ics file"""
    def __init__(self, file):
        self.events = []
        self.num_entries = 0
        
        inFile = open(file, "r")
        for line in inFile:
            print(line),
            self.get_events(line)
        
        print("length: ", len(self.events))
        print("num_entries: ", self.num_entries)
    
    def get_events(self, line):
        matchobj = re.match('DTSTART:(.*)', line)
        if(matchobj):
            start_time = self.get_time(matchobj.group(1))
            self.events.append(Event())
            self.events[self.num_entries].start_time = start_time
        
        matchobj = re.match('DTEND:(.*)', line)
        if(matchobj):
            end_time = self.get_time(matchobj.group(1))
            self.events[self.num_entries].end_time = end_time
            
        matchobj = re.match('(.*)UNTIL=(.*);BYDAY(.*)', line)
        if(matchobj):
            util_time = self.get_time(matchobj.group(2))
            self.events[self.num_entries].util_time = util_time
            
        matchobj = re.match('LOCATION:(.*)', line)
        if(matchobj):
            location = matchobj.group(1)
            self.events[self.num_entries].location = location
        
        matchobj = re.match('SUMMARY:(.*)', line)
        if(matchobj):
            summary = matchobj.group(1)
            self.events[self.num_entries].summary = summary
            if(self.events[self.num_entries].util_time != None):
                self.generate_events()
            self.num_entries = self.num_entries + 1
            
    def generate_events(self):
        next_week = self.events[self.num_entries].start_time + datetime.timedelta(weeks = 1)
        
        if(next_week < self.events[self.num_entries].util_time):
            
            self.events.append(self.events[self.num_entries])
            self.num_entries = self.num_entries + 1
            self.events[self.num_entries].start_time = next_week
            
            print("event 0: ", self.events[0].start_time, self.events[0])
            self.generate_events()
    
    def get_time(self, text):
        matchobj = re.match("(\d{4})(\d{2})(\d{2})T(\d{2})(\d{2})(\d{2})", text)
        if(matchobj):
            (year, month, day, hour, minute, second) = matchobj.groups()
            time = datetime.datetime(int(year), int(month), int(day), int(hour), int(minute), int(second))
            return time
           
        
class Event:
    """A class for a single event"""
    def __init__(self, start_time, end_time, util_time, location, summary):
        self.start_time = start_time
        self.end_time = end_time
        self.util_time = util_time
        self.location = location
        self.summary = summary
    
    def __init__(self, event2):
        self.start_time = event2.start_time
        self.end_time = event2.end_time
        self.util_time = event2.util_time
        self.location = event2.locatoin
        self.summary = event2.summary
        
    def __init__(self):
        self.start_time = None
        self.end_time = None
        self.util_time = None
        self.location = None
        self.summary = None
        
    def compareTo(self, event2):
        if(self.start_time < event2.start_time):
            return -1
        elif(self.start_time == event2.start_time):
            return 0
        else:
            return 1
    
    def __repr__(self):
        output = self.start_time.strftime("%-2I:%M %P") + " to " + self.end_time.strftime("%-2I:%M %P: ");
        output = output + self.summary + " [" + self.location + "]"
        return output

def main():
    calprint = Calprint("many.ics")
    for event in calprint.events:
        print("event: ", event.start_time, event)
        
    
if __name__== "__main__":
    main()