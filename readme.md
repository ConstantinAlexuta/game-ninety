# 90 Baal Ticket Generator algorithm

##  How it works?

It uses two map collections to store:
- first: 18 lists with numbers 1-9, 20...29,..., 80...90 - for each line of the strip
- second:  9 lists, one for each column, 1...18, to store the cells rows that are not populated yet

The algorithm will start to populate the six tickets from the strip and during this will remove from the previously lists the numbers used and the populated cells and also the cells where is not allowed anymore to populate because of 5 number on a row restriction.

The algorithm begin with  population in each ticket, on each column of one cell and avoiding populating more than 5 numbers on a row.

After that will continue to populate from the beginning the columns remained with numbers not used from the list, for each column.

The algorithm can be improved. I have also some other ideas with less code. On working on this I realize more and more possibilities, to be faster and simple. I know it's not perfect.

In the past I've designed my own small Prince of Persia game in C++ almost 20 years ago. All these are funny.
