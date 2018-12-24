;;;; Inventory Management System

;;; Part 1
(defun part1 ()
  (defvar ids (sort-list-ids (input-to-list)))
  (loop for id in ids with total-twos = 0 with total-threes = 0
     while id do
       (if (equal t (id-has-two id)) (incf total-twos))
       (if (equal t (id-has-three id)) (incf total-threes))
       finally (return (* total-twos total-threes))))

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
