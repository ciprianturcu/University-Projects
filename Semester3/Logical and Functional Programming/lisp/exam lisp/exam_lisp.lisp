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

;invrt(l1..ln aux)
;myReverse aux - l null
;myAppend(myReverse aux , invrt(l1..ln,nil) U invrt(l2..ln,nil)) - l1 list
;invrt(l2..ln,myAppend(aux,l1))


(defun invrt(l aux)
  (cond
    ((null l) (myReverse aux))
    ((listp (car l)) (myAppend (myReverse aux) (cons (invrt (car l) nil) (invrt (cdr l) nil))))
    (T (invrt (cdr l) (myAppend aux (list (car l)))))
  )
)

(defun solve(l)
  (invrt l nil)
)

(print (solve '(a (b (c (d) e) f) g)))
