;;;; Chronal Calibration

;;; Part 1
(defun part1 ()
  (reduce '+ (funcall 'input-to-list)))

;;; Part 2 - I'm doing something particularly inefficient here, part2 takes a
;;; minute or more to execute.
(defun part2 ()
  (defparameter adjustments (funcall 'input-to-circular-list))
  (loop for adjustment in adjustments with total = 0 with seen = '() do
       (setq total (+ total adjustment))
       (if (find total seen) (return total))
       (push total seen)))

;;; Get a list of the input data.
(defun input-to-list ()
  (with-open-file (input "input.txt")
    (loop for adjustments = (read-line input nil)
       while adjustments
       collect (parse-integer adjustments))))

;;; Get a circular list of the input data.
(defun input-to-circular-list()
  (defparameter adjustments (funcall 'input-to-list))
  (setf (cdr (last adjustments)) adjustments))

(format t "~@d~%" (funcall 'part1))
(format t "~@d~%" (funcall 'part2))
