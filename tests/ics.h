#ifndef _ICS_H_
#define _ICS_H_

#define DT_LEN       17
#define SUMMARY_LEN  80
#define LOCATION_LEN 80
#define RRULE_LEN    17

typedef struct event_t
{
    char *dtstart;
    char *dtend;
    char *summary;
    char *location;
    char *rrule;
} event_t;

#endif
