CREATE TABLE IF NOT EXISTS data
(
  x smallint,
  y smallint,
  CONSTRAINT data_pk PRIMARY KEY (x,y)
);

COPY data(x,y) FROM 'C:\Projects\aoc\aoc-2018\day06\sql\highsource\input.txt' DELIMITER ',' CSV;

CREATE OR REPLACE VIEW coords AS 
SELECT
	chr(
		CASE
			WHEN id <= 26 THEN id + 64
			ELSE id + 70
		END) as id,
	x,
	y
FROM
	(
		SELECT
			CAST (row_number() OVER (ORDER BY x, y) AS int) AS id,
			data.x,
			data.y
		FROM DATA
	) AS DATA_WITH_IDS;

CREATE OR REPLACE VIEW data_dimensions AS
SELECT
	MIN(x) AS minX,
	MIN(y) AS minY,
	MAX(x) AS maxX,
	MAX(y) AS maxY
FROM data;

CREATE OR REPLACE VIEW data_dimensions_buffered AS
SELECT
	minX - 100 as bufferedMinX,
	minY - 100 as bufferedMinY,
	maxX + 100 as bufferedMaxX,
	maxY + 100 as bufferedMaxY
FROM data_dimensions;

CREATE OR REPLACE VIEW board AS
SELECT *
FROM
	(
		SELECT
			generate_series(bufferedMinX, bufferedMaxX) as x
		FROM
			data_dimensions_buffered
	) AS xs,
	(
		SELECT
			generate_series(bufferedMinY, bufferedMaxY) as y
		FROM
			data_dimensions_buffered
	) AS ys;


CREATE OR REPLACE VIEW board_with_distance_per_coord AS
SELECT
	coords.id AS coord_id,
	coords.x AS coord_x,
	coords.y AS coord_y,
	board.x AS board_x,
	board.y AS board_y,
	abs(coords.x - board.x) + abs(coords.y - board.y) as distance
FROM
	coords,
	board;

CREATE OR REPLACE VIEW board_with_distance AS
SELECT
	board_x, board_y, min(distance) as min_distance
FROM
	board_with_distance_per_coord
GROUP BY
	board_x, board_y;


CREATE OR REPLACE VIEW board_with_min_distance_per_coord AS
SELECT
	board_with_distance_per_coord.board_x,
	board_with_distance_per_coord.board_y,
	board_with_distance_per_coord.coord_id,
	board_with_distance_per_coord.coord_x,
	board_with_distance_per_coord.coord_y,
	board_with_distance.min_distance
FROM
	board_with_distance_per_coord,
	board_with_distance
WHERE
	board_with_distance_per_coord.board_x = board_with_distance.board_x AND
	board_with_distance_per_coord.board_y = board_with_distance.board_y AND
	board_with_distance_per_coord.distance = board_with_distance.min_distance;

CREATE OR REPLACE VIEW board_with_min_distance_coord_id AS
SELECT
	board_x, board_y, min(board_with_min_distance_per_coord.coord_id) as coord_id
FROM
	board_with_min_distance_per_coord
GROUP BY
	board_x, board_y, min_distance
HAVING
	COUNT(*) = 1;

CREATE OR REPLACE VIEW board_border_coord_id AS
SELECT
	DISTINCT(coord_id) as coord_id
FROM
	board_with_min_distance_coord_id,
	data_dimensions_buffered
WHERE
	board_with_min_distance_coord_id.board_x = data_dimensions_buffered.bufferedminx OR
	board_with_min_distance_coord_id.board_y = data_dimensions_buffered.bufferedminy OR
	board_with_min_distance_coord_id.board_x = data_dimensions_buffered.bufferedmaxx OR
	board_with_min_distance_coord_id.board_y = data_dimensions_buffered.bufferedmaxy;

SELECT
	coord_id,
	COUNT(*) as area
FROM
	board_with_min_distance_coord_id
WHERE
	board_with_min_distance_coord_id.coord_id NOT IN (SELECT coord_id FROM board_border_coord_id)
GROUP BY
	coord_id
ORDER BY
	area DESC
LIMIT 1;

CREATE OR REPLACE VIEW board_with_total_distance AS
SELECT
	board_x, board_y, sum(distance) as total_distance
FROM
	board_with_distance_per_coord
GROUP BY
	board_x, board_y;

SELECT
	COUNT(*)
FROM
	board_with_total_distance
WHERE
	total_distance < 10000;