;a) A linear list is given. Eliminate from the list all elements from N to N steps, N-given.

;removeNN(l n k)
;nil - l null
;removeNN(l2..ln,n,n) - k=1
;l1 + removeNN (l2..ln,n,k-1)

(defun _removeNN(l n k)
	(cond
		((null l) nil)
		((= k 1) (_removeNN (cdr l) n n))
		(T (cons (car l) (_removeNN (cdr l) n (- k 1))))
	)
)
(defun removeNN (l n)
	(_removeNN l n n)
)

(print (removeNN '(1 2 3 4 5 6 7 8) 3))

;b) Write a function to test if a linear list of integer numbers has a "valley" aspect (a list has a valley
;aspect if the items decrease to a certain point and then increase. Eg. 10 8 6 17 19 20). A list must have
;at least 3 elements to fullfill this condition.

(defun _valley (l decreasing)
	(cond
		((= (list-length l) 1) (if decreasing nil T))
		((and (> (car l) (cadr l)) (not decreasing)) nil)
		((and (< (car l) (cadr l)) decreasing) (_valley (cdr l) nil))
		(T (_valley (cdr l) decreasing))
	)
)

(defun valleyAspect(l)
    (cond
        ((null l) nil)
        (T (_valley l T))
    )
)

(print (valleyAspect '(5 4 3 4)))


;c) Build a function that returns the minimum numeric atom from a list, at any level.

(defun minNb (a b)
    (if (< a b) a b)
)



(defun myMinim(l)
    (cond
        ((null l) nil)
        ((and (null (cdr l)) (numberp (car l))) (car l))
        ((numberp (car l)) (minNb (car l) (myMinim (cdr l))))
        ((listp (car l)) (minNb (myMinim (car l)) (myMinim (cdr l))))
        (T (myMinim (cdr l)))
    )
)


;d) Write a function that deletes from a linear list of all occurrences of the maximum element.

(defun maxNb (a b)
    (if (> a b) a b)
)


(defun maxNum (l)
    (cond
        ((null l) -1)
        ((and (null (cdr l)) (numberp (car l))) (car l))
        ((numberp (car l)) (maxNb (car l) (maxNum (cdr l))))
        (t (maxNum (cdr l)))
     )
)

;replaceElement(l e)
;nil - l
;replaceElement(l e ) - l1 = e  
;l1 +replaceElement(l e) 

(defun replaceElement(l e)
    (cond 
        ((null l) nil)
        ((equal e (car l)) (replaceElement (cdr l) e))
        (T (cons (car l) (replaceElement (cdr l) e)))
    )
)

(defun solve(l)
    (replaceElement l (maxNum l))
)

(print (solve '(1 2 5 1 1 4 5)))