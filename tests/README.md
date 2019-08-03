# Description of files in this directory.

The files here are starter files for UVic SENG 265, Summer 2019,
Assignment #4. This is the fourth version of _calprint_. The files are
as follows:

* ```makefile```: Needed to build the assignment. In order to
construct the ```calprint4``` executable, type either ```make``` or
```make calprint4```.

* ```calprint4.c```: The C file containing the ```main()``` function.
(There must only exist one ```main``` in any program.)  This should be
suitably modified to complete the assignment.

* ```emalloc.[ch]```: Source code and header file for the
```emalloc``` function described in lecture. This is kept in its own
source-code file as it can be used independently in both
```calprint.c``` and ```listy.c```.

* ```ics.h```: The definition of the ```struct event_a``` type.

* ```listy.[ch]```: Linked-list routines based on the lectures. The
routines here may be added to or modified. Regardless, however,
students are responsible for any segmentation faults that occur as a
result of using this code.

* ```motherofalltests.sh```: Modified to run the tests using
```calprint4```.
