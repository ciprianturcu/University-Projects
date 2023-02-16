;a) Determine the least common multiple of the numerical values of a nonlinear list.

(defun _lcm (a b)
    (cond
        ((and (not (numberp a)) (not (numberp b))) nil)
        ((not (numberp a)) b)
        ((not (numberp b)) a)
        (T (/ (* a b) (_gcd a b)))
    )
)

(defun _gcd(a b)
    (cond
        ((and (not (numberp a)) (not (numberp b))) nil)
        ((not (numberp a)) b)
        ((not (numberp b)) a)
        ((equal b 0) a)
        (T (_gcd b (mod a b)))
    )
)

;listLcm(l)
;nil - l null
;l1 - l1 atom and l2...ln null
;lcm(l1, listLcm(l2..ln)) 
(defun listLcm(l)
    (cond
        ((null l) nil)
        ((and (atom (car l)) (null (cdr l))) (car l))
        (T (_lcm (car l) (listLcm (cdr l))))
    )
)

(print (listLcm '(24 ( 16 (12 A B)) 72)))

;b) Write a function to test if a linear list of numbers has a "mountain" aspect (a list has a "mountain"
;aspect if the items increase to a certain point and then decreases.
;Eg. (10 18 29 17 11 10). The list must have at least 3 atoms to fullfil this criteria.

;myLength(l)
;0 - l null
;1 + myLength(l2..ln)

(defun myLength(l)
    (cond
        ((null l) 0)
        (T (+ 1 (myLength (cdr l))))
    )
)

(defun _mountain (l decreasing)
	(cond
		((= (list-length l) 1) (if decreasing nil T))
		((and (< (car l) (cadr l)) (not decreasing)) nil)
		((and (> (car l) (cadr l)) decreasing) (_mountain (cdr l) nil))
		(T (_mountain (cdr l) decreasing))
	)
)

(defun mountain(l)
    (cond
        ((< (myLength l) 3) nil)
        (T (_mountain l T))  
    )
)

(print (mountain '(10 18 29 17 11 10)))

(print (mountain '(10 18 29 17 11 29 10)))



;c) Remove all occurrences of a maximum numerical element from a nonlinear list.

(defun maxNb (a b)
    (if (> a b) a b)
)


(defun maxNum (l)
    (cond
        ((null l) -1)
        ((and (null (cdr l)) (numberp (car l))) (car l))
        ((numberp (car l)) (maxNb (car l) (maxNum (cdr l))))
        ((listp (car l)) (maxNb (maxNum (car l)) (maxNum (cdr l))))
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

(print (solve '(1 2 (3 (a) (1 3)) 4 5)))

;d) Write a function which returns the product of numerical even atoms from a list, to any level.

;prodEvenAtm(l)
;1 - l null
;prodEvenAtm(l1) * prodEvenAtm(l2..ln) - l1 list
;l1* prodEvenAtm(l2..ln) - l1 number
;1* prodEvenAtm(l2..ln) - otherwise

(defun prodEvenAtm(l)
    (cond
        ((null l) 1)
        ((listp (car l)) (* (prodEvenAtm (car l)) (prodEvenAtm (cdr l))))
        ((and (numberp (car l)) (= (mod (car l) 2) 0)) (* (car l) (prodEvenAtm (cdr l))))
        (T (* 1 (prodEvenAtm (cdr l))))
    )
)

(print (prodEvenAtm '(1 2 3 (4 5 (6)) (7))))