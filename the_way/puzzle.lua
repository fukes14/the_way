io.write("The Desk has a computer on it, when you walk towards it the computer turns on\n")
math.randomseed(os.time())
math.random()
a = math.random(1,10)
io.write("To pass, guess between 1 and 10\n")
b = io.read("*n")
if (b == a)
then print("You may pass") return 3
else print("you may not pass") return 8
end