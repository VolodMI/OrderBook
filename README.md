# OrderBook
 Generally, order book looks like this (B - bid, S - spread, A - ask), size defines how many shares can be bought/sold at this price:


PRICE  SIZE  TYPE  COMMENT
100    0     A     size is zero, but it is still ask price, because it is higher than best ask
99     50    A 	   best ask (lowest non-zero ask price)
97     0     S
96     0     S
95     40    B     best bid (highest non-zero bid price)


Input file
There is a text file with:
incremental updates in the following format:

u,<price>,<size>,bid - set bid size at <price> to <size>
u,<price>,<size>,ask - set ask size at <price> to <size>

queries in the following format:

q,best_bid - print best bid price and size
q,best_ask - print best ask price and size
q,size,<price> - print size at specified price (bid, ask or spread).

and orders in the following format:

o,buy,<size> - removes <size> shares out of asks, most cheap ones.
o,sell,<size> - removes <size> shares out of bids, most expansive ones.

Example:

Input file

u,9,1,bid
u,11,5,ask
q,best_bid
u,10,2,bid
q,best_bid
o,sell,1
q,size,10

Output file

9,1
10,2
1
