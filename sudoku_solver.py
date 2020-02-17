from __future__ import print_function
import copy

# board = [
#     "6..874.1.",
#     "..9.36...",
#     "...19.8..",
#     "7946.....",
#     "..1.894..",
#     "...41..69",
#     ".7..5..9.",
#     ".539.76..",
#     "9.2.61.47"
# ]

board = []


def main():
    global board

    print("Welcome to the Sudoku Solver. We're going to ask you to input a 9 by 9 matrix so we can solve it for you. Press Enter when you are ready.")
 

    try:
        row1 = input("Enter a string containing nine digits with unkown values as '.': ")
        row2 = input("Enter a string containing nine digits with unkown values as '.': ")
        row3 = input("Enter a string containing nine digits with unkown values as '.': ")
        row4 = input("Enter a string containing nine digits with unkown values as '.': ")
        row5 = input("Enter a string containing nine digits with unkown values as '.': ")
        row6 = input("Enter a string containing nine digits with unkown values as '.': ")
        row7 = input("Enter a string containing nine digits with unkown values as '.': ")
        row8 = input("Enter a string containing nine digits with unkown values as '.': ")
        row9 = input("Enter a string containing nine digits with unkown values as '.': ")

    except:
        print("Please enter valid strings!")

    try:  
        board.append(row1)
        board.append(row2)
        board.append(row3)
        board.append(row4)
        board.append(row5)
        board.append(row6)
        board.append(row7)
        board.append(row8)
        board.append(row9)

    except:
        print("You didn't enter valid strings!")


    for idx, line in enumerate(board):
        board[idx] = list(line)

    solve()
    printBoard()

def solve():
    global board

    try:
        fillAllObvious()

    except:
        return False

    if isComplete():
        return True

    i, j = 0, 0
    for rowIdx,row in enumerate(board):
        for colIdx,col in enumerate(row):
            if col == ".":
                i,j = rowIdx, colIdx

    possibilities = getPossibilities(i,j)
    for value in possibilities:
        snapshot = copy.deepcopy(board)
        board[i][j] = value
        result = solve()
        if result == True:
            return True
        else:
            board = copy.deepcopy(snapshot)


    return False


def fillAllObvious():
    global board

    while True:
        somethingChanged = False
        for i in range(0,9):
            for j in range(0,9):
                possibilities = getPossibilities(i,j)
                if possibilities == False:
                    continue
                if len(possibilities) == 0:
                    raise RuntimeError("No moves left")
                if len(possibilities) == 1:
                    board[i][j] = possibilities[0]
                    somethingChanged = True

        if somethingChanged == False:
            return 

def getPossibilities(i,j):
    global board
    if board[i][j] != '.':
        return False

    possibilities = {str(n) for n in range(1,10)}

    for val in board[i]:
        possibilities -= set(val)

    for idx in range(0,9):
        possibilities -= set(board[idx][j])

    iStart = (i // 3) * 3
    jStart = (j // 3) * 3 

    subboard = board[iStart:iStart+3]
    for idx,row in enumerate(subboard):
        subboard[idx] = row[jStart:jStart+3]

    for row in subboard:
        for col in row:
            possibilities -= set(col)

    return list(possibilities)


def printBoard():
    global board
    for row in board:
        for col in row:
            print(col, end="")
        print("")

def isComplete():
    for row in board:
        for col in row:
            if(col == "."):
                return False

    return True


main()