/*
 * calprint4.c
 *
 * Starter file provided to students for Assignment #4, SENG 265,
 * Summer 2019.
 */

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <regex.h>
#include <time.h>
#include "emalloc.h"
#include "ics.h"
#include "listy.h"

time_t *convert_time(char *time_s);
void print_date(node_t *node);
void print_time(node_t *node);
time_t *get_date(char *time_s);
void ptime(time_t *time);

void print_events(node_t *events, time_t *start_date, time_t *end_date) {
    
    node_t *curr = events;
    node_t *prev = NULL;
    for(; curr != NULL; curr = curr->next){
        time_t *curr_date = get_date(curr->val->dtstart);
        time_t *prev_date;
        if(prev != NULL){
            prev_date = get_date(prev->val->dtstart);
        }
        printf("ssadf");
        if(difftime(*curr_date, *start_date) >=0 || difftime(*end_date, *curr_date) <= 0){
            if(prev == NULL || difftime(*prev_date, *curr_date) == 0){
                print_date(curr);
            }
            print_time(curr);
        }
    }
}


#ifdef DEBUG



#endif
void ptime(time_t *time){
    char *output = (char *) emalloc(sizeof(char) * 40);
    strftime(output, 40, "%B %d, %Y (%a) %H %M", localtime(time));
    printf("%s\n", output);
}
void print_date(node_t *node){
    
    char *output = (char *) emalloc(sizeof(char) * 40);
    
    time_t *time = convert_time(node->val->dtstart);
    
    strftime(output, 40, "%B %d, %Y (%a)\n", localtime(time));

    int length = strlen(output);
    for(int i = 0; i < length; i++){
        strcat(output, "-");
    }
    
    printf("%s\n", output);
    free(output);
}
void print_time(node_t *node){
    char *output = (char *) emalloc(sizeof(char) * 40);
    
    event_t *event = node->val;
    time_t *from_time = convert_time(event->dtstart);
    time_t *to_time = convert_time(event->dtend);
    
    strftime(output, 10, "%H:%M %P", localtime(from_time));
    strftime(output, 10, "%H:%M %P", localtime(to_time));
    
    strncat(output, event->summary, 20);
    strncat(output, event->location, 20);
    printf("%s\n", output);
    
    free(output);
}
time_t *get_date(char *time_s){
    struct tm *time_tm = (struct tm *)emalloc(sizeof(struct tm));
    
    sscanf(time_s, "%4d%2d%2d", &time_tm->tm_year, &time_tm->tm_mon, &time_tm->tm_mday);
    
    time_tm->tm_year -= 1900;
    time_tm->tm_mon -= 1;
    
    time_t *time = (time_t *) emalloc(sizeof(time_t));
    *time = mktime(time_tm);

    free(time_tm);
    return time;
}
time_t *convert_time(char *time_s){
    if(strlen(time_s) == 0){
        return NULL;
    }
    struct tm *time_tm = (struct tm *)emalloc(sizeof(struct tm));
    
    sscanf(time_s, "%4d%2d%2dT%2d%2d%2d", &time_tm->tm_year, &time_tm->tm_mon, &time_tm->tm_mday, &time_tm->tm_hour, &time_tm->tm_min, &time_tm->tm_sec);
    
    time_tm->tm_year -= 1900;
    time_tm->tm_mon -= 1;
    
    time_t *time = (time_t *) emalloc(sizeof(time_t));
    *time = mktime(time_tm);

    free(time_tm);
    return time;
}
char *increment_week(char *time_s){
    struct tm *time_tm = (struct tm *)emalloc(sizeof(struct tm));
    
    sscanf(time_s, "%4d%2d%2dT%2d%2d%2d", &time_tm->tm_year, &time_tm->tm_mon, &time_tm->tm_mday, &time_tm->tm_hour, &time_tm->tm_min, &time_tm->tm_sec);
    
    time_tm->tm_year -= 1900;
    time_tm->tm_mon -= 1;
    time_tm->tm_mday += 7;
    
    time_t *time = (time_t *) emalloc(sizeof(time_t));
    *time = mktime(time_tm);
    
    char *output = (char *)emalloc(sizeof(char) * 20);
    strftime(output, 20, "%Y%m%dT%H%M%S", localtime(time));
    return output;
}
node_t *add_inorder(node_t *list, node_t *new){
    if(list == NULL){
        return new;
    }
    time_t *new_time = convert_time(new->val->dtstart);
    
    node_t *curr = list;
    node_t *prev = NULL;
    for(; curr != NULL; curr = curr->next){
        time_t *curr_time = convert_time(curr->val->dtstart);
        
        if(difftime(*curr_time, *new_time) >= 0){
            if(prev == NULL){
                new->next = list;
                free(curr_time);
                free(new_time);
                return new;
            }
            prev->next = new;
            new->next = curr;
            free(curr_time);
            free(new_time);
            return list;
        }
        free(curr_time);
        prev = curr;
    }
    if(curr == NULL){
        prev->next = new;
    }
    free(new_time);
    return list;
}
node_t *get_repetitive_events(node_t *events, node_t *repeat){
    
    char *next_week_s = increment_week(repeat->val->dtstart);
    time_t *next_week = convert_time(next_week_s);
    time_t *util_time = convert_time(repeat->val->rrule);
    
    if(difftime(*util_time, *next_week) >= 0){
    
        event_t *next_event = (event_t *) emalloc(sizeof(event_t));
        
        event_t *curr_event = repeat->val;
        
        strncpy(next_event->dtstart, next_week_s, strlen(next_week_s)+1);
        strncpy(next_event->dtend, curr_event->dtend, strlen(curr_event->dtend)+1);
        strncpy(next_event->rrule, curr_event->rrule, strlen(curr_event->rrule));
        strncpy(next_event->location, curr_event->location, strlen(curr_event->location)+1);
        strncpy(next_event->summary, curr_event->summary, strlen(curr_event->summary)+1);
        
        node_t *next_node = new_node(next_event);
        events = add_inorder(events, next_node);
        
        free(next_week_s);
        free(next_week);
        free(util_time);
        return get_repetitive_events(events, next_node);
    }
    
    return events;
}
event_t *get_event(char *line, event_t *temp_event){
    if(strncmp(line, "DTSTART:", 8) == 0){
        sscanf(line, "DTSTART:%s", temp_event->dtstart);
    }
    if(strncmp(line, "DTEND:", 6) == 0){
        sscanf(line, "DTEND:%s", temp_event->dtend);
    }
    if(strncmp(line, "LOCATION:", 9) == 0){
        int length = strlen(line);
        strncpy(temp_event->location, line+=9,length-9-1);
    }
    if(strncmp(line, "RRULE:", 6) == 0){
        char *rrule = (char *) emalloc(sizeof(char) * DT_LEN);
        
        strncpy(rrule, line+=32,DT_LEN-2);
        strncpy(temp_event->rrule, rrule, DT_LEN);
        free(rrule);
    }
    if(strncmp(line, "SUMMARY:", 8) == 0){
        int length = strlen(line);
        strncpy(temp_event->summary, line+=8,length-8-1);
        
    }
    return temp_event;
}
int main(int argc, char *argv[])
{
    int from_y = 0, from_m = 0, from_d = 0;
    int to_y = 0, to_m = 0, to_d = 0;
    char *filename = NULL;
    int i; 

    for (i = 0; i < argc; i++) {
        if (strncmp(argv[i], "--start=", 7) == 0) {
            sscanf(argv[i], "--start=%d/%d/%d", &from_d, &from_m, &from_y);
        } else if (strncmp(argv[i], "--end=", 5) == 0) {
            sscanf(argv[i], "--end=%d/%d/%d", &to_d, &to_m, &to_y);
        } else if (strncmp(argv[i], "--file=", 7) == 0) {
            filename = argv[i]+7;
        }
    }
    
    if (from_y == 0 || to_y == 0 || filename == NULL) {
        fprintf(stderr, 
            "usage: %s --start=dd/mm/yyyy --end=dd/mm/yyyy --file=icsfile\n",
            argv[0]);
        exit(1);
    }
    
    struct tm *time_start = (struct tm *)emalloc(sizeof(struct tm));
    
    time_start->tm_year = from_y;
    time_start->tm_mon = from_m;
    time_start->tm_mday = from_d;
    
    time_start->tm_year -= 1900;
    time_start->tm_mon -= 1;
    
    time_t *start_date = (time_t *) emalloc(sizeof(time_t));
    *start_date = mktime(time_start);
    
    struct tm *time_end = (struct tm *)emalloc(sizeof(struct tm));
    
    time_end->tm_year = to_y;
    time_end->tm_mon = to_m;
    time_end->tm_mday = to_d;
    
    printf("tm_year: %d\n", time_end->tm_year);
    time_end->tm_year -= 1900;
    time_end->tm_mon -= 1;
    printf("tm_year: %d\n", time_end->tm_year);
    
    time_t *end_date = (time_t *) emalloc(sizeof(time_t));
    *end_date = mktime(time_end);
    
    ptime(start_date);
    ptime(end_date);
    
    
    node_t *events = NULL;
    event_t *temp_event = NULL;
    
    FILE *fp = fopen(filename, "r");
    char *line = (char *)emalloc(sizeof(char));
    size_t len = sizeof(line) * 80;
    
    while(fgets(line, len, fp) != NULL){
       
        if(strncmp(line, "DTSTART:", 8) == 0){
            temp_event = emalloc(sizeof(event_t));
        }
        
        temp_event = get_event(line, temp_event);
        
        if(strncmp(line, "SUMMARY:", 8) == 0){
            node_t *temp_node = new_node(temp_event);
            events = add_inorder(events, temp_node);
            
            if(strlen(temp_node->val->rrule) != 0){
                events = get_repetitive_events(events, temp_node);
            }
        }
    }
    free(line);
    print_events(events, start_date, end_date);
    
    free(start_date);
    free(end_date);
    freeall(events);
    fclose(fp);
/* 
 * Showing some simple usage of the linked-list routines.
 */

#ifdef DEBUG
    //_demo();
#endif

    exit(0);
}
