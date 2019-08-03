#
# "makefile" for the final implementation of calprint (calprint4)
# Summer 2019, assignment #4)
#

CC=gcc

# The line with -DDEBUG can be used for development. When
# building your code for evaluation, however, the line *without*
# the -DDEBUG will be used.
#
CFLAGS=-c -Wall -g -DDEBUG -std=c99
#CFLAGS=-c -Wall -g -std=c99


all: calprint4

calprint4: calprint4.o listy.o emalloc.o
	$(CC) calprint4.o listy.o emalloc.o -o calprint4

calprint4.o: calprint4.c listy.h ics.h emalloc.h
	$(CC) $(CFLAGS) calprint4.c

listy.o: listy.c listy.h ics.h emalloc.h
	$(CC) $(CFLAGS) listy.c

emalloc.o: emalloc.c emalloc.h
	$(CC) $(CFLAGS) emalloc.c

clean:
	rm -rf *.o calprint4 
