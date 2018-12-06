def reactLetters(a, b):
    return a.upper() == b.upper() and ((a.islower() and b.isupper()) or (b.islower() and a.isupper()))

def reactPolymer(polymer):
    reacted_polymer = ''
    i = 0
    polymer_len = len(polymer)
    while i < len(polymer):
        if i < polymer_len - 1 and reactLetters(polymer[i], polymer[i + 1]):
            i += 2
        else:
            reacted_polymer += polymer[i]
            i += 1
    return reacted_polymer

def getMinPolymer(polymer):
    reacted_polymer = reactPolymer(polymer)
    while len(reacted_polymer) != len(polymer):
        polymer = reacted_polymer
        reacted_polymer = reactPolymer(polymer)
    return reacted_polymer

polymer = None
with open('input.txt') as input_file:
    polymer=input_file.read().rstrip()

print('Solution to part 1: %s' % (len(getMinPolymer(polymer)),))

min_poly_len = 1000000
for i in range(ord('a'), ord('z')+1):
    mod_polymer = polymer.replace(chr(i), '').replace(chr(i).upper(), '')
    mod_poly_len = len(getMinPolymer(mod_polymer))
    if mod_poly_len < min_poly_len:
        min_poly_len = mod_poly_len

print('Solution to part 2: %i' % (min_poly_len,))
