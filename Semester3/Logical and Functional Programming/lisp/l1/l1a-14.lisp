;a) Write a function to return the union of two sets.

;myAppend(l e)
;e - l null
;l1 U myAppend(l2..ln,e)

(defun myAppend(l e)
    (cond
        ((null l) e)
        (T (cons (car l) (myAppend (cdr l) e)))
    )
)

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

;unionOperation(l1 l2)
;nil - l1 null or l2 null
;l11 + unionOperation(l12..l1n,l2) - contains (l2 l11)
;unionOperation(l12...l1n,l2)

(defun  unionOperation(l1 l2)
    (cond
        ((or (null l1) (null l2)) nil)
        ((contains l2 (car l1)) (myAppend (list (car l1)) (unionOperation (cdr l1) l2)))
        (T (unionOperation (cdr l1) l2))
    )
)

(print (unionOperation '(1 2 3 4 5) '(1 5 6 7 9)))


;b) Write a function to return the product of all numerical atoms in a list, at any level.

(defun prodAtm(l)
    (cond
        ((null l) 1)
        ((listp (car l)) (* (prodAtm (car l)) (prodAtm (cdr l))))
        ((numberp (car l)) (* (car l) (prodAtm (cdr l))))
        (T (* 1 (prodAtm (cdr l))))
    )
)

(print (prodAtm '(1 2 3 (4 5 (6)) (7))))

;c) Write a function to sort a linear list with keeping the double values.

;insert (l e)
;(e) - l null
;(e,l1..ln) - e<=l1
;(l1, insert (l2..ln e)) otherwise

(defun insert(l e)
    (cond
        ((null l) (list e))
        ((<= e (car l)) (cons e l))
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

(print (sortare '(2 1 3 3 4 5 6 1 1 1 9 8 7)))


;d) Build a list which contains positions of a minimum numeric element from a given linear list.

;myAppend(l e)
;e - l null
;l1 U myAppend(l2..ln,e)

(defun myAppend(l e)
    (cond
        ((null l) e)
        (T (cons (car l) (myAppend (cdr l) e)))
    )
)

(defun minNb (a b)
    (if (< a b) a b)
)

(defun myMinim(l)
    (cond
        ((null l) nil)
        ((and (null (cdr l)) (numberp (car l))) (car l))
        ((numberp (car l)) (minNb (car l) (myMinim (cdr l))))
        (T (myMinim (cdr l)))
    )
)

(print (myMinim '(3 2 5 6 2 7 7 2 3 9)))

(defun buildListMin(l pos m)
    (cond
        ((null l) nil)
        ((equal m (car l)) (cons pos (buildListMin (cdr l) (+ 1 pos) m)))
        (T (buildListMin (cdr l) (+ 1 pos) m))
    )
)

(defun solve(l)
    (buildListMin l 1 (myMinim l))
)

(print (solve '(3 2 5 6 2 7 7)))