;a)Write twice the n-th element of a linear list. Example: for (10 20 30 40 50) and n=3 will produce (10 20 30 30 40 50).

;writenth(l n pos)
;nil - l null
;l1 + l1 + writenth(l2..ln)- n = pos
;l1 + writenth(l2..ln)

(defun writenth(l n pos)
    (cond
        ((null l) nil)
        ((equal n p) (cons (car l) (cons (car l) (writenth (cdr l) n (+ 1 pos)))))
        (T (cons (car l) (writenth (cdr l) n (+ 1 pos))))
    )
)

(defun solve(l n)
    (writenth l n 1)
)

(print (solve '(10 20 30 40 50) 3))



;b)Write a function to return an association list with the two lists given as parameters. Example: (A B C) (X Y Z) --> ((A.X) (B.Y) (C.Z)).

;myAppend(l e)
;e - l null
;l1 U myAppend(l2..ln,e)

(defun myAppend(l e)
    (cond
        ((null l) e)
        (T (cons (car l) (myAppend (cdr l) e)))
    )
)

;assoc(l1 l2)
;nil - l1 null and l2 null
;nil.l21  + assoc (l1 l22.l2n)- l1 null
;l11.nil  + assoc (l12.l1n l2)- l2 null
;l11.l21 + assoc(l12..l1n l22..l2n)

(defun association(l1 l2)
    (cond
        ((and (null l1) (null l2)) nil)
        ((null l1) (myAppend (list (cons nil (car l2))) (association l1 (cdr l2))))
        ((null l2) (myAppend (list (cons (car l1) nil)) (association (cdr l1) l2)))
        (T (myAppend (list (cons (car l1) (car l2))) (association (cdr l1) (cdr l2))))
    )
)

(print (association '(A B C Q) '(X Y Z T U)))

;c)Write a function to determine the number of all sublists of a given list, on any level.
;A sublist is either the list itself, or any element that is a list, at any level. 
;Example: (1 2 (3 (4 5) (6 7)) 8 (9 10)) => 5 lists:(list itself, (3 ...), (4 5), (6 7), (9 10)).

;listLvl(l)
;0 - l atom
;1+ listLvl(l) - l list

(defun listLvl(l)
    (cond
        ((atom l) 0)
        ((listp l) (+ 1 (apply '+ (mapcar 'listLvl l))))
    )
)

(print (listLvl '(1 2 (3 (4 5) (6 7)) 8 (9 10))))

;d)Write a function to return the number of all numerical atoms in a list at superficial level.

(defun atomNC(l)
    (cond
        ((null l) 0)
        ((numberp (car l)) (+ 1 (atomNC (cdr l))))
        (T (atomNC (cdr l)))
    )
)

(print (atomNC '(1 2 s 5 (6) fg 5)))