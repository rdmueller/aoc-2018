;;;; Chronal Calibration

;;; Part 1
(with-open-file (input "input.txt")
  (loop for change = (read-line input nil)
        while change
        sum (parse-integer change) into frequency
        finally (return (format t "~@d~%" frequency))))
