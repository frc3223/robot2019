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
