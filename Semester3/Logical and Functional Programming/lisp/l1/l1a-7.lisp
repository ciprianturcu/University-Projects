;a) Write a function to eliminate the n-th element of a linear list.
 
;eliminate(l n pos)
;nil - null l
;l2...ln - n = 1
;l1 + eliminate(l2..ln n-1)

(defun eliminate(l n)
    (cond
        ((null l) nil)
        ((= n 1) (cdr l))
        (T (cons (car l) (eliminate (cdr l) (- n 1))))
    )
)

(print (eliminate '(1 2 3 4 5 6 7 8) 5))


;b) Write a function to determine the successor of a number represented digit by digit as a list, without
;transforming the representation of the number from list to number. 
;Example: (1 9 3 5 9 9) --> (1 9 3 6 0 0)

(defun successor (list carry)
  (cond 
   ((null number-list) (if (> carry 0) (list carry) nil))
   (t (let ((new-digit (+ (car number-list) carry)))
        (cons (mod new-digit 10) (recursive-successor (cdr number-list) (if (>= new-digit 10) 1 0)))))))


;c) Write a function to return the set of all the atoms of a list.
; Exemplu: (1 (2 (1 3 (2 4) 3) 1) (1 4)) ==> (1 2 3 4)

;myAppend(l e)
;e - l null
;l1 U myAppend(l2..ln,e)

(defun myAppend(l e)
    (cond
        ((null l) e)
        (T (cons (car l) (myAppend (cdr l) e)))
    )
)

;liniarizare(l)
;nil - l null
;l1 + liniarizare(l2..ln) - l1 number
;liniarizare(l1) + liniarizare(l2..ln) - l1 list
;liniarizare(l2..ln)

(defun liniarizare(l)
    (cond
        ((null l) nil)
        ((numberp (car l)) (cons (car l) (liniarizare (cdr l))))
        ((listp (car l)) (myAppend (liniarizare (car l)) (liniarizare (cdr l))))
        (T (liniarizare (cdr l)))
    )
)



;insert (l e)
;(e) - l null
;(l1..ln) - e == ln
;(e,l1..ln) - e<l1
;(l1, insert (l2..ln e)) otherwise

(defun insert(l e)
    (cond
        ((null l) (list e))
        ((equal (car l) e) l)
        ((< e (car l)) (cons e l))
        (T (cons (car l) (insert (cdr l) e )))
    )
)

;sortare(l)
;nil - l null
;insert(sortare (cdr l) (car e))

(defun sortare(l)
    (cond
        ((null l) nil)
        (T (insert (sortare (cdr l)) (car l)))
    )
)

(defun solve (l)
    (sortare (liniarizare l))
)

(print (solve '(1 (2 (1 3 (2 4) 3) 1) (1 4))))

;d) Write a function to test whether a linear list is a set.

;contains(l e)
;nil - l null
;T - l1 =e
;contains(l2..ln,3) -otherwise

(defun contains(l e)
    (cond
        ((null l) nil)
        ((equal (car l) e ) T)
        (T (contains (cdr l) e ))
    )
)

;checkSet(l)
;t - l null
;nil - contains(l2..ln, l1)
;checkSet(l2..ln)

(defun checkSet(l)
    (cond
        ((null l) T)
        ((contains (cdr l) (car l)) nil)
        (T (checkSet (cdr l)))
    )
)

(print (checkSet '(1 2 3 4)))


(print (checkSet '(1 2 2 3 4)))
