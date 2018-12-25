;;;; Inventory Management System

;;; Part 1
(defun part1 ()
  (defvar ids (sort-list-ids (input-to-list)))
  (loop for id in ids with total-twos = 0 with total-threes = 0
     while id do
       (if (equal t (id-has-two id)) (incf total-twos))
       (if (equal t (id-has-three id)) (incf total-threes))
       finally (return (* total-twos total-threes))))

;;; Part 2
(defun part2 ()
  (defvar outer-list (input-to-list))
  (defvar inner-list (copy-list outer-list))
  (loop named outer for outer-id in outer-list
     while outer-id do
       (loop named inner for inner-id in inner-list with mismatch-index = 0
          while inner-id do
            (setf mismatch-index (mismatch outer-id inner-id))
            (when (not (equal nil mismatch-index))
              (if (equal nil (mismatch outer-id inner-id :start1 (+ 1 mismatch-index) :start2 (+ 1 mismatch-index)))
                (return-from outer (remove (elt outer-id mismatch-index) outer-id :start mismatch-index :end (+ 1 mismatch-index))))))))

;;; Sort each item in the list alphabetically.
(defun sort-list-ids (ids)
  (loop for id in ids do
       (setf id (sort id #'char-lessp))
       finally (return ids)))

;;; Does the item have exactly two of a character?
(defun id-has-two (id)
  (loop while (> (length id) 0) with char = nil do
       (setf char (elt id 0))
       (when (eql 2 (count char id :test #'equal))
         (return-from id-has-two t))
       (setf id (remove char id))))

;;; Does the item have exactly three of a character?
(defun id-has-three (id)
  (loop while (> (length id) 0) with char = nil do
       (setf char (elt id 0))
       (when (eql 3 (count char id :test #'equal))
         (return-from id-has-three t))
       (setf id (remove char id))))

;;; Get a list of the input data.
(defun input-to-list ()
  (with-open-file (input "input.txt")
    (loop for ids = (read-line input nil)
       while ids
       collect ids)))

(format t "~@a~%" (part1))
(format t "~@a~%" (part2))
