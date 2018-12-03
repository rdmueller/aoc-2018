def same_letters(a, b):
    found_letters = []

    for i in range(len(a) - 1):
        letter = a[i]
        if letter == b[i]:
            found_letters.append(letter)
    return found_letters


words = open('input').readlines()
counts = []
for letters in words:
    count = {}
    counts.append(count)
    for letter in letters:
        if letter in count:
            count[letter] += 1
        else:
            count[letter] = 1

repeated_2 = 0
repeated_3 = 0
for c in counts:
    counted_2 = False
    counted_3 = False
    for v in c.values():
        if v == 2 and not counted_2:
            repeated_2 += 1
            counted_2 = True
        elif v == 3 and not counted_3:
            repeated_3 += 1
            counted_3 = True
checksum = repeated_2 * repeated_3
print(checksum)

top_found = []
for w in words:
    for z in words:
        if w != z:
            letters = same_letters(w, z)
            if len(letters) > len(top_found):
                top_found = letters

print(''.join(top_found))
