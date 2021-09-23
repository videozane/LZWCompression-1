HELLO whoever gets this jank ass program. I think everything works but I didn't test it very well. 

Every single coded character is 12 bits long.

Good luck with this :P


Part 3 update: 


Aaron wrote the decoder for the encoder and now I'm optimizing Aaron's decoder. It was really hard to optimize 
anything major because Aaron never used a binary file, so there is much less going on. 
There was a couple lines that I was able to delete because they were redundant; however,
they were O(1), so that didn't really make a big difference as you can see.

Avg time before optimization: 10.55ms
Avg time after optimization: 10.4ms

I read and understood how his code worked. I did fix some variable names which I thought were very unclear.
I don't know the efficiency of Stirng.split, but I assume it's optimized. 

-Chris