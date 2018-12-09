INPUT_FILE_NAME = 'input.txt'

class TreeNode:
    def __init__(self):
        self.children = []
        self.metadata = []

    def addChild(self, child):
        self.children.append(child)

    def addMetadata(self, metadata_list):
        self.metadata.extend(metadata_list)

    def sumMetadata(self):
        child_metadata_sum = 0
        if len(self.children) > 0:
            child_metadata_sum = sum([c.sumMetadata() for c in self.children])
        return sum(self.metadata) + child_metadata_sum

    def getValue(self):
        if len(self.children) == 0:
            return sum(self.metadata)
        else:
            result = 0
            len_children = len(self.children)
            for m in self.metadata:
                if m - 1 < len_children:
                    result += self.children[m - 1].getValue()
            return result

license_input = None
with open(INPUT_FILE_NAME) as input_file:
    license_input = [int(n) for n in input_file.read().rstrip().split(' ')]

def generateTree(input):
    node = TreeNode()
    header_cnt_children = input[0]
    header_cnt_metadata = input[1]
    current_index = 2
    for _ in range(header_cnt_children):
        child_node, delta_index = generateTree(input[current_index:])
        current_index += delta_index
        node.addChild(child_node)
    node.addMetadata(input[current_index:current_index+header_cnt_metadata])
    return node, current_index+header_cnt_metadata

root_node, _ = generateTree(license_input)
print('Solution to part 1: %i' % (root_node.sumMetadata(),))

print('Solution to part 2: %i' % (root_node.getValue(),))