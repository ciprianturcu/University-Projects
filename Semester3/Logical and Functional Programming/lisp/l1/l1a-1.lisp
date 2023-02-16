; a)Write a function to return the n-th element of a list, or NIL if such an element does not exist.

;_n_elem - nil, n=0
;        - l0, n=pos
;        - _n_elem(l1...ln,n,pos+1)

(defun _n_elem (l n pos)
    (cond 
        ((null l) nil)
        ((= n pos) (car l))
        (T (_n_elem (cdr l) n (+ pos 1)))
    )
)

(defun n_elem (l n)
    (_n_elem l n 1)
)



;b)Write a function to check whether an atom E is a member of a list which is not necessarily linear.
;check_atom(l, e) - nil, n=0
;                 - true , l1 is an atom and is equal to e 
;                 - check(l2...ln,e) if l1 is an atom and not equal to e
;                 - check(l1,e) or check(l2,...,ln,e) if l1 is a list

(defun check_atom(l e)
    (cond
        ((null l) nil)
        ((and (atom (car l)) (equal (car l) e)) t)
        ((atom(car l)) (check_atom (cdr l) e))
        ((list (car l)) (or (check_atom (car l) e) (check_atom (cdr l) e)))
    )
)




; c) Write a function to determine the list of all sublists of a given list, on any level.
;  A sublist is either the list itself, or any element that is a list, at any level. Example:
;  (1 2 (3 (4 5) (6 7)) 8 (9 10)) => 5 sublists :
;  ( (1 2 (3 (4 5) (6 7)) 8 (9 10)) (3 (4 5) (6 7)) (4 5) (6 7) (9 10) )

; all_sublists(l) - nil , l is an atom
;                 - l U mapcar(all_sublists l)

(defun all_sublists (l)
    (cond
        ((atom l) nil)
        (T (apply 'append (list l) (mapcar 'all_sublists l)))
    )      
)


;transform (l) - nil, n=0
;              - l1 U transform (remove-apparences(l2...ln))
;d)Write a function to transform a linear list into a set.

(defun transform_set (l)
    (cond
        ((null l) nil)
        (T (cons (car l) (transform_set(remove_apparences (cdr l) (car l)))))
    )
)

;remove_apparences(l e) - nil , n=0
;                       - remove_apparences(l2..ln,e) , l1= e
;                       - l1 U remove_apparences(l2..ln,e) , otherwise
(defun remove_apparences (l e)
    (cond
        ((null l) nil)
        ((= (car l) e) (remove_apparences (cdr l) e))
        (T (cons (car l) (remove_apparences (cdr l) e)))
    )
)

(print(n_elem '(1 2 3 4 5 6 7 8 9) '4))
(terpri)
(print(check_atom '(1 2 (3 (a 5) (6 7)) 8 (9 10)) 'a))
(terpri)
(print(all_sublists '(1 2 (3 (4 5) (6 7)) 8 (9 10))))
(terpri)
(print(transform_set '(1 2 3 44 2 4  5 2 3 3 3 3 4 4 5 77)))


