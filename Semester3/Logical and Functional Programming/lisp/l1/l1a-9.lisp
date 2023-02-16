;a) Write a function that merges two sorted linear lists and keeps double values.

;mergeSortedL(l1 l2)
; l2 - l1 null
; l1 - l2 null
;l11 + mergeSortedL(l12..l1n,l2) - l11 <= l21
;l21 + mergeSortedL(l1 , l22..l2n)

(defun mergeSortedL(l1 l2)
    (cond
        ((null l1) l2)
        ((null l2) l1)
        ((<= (car l1) (car l2)) (cons (car l1) (mergeSortedL (cdr l1) l2)))
        (T (cons (car l2) (mergeSortedL l1 (cdr l2))))
    )
)

(print (mergeSortedL '(1 2 3 3) '(3 4 4 5 5 7)))

;b) Write a function to replace an element E by all elements of a list L1 at all levels of a given list L.

(defun replaceElementL(l e o)
    (cond 
        ((null l) nil)
        ((listp (car l)) (cons (replaceElementL (car l) e o) (replaceElementL (cdr l) e o)))
        ((equal e (car l)) (cons o (replaceElementL (cdr l) e o)))
        (T (cons (car l) (replaceElementL (cdr l) e o)))
    )
)

(print (replaceElementL '(1 2 3 4 (4 6 6 (6)) 5 6 7 6 4 6) 6 '(1 2 3 4)))


;c) Write a function to determines the sum of two numbers in list representation, and returns the
;corresponding decimal number, without transforming the representation of the number from list to
;number.

;digit(l k c)
;k+c mod 10 - l null
;l+c mod 10 - k null
;l+c+k mod 10
(defun digit (l k c)
    (cond
        ((null l) (mod (+ k c) 10))
        ((null k) (mod (+ l c) 10))
        (T (mod (+ l k c) 10))
    )
)

;carry(l k c)
;k + c mod 10 - l null and k+c < 9
;k + c div 10 - l null and k+c > 9
;l + c mod 10 - k null and l+c < 9
;l + c div 10 - k null and l+c > 9
;l + k + c mod 10 - l+k+c < 9
;l + k + c div 10 - l+k+c > 9

(defun carry (l k c)
    (cond
        ((null l) (if (> (- (+ k c) (mod (+ k c) 10)) 9) 
                      (/ (- (+ k c) (mod (+ k c) 10)) 10) 
                      (mod (+ k c) 10)
                  )
        )
        ((null k) (if (> (- (+ l c) (mod (+ l c) 10)) 9)
                      (/ (- (+ l c) (mod (+ l c) 10)) 10) 
                      (mod (+ l c) 10)
                  )
        )
        (T (if  (> (- (+ l k c) (mod (+ l k c) 10)) 9)
                (/ (- (+ l k c) (mod (+ l k c) 10)) 10) 
                (mod (+ l k c) 10)
            )
        )
    )
)
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

;sumList(l k c)
;nil - c = 0 and l null and k null
;c - l null k null
;sumList(l2..ln k2..kn carry(l1 k2 c))+digit(l1 l2 c)

(defun sumList (l k c)
    (cond
        ((and (null l) (null k)) (if (= 1 c) (list 1) nil))
        (T (myAppend (sumList (cdr l) (cdr k) (carry (car l) (car k) c)) (list (digit (car l) (car k) c))))        
    )
)

(defun solve (l k)
    (sumList (myReverse l) (myReverse k) 0)
)
(print (solve '(9 9) '(1 2)))


;d) Write a function to return the greatest common divisor of all numbers in a linear list.



(defun _gcd(a b)
    (cond
        ((and (not (numberp a)) (not (numberp b))) nil)
        ((not (numberp a)) b)
        ((not (numberp b)) a)
        ((equal b 0) a)
        (T (_gcd b (mod a b)))
    )
)

;listGcd(l)
;nil - l null
;l1 - l1 atom and l2...ln null
;gcd(l1, listGcd(l2..ln)) 
(defun listGcd(l)
    (cond
        ((null l) nil)
        ((and (atom (car l)) (null (cdr l))) (car l))
        (T (_gcd (car l) (listGcd (cdr l))))
    )
)

(print (listGcd '(24 16 12 ( 16 (12 A B)) 72)))