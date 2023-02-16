;a) Write a function to return the product of two vectors

;dot_product(v1,v2)
; nil - v1 or v2 null
; v11*v21+dot_product(v12..v1n,v22..v2m)

(defun dot_product(v1 v2)
    (cond
        ((or (null v1) (null v2)) 0)
        (T (+ (* (car v1) (car v2)) (dot_product (cdr v1) (cdr v2))))
    )
)

(print(dot_product '(4 5 6) '(1 2 3)))
(terpri)

;b) Write a function to return the depth of a list. 
;Example: the depth of a linear list is 1.
;
;depth(l)
;0 - null l
;max(depth(l1),depth(l2..ln)) - l1 list
;depth (l2...ln) - otherwise

(defun max2(a b)
    (cond
        ((> a b) a)
        (t b)
    )
)

(defun depth(l, level)
    (cond
        ((null l) level)
        ((listp (car l)) (max2 (depth (car l) (+ 1 level)) (depth (cdr l) level)))
        (T (depth (cdr l) level))
    )
)

(print (depth '(1 2 3 4 (1 2 (3)) 4 (5 (6) (7 (8)))) 1))
(terpri)

;c) Write a function to sort a linear list without keeping the double values.

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

(print (sortare '(2 1 3 3 4 5 6 1 1 1 9 8 7)))
(terpri)

;d) Write a function to return the intersection of two sets

;removeAp(l e)
;nil - l null
;remove(l2...ln,e) - l1 = e
;l1 + removeAp(l2..ln,e)

(defun removeAp(l e)
    (cond
        ((null l) nil)
        ((equal (car l) e) (removeAp (cdr l) e))
        (T (cons (car l) (removeAp (cdr l) e)))
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

;inters(s1 s2)
;nil - s1 null or s2 null
;(s11 + inters(s12..s1n,removeAp(s2,s11))) -contains(s2 s11)
;inters(s12..s1n, s2)

(defun inters(l1 l2)
    (cond
        ((or (null l1) (null l2)) nil)
        ((contains l2 (car l1)) (cons (car l1) (inters (cdr l1) (removeAp l2 (car l1)))))
        (T (inters (cdr l1) l2))
    )
)

(print (inters '(1 2 3 4 5) '(1 5 6 7 9)))


