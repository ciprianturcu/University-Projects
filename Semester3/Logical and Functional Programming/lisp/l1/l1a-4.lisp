;a) Write a function to return the sum of two vectors.a

;sum2(l1 l2)
;l2 - l1 null
;l1 - l2 null
;(l11 + l21) + sum2(l12...l1n,l22...l2m)

(defun sum2(l1 l2)
    (cond
        ((null l1) l2)
        ((null l2) l1)
        (T (mapcar #'+ l1 l2))
    )
)

(print (sum2 '(1 2 3) '(4 5 6)))


;b) Write a function to get from a given list the list of all atoms, on any
;level, but on the same order. Example:
;(((A B) C) (D E)) ==> (A B C D E)

;transf(l)
;nil l null
;transf(l1) U transf(l2...ln) - l1 list
;l1 U transf(l2..ln) - l1 not list

; myAppend(l1l2...ln, p1p2...pm) = 
; = p1p2...pm, if n = 0
; = {l1} U myAppend(l2...ln, p1p2...pm), otherwise

(defun myAppend(l e)
    (cond
        ((null l) e)
        (T (cons (car l) (my_append (cdr l) e)))
    )
)


(defun transf(l)
    (cond
        ((null l) nil)
        ((listp (car l)) (my_append (transf (car l)) (transf (cdr l))))
        (T (my_append (list (car l)) (transf (cdr l))))
    )
)

(print (transf '(((A B) C) (D E))))

;c) Write a function that, with a list given as parameter, inverts only continuous
;sequences of atoms. Example:
;(a b c (d (e f) g h i)) ==> (c b a (d (f e) i h g))

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

;rev(l aux)
;myReverse aux - l null
;myAppend(myReverse aux , rev(l1,nil) U rev(l2..ln,nil)) - l1 list
;rev(l2..ln,myAppend(aux,l1))


(defun rev(l aux)
  (cond
    ((null l) (myReverse aux))
    ((listp (car l)) (myAppend (myReverse aux) (cons (rev (car l) nil) (rev (cdr l) nil))))
    (T (rev (cdr l) (myAppend aux (list (car l)))))
  )
)

(defun solve(l)
  (rev l nil)
)

(print (solve '(a b c (d (e f) g h i))))

;d)Write a list to return the maximum value of the numerical atoms from a list, at superficial level.

(defun myMax (a b)
    (cond
        ((and (not (numberp a)) (not (numberp b))) nil)
        ((not (numberp a)) b)
        ((not (numberp b)) a)
        ((> a b) a)
        (T b)
    )
)

;maxList(l)
;nil - l null
;myMax(l1, maxList(l2..ln))

(defun maxList(l)
  (cond 
    ((null l) nil)
    ((myMax (car l) (maxList (cdr l))))
  )
)

(print (maxList '(A 1 B 4 5 3 C F)))