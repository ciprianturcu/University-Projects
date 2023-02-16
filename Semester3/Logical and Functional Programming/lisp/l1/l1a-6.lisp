;a)Write a function to test whether a list is linear.

;linearList(l)
;T - l null
;nil - l1 list
;linerList(l2..ln)

(defun linearList(l)
    (cond
        ((null l) T)
        ((listp (car l)) nil)
        (T (linearList (cdr l)))
    )
)

(print (liniarList '(a (b c) (d (e (f))))))

(print (liniarList '(1 2 3 4)))



;b)Write a function to replace the first occurence of an element E in a given list with an other element O.

;replaceFirstElement(l e o)
;nil - l
;o + replaceFirstElement(l e o) - l1 = e  
;l1 +replacefirstElement(l e o) 

(defun replaceFirstElement(l e o)
    (cond 
        ((null l) nil)
        ((equal e (car l)) (cons o (cdr l)))
        (T (cons (car l) (replaceFirstElement (cdr l) e o)))
    )
)

(print (replaceFirstElement '(1 2 3 4 5 6 7 6 4 6) 6 0))




;c)Write a function to replace each sublist of a list with its last element.
;A sublist is an element from the first level, which is a list.
;Example: 
;(a (b c) (d (e (f)))) ==> (a c (e (f))) ==> (a c (f)) ==> (a c f)
;(a (b c) (d ((e) f))) ==> (a c ((e) f)) ==> (a c f)

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
;myReverse(l2..ln) U l1 - otherwise

(defun myReverse(l)
  (cond
    ((null l) nil)
    ((myAppend (myReverse (cdr l)) (list (car l))))
  )
)

(defun lastElement (l)
	(if (listp l) 
        (lastElement (car (myReverse l)))
        l
    )
)

;_reduce(l)
;nil - l null
;lastElement(l1) U _reduce(l2..ln) - l1 list
;l1 U reduce (l2..ln)

(defun _reduce(l)
    (cond
        ((null l) nil)
        ((listp (car l)) (cons (lastElement (car l)) (_reduce (cdr l))))
        (T (cons (car l) (_reduce (cdr l))))
    )
)

(print (_reduce '(a (b c) (d (e (f))))))

;d)Write a function to merge two sorted lists without keeping double values.

;mergeSortedL(l1 l2)
;nil - l1 and l2 null
;l21 + mergeSortedL(l1 l2..ln) - l1 null
;l11 + mergeSortedL(l12...l1n l2) - l2 null
;l11 + mergeSortedL(l12...l1n, l22..l2n) l11 = l21 
;l11 + mergeSortedL(l12..l1n,l2) - l11 < l21
;l12 + mergeSortedL(l1 , l22..l2n)

(defun mergeSortedL(l1 l2)
    (cond
        ((null l1) l2)
        ((null l2) l1)
        ((<= (car l1) (car l2)) (cons (car l1) (mergeSortedL (cdr l1) l2)))
        (T (cons (car l2) (mergeSortedL l1 (cdr l2))))
    )
)

;removeDoublesL(l)
;nil - l null
;l1 - l2..ln null
;removeDoublesL(l2..ln) - l1 = l2
;l1 + removeDoubles(l2..ln)

(defun removeDoublesL(l)
    (cond
        ((null l) nil)
        ((null (cdr l)) (list (car l)))
        ((= (car l) (cadr l)) (removeDoublesL (cdr l)))
        (T (cons (car l) (removeDoublesL (cdr l))))
    )

)

(defun solve(l1 l2) 
    (removeDoublesL (mergeSortedL l1 l2))
)

(print (solve '(1 2 3 3) '(3 4 4 5 5 7)))