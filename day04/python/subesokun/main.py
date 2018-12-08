from datetime import datetime
import operator

guard_map = {}
current_guard_id = None
evt_list = []

def dateKey(elem):
    return elem[0]

# Populate fabric pieces and claim overlap maps
with open('input.txt') as input_file:
    for evt_str in input_file:
        evt_date_str, evt_content = evt_str.rstrip().split('] ', 1)
        evt_date = datetime.strptime(evt_date_str[1:], '%Y-%m-%d %H:%M')
        evt_list.append((evt_date, evt_content))

# Sort events
evt_list.sort(key=dateKey)

for evt in evt_list:
    evt_date, evt_content = evt
    evt_type = evt_content[:1]
    if evt_type == 'w':
        sleep_evt_date = guard_map[current_guard_id]['ts']
        guard_map[current_guard_id]['seconds'] += int((evt_date-sleep_evt_date).total_seconds())
        guard_map[current_guard_id]['ts'] = None
        guard_minutes_map = guard_map[current_guard_id]['minutes']
        for min in range(sleep_evt_date.minute, evt_date.minute):
            guard_minutes_map[min] = guard_minutes_map.get(min, 0) + 1
    elif evt_type == 'f':
        guard_map[current_guard_id]['ts'] = evt_date
    elif evt_type == 'G':
        guard_id = int(evt_content.split(' ', 2)[1][1:])
        guard_map[guard_id] = guard_map.get(guard_id, {'seconds': 0, 'ts': None, 'minutes': {}})
        current_guard_id = guard_id

# Find guard who sleeps the most seconds in total
max_sleep_guard_id = max(guard_map.keys(), key=(lambda k: guard_map[k]['seconds']))

# Find minute with most sleep events of guard
minutes_map = guard_map[max_sleep_guard_id]['minutes']
max_sleep_minute = max(minutes_map.keys(), key=(lambda k: minutes_map[k]))

print('Solution to part 1: %i' % (max_sleep_guard_id * max_sleep_minute,))

current_max_sleep_guard_id, current_max_sleep_minute, current_max_sleep_minute_cnt = None, None, 0
for guard_id, guard_data in guard_map.items():
    minutes_map = guard_data['minutes']
    if len(minutes_map) > 0:
        guard_max_sleep_minute = max(minutes_map.keys(), key=(lambda k: minutes_map[k]))
        guard_max_sleep_minute_cnt = minutes_map[guard_max_sleep_minute]
        if current_max_sleep_minute_cnt < guard_max_sleep_minute_cnt:
            current_max_sleep_minute = guard_max_sleep_minute
            current_max_sleep_minute_cnt = guard_max_sleep_minute_cnt
            current_max_sleep_guard_id = guard_id

print('Solution to part 2: %i' % (current_max_sleep_guard_id * current_max_sleep_minute,))
