;a) Write a function to return the difference of two sets.

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

;differenceOfSets(s1 s2)
;nil - s1 null
;s11 + differenceOfSets(s12..s1n s2) - contains(s2 s11) false
;differenceOfSets(s12..s1n, s2)

(defun differenceOfSets(s1 s2)
    (cond
        ((null s1) nil)
        ((not (contains s2 (car s1))) (cons (car s1) (differenceOfSets (cdr s1) s2)))
        (T (differenceOfSets (cdr s1) s2))
    )
)

(print (differenceOfSets '(1 2 3 4 5) '(2 3 4 5 6 7)))

;b) Write a function to reverse a list with its all sublists, on all levels.

;myAppend(l e)
;e - l null
;l1 U myAppend(l2..ln,e)

(defun myAppend(l e)
    (cond
        ((null l) e)
        (T (cons (car l) (myAppend (cdr l) e)))
    )
)

;myReverse(l)
;nil - l null
;myReverse(l2..ln) + myReverse(l1) - l1 list
;myReverse(l2..ln) U l1 - otherwise

(defun myReverse(l)
  (cond
    ((null l) nil)
    ((listp (car l)) (myAppend (myReverse (cdr l)) (list (myReverse (car l)))))
    (T (myAppend (myReverse (cdr l)) (list (car l))))
  )
)

(print (myReverse '(1 2 (3 (4 5) (6 7)) 8 (9 10 11))))

;c) Write a function to return the list of the first elements of all list elements of a given list with an odd
;number of elements at superficial level. Example:
;(1 2 (3 (4 5) (6 7)) 8 (9 10 11)) => (1 3 9).

;myLength(l)
;0 - l null
;1 + myLength(l2..ln)

(defun myLength(l)
    (cond
        ((null l) 0)
        (T (+ 1 (myLength (cdr l))))
    )
)

(print (myLength '(1 2 3 4)))

;oddListLength(l)
;myLength(l) mod 2 = 1

(defun oddListLength(l)
        (= (mod (myLength l) 2) 1)
)

;firstElem(l)
;nil - l atom
;l1 + firstElem(l2) + .. + firstElem(ln) - oddListLength(l) true
;firstElem(l2) + .. + firstElem(ln) - otherwise

(defun firstElem(l)
    (cond
        ((atom l) nil)
        ((oddListLength l) (cons (car l) (apply 'append (mapcar 'firstElem (cdr l)))))
        (T (apply 'append (mapcar 'firstElem (cdr l))))
    )
)

(print (firstElem '(1 2 (3 (4 5) (6 7)) 8 (9 10 11))))


;d) Write a function to return the sum of all numerical atoms in a list at superficial level.

;sumNrAtm(l)
;0-l null
; l1 + sumNrAtm(l2..ln) l1 number
; sumNrAtm(l2..ln) -otherwise

(defun sumNrAtm(l)
    (cond
        ((null l) 0)
        ((numberp (car l)) (+ (car l) (sumNrAtm (cdr l))))
        (T (+ 0 (sumNrAtm (cdr l))))
    )
)

(print (sumNrAtm '(1 2 (3 (4 5) (6 7)) 8 (9 10 11))))

