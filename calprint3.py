#!/usr/bin/env python3
import datetime
import copy
import re

class Calprint:
    """A class contains all events in an ics file"""
    def __init__(self, file):
        self.events = []
        self.num_entries = 0
        
        inFile = open(file, "r")
        for line in inFile:
            self.get_events(line)
        
        self.events.sort(key = lambda event: event.start_time)
    
    # This function takes a datetime object and returns formatted string with the days events
    def get_events_for_day(self, date):
        output = ""
        
        # get all events in the date in the self.events into list
        list = []
        date = date.date()
        for event in self.events:
            start_date = event.start_time.date()
            if(start_date == date):
                list.append(event)
        
        # store fomatted string into output
        if(len(list) != 0):
            start_date = list[0].start_time.strftime("%B %d, %Y (%a)")
            output += start_date + '\n'
            output += ('-' * len(start_date)) + '\n'
            for i in range(0, len(list)):
                output += list[i].__repr__()
                if(i != len(list)-1):
                    output+= '\n'
        
        return output
    
    # This method extracts information required from the input line and store them into the events
    def get_events(self, line):
        matchobj = re.match('DTSTART:(.*)', line)
        if(matchobj):
            # extracts start_time in format "20190623T112000" and pass to function get_time to get a datetime object for start_time
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
            
    # This function generate all repetitive events recursively util util_time stored current event is reached
    def generate_events(self):
        next_week = self.events[self.num_entries].start_time + datetime.timedelta(weeks = 1)
        
        if(next_week < self.events[self.num_entries].util_time):
            
            new_event = copy.deepcopy(self.events[self.num_entries])
            new_event.start_time = next_week
            self.events.append(new_event)
            self.num_entries = self.num_entries + 1
            
            self.generate_events()
    
    # This function extract information from formatted string to create a datetime object
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
        
    def __init__(self):
        self.start_time = None
        self.end_time = None
        self.util_time = None
        self.location = None
        self.summary = None
    
    def __repr__(self):
        output = self.start_time.strftime("%-2I:%M %P") + " to " + self.end_time.strftime("%-2I:%M %P: ");
        output = output + self.summary + " [" + self.location + "]"
        return output