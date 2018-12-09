NUMBER_WORKER = 5
ITEM_DURATION = 60
INPUT_FILE_NAME = 'input.txt'

class WorkItem:
    def __init__(self, name):
        self.name = name
        self.dependencies = []
        self.completed = False
        self.completed_at = None

    def addDependency(self, item):
        self.dependencies.append(item)

    def isWorkable(self):
        if self.completed: return False
        for p in self.dependencies:
            if not p.completed: return False
        return True

    def startWorking(self, current_time):
        self.completed_at = current_time + ITEM_DURATION + (ord(self.name) - ord('A'))  + 1

def getWorkableItems(work_item_map):
    result = []
    for item in work_item_map.values():
        if item.isWorkable(): result.append(item)
    return result

def workOnItemsSequential(work_item_map):
    instruction = ''
    while True:
        work_items = getWorkableItems(work_item_map)
        if len(work_items) == 0: break
        item = min(work_items, key = lambda item: item.name)
        instruction += item.name
        item.completed = True
    return instruction

def initWorkItemMap(file_name):
    result = {}
    with open(file_name) as input_file:
        for dep_str in input_file:
            tmp = dep_str.rstrip().split(' ')
            dep_work_item = result.get(tmp[1], WorkItem(tmp[1]))
            result[tmp[1]] = dep_work_item
            work_item = result.get(tmp[7], WorkItem(tmp[7]))
            result[tmp[7]] = work_item
            work_item.addDependency(dep_work_item)
    return result

work_item_map = initWorkItemMap(INPUT_FILE_NAME)
instruction = workOnItemsSequential(work_item_map)

print('Solution to part 1: %s' % (instruction,))

# -------------

def workOnItemsParallel(workers, work_item_map):
    current_time = 0
    in_progress_items = {}
    while True:
        # First check if some tasks has been completed
        remove_items = []
        for w, item in in_progress_items.values():
            if item.completed_at == current_time:
                item.completed = True
                w['busy'] = False
                remove_items.append(item)
        for item in remove_items:
            del in_progress_items[item.name]
        # Check if we've completed everything
        work_items = getWorkableItems(work_item_map)
        if len(work_items) == 0 and len(in_progress_items) == 0: break
        # If not, assign available tasks to workers
        for w in workers:
            if not w['busy'] and len(work_items) > 0:
                item = min(work_items, key = lambda item: item.name)
                item.startWorking(current_time)
                w['busy'] = True
                in_progress_items[item.name] = (w, item)
                del work_item_map[item.name]
                work_items = getWorkableItems(work_item_map)
        current_time += 1
    return current_time

work_item_map = initWorkItemMap(INPUT_FILE_NAME)
workers = [{'name': i, 'busy': False} for i in range(NUMBER_WORKER)]
duration = workOnItemsParallel(workers, work_item_map)

print('Solution to part 2: %i' % (duration,))