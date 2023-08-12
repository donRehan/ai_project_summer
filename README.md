# ai project summer

We have some bugs regarding having horizontal objects.

This currently what the algorithim solves. In this repo I will make sure that all the remaining bugs are solved.

This is how it looks like now where o is the object that occupies two cells and the goal is to reach the right most end of the board 
This will be done by first making the x object move vertically up then moving the object to the right and solve the puzzle.
> . . . .
> o o x . 
> . . x . 
> . . . .

# BUG 
Consider the following example
b stands for a horizontal object, as soon as we have a horizontal object. the code goes into an infinite loop.
Hence we want to fix that.
> . . . .
> o o x . 
> . . x . 
> . b b .
