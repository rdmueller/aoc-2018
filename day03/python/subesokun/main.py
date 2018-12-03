
fabric_piece_map = {}
claim_overlap_map = {}

def getClaimObject(claim_str):
    # Hopefully, faster than regex :o)
    parts = claim_str[1:].split(' ')
    claim_id = int(parts[0])
    coords = parts[2][:-1].split(',')
    left_coord, top_coord = int(coords[0]), int(coords[1])
    dimensions = parts[3].split('x')
    width, height = int(dimensions[0]), int(dimensions[1])
    return {
        'claim_id': claim_id,
        'position': (left_coord, top_coord),
        'height': height,
        'width': width
    }

def countOverlaps():
    claim_overlaps = 0
    for claim_id_list in fabric_piece_map.values():
        if len(claim_id_list) > 1:
            claim_overlaps += 1
    return claim_overlaps

def getUniqueNoneOverlappingClaim():
    for claim_id_list in fabric_piece_map.values():
        if len(claim_id_list) > 1:
            # Mark all claims in the fabric piece as overlapping
            for claim_id in claim_id_list:
                claim_overlap_map[claim_id] = True
    for claim_id, claim_is_overlapping in claim_overlap_map.items():
        if not claim_is_overlapping:
            return claim_id

# Populate fabric pieces and claim overlap maps
with open('input.txt') as claim_file:
    for claim_str in claim_file:
        claim = getClaimObject(claim_str.rstrip())
        # Initially, assume that the claim has no overlap
        claim_overlap_map[claim['claim_id']] = False
        for x in range(claim['position'][0], claim['position'][0] +  claim['width']):
            for y in range(claim['position'][1], claim['position'][1] +  claim['height']):
                fabric_piece_claims = fabric_piece_map.get((x, y), [])
                fabric_piece_claims.append(claim['claim_id'])
                fabric_piece_map[(x, y)] = fabric_piece_claims

print('Solution to part 1: %i' % (countOverlaps(),))
print('Solution to part 2: %i' % (getUniqueNoneOverlappingClaim(),))
