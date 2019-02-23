import pickle 


def dump(elevator, fnom):
    stuff = {
        "A": elevator.sysd.A,
        "Ainv": elevator.Ainv,
        "B": elevator.sysd.B,
        "C": elevator.sysd.C,
        "D": elevator.sysd.D,
        "K": elevator.K,
        "Kff": elevator.Kff,
        "L": elevator.L,
        "coeffs": [elevator.Ks, elevator.Kv, elevator.Ka],
    }
    with open(fnom, "wb") as f:
        pickle.dump(stuff, f)	
def dump_java3(toPrint, name, index):
	c = "// "
	c += name
	c += " is "
	c += str(toPrint["coeffs"][index])
	print(c)
def dump_java2(loopThrough, toPrint, name):
	c = "double[][] "
	c += name
	c += " = new double[][] {"
	print(c)
	for i in range(toPrint[loopThrough].shape[0]):
		s = "    { "
		for j in range(toPrint[loopThrough].shape[1]):
			s += str(toPrint[loopThrough][i,j])
			if j != toPrint[loopThrough].shape[1] - 1:
				s += ", "
		s += " }"
		if i != toPrint[loopThrough].shape[0] - 1:
			s += ", "
		print(s)
	print("};")
def dump_java(toPrint):
	#matrix a
	dump_java2("A", toPrint, "a")
	#matix a_inv
	dump_java2("Ainv", toPrint, "a_inv")
	#matrix b
	dump_java2("B", toPrint, "b")
	#matrix c
	dump_java2("C", toPrint, "c")
	#matrix k
	dump_java2("K", toPrint, "k")
	#matrix kff
	dump_java2("Kff", toPrint, "kff")
	#matrix L
	dump_java2("L", toPrint, "L")
	#coeffs
	dump_java3(toPrint, "Ks", 0)
	dump_java3(toPrint, "Kv", 1)
	dump_java3(toPrint, "Ka", 2)
if __name__ == "__main__":
	x = pickle.load(open("cascading_elevator.pickle", "rb"))
	print(x)
	dump_java(x)