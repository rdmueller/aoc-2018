lines = open('input').readlines()
numbers = []
for line in lines:
    numbers.append(int(line))

print(sum(numbers))

found_frequencies = {}
frequency_found_twice = False

frequency = 0
while not frequency_found_twice:
    for number in numbers:
        frequency = frequency + number
        if frequency in found_frequencies:
            frequency_found_twice = True
            break
        else:
            found_frequencies[frequency] = True

print(frequency)
