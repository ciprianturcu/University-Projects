;Write a function that substitutes an element E with all elements of a list L1 at all levels of a given list L.


;subst(e l1 l) - nil,null
;              - ll, if l==e && l is an atom
;              - l if l!=e && l is an atom
;              - subst(e ll L1) U subst(e ll l2) U ... U subst(e ll ln) otherwise

(defun substitutie (e LL l)
    (cond
        ((null l) nil)
        ((and (atom l) (equal l e)) LL)
        ((atom l) l)
        (T (mapcar #'(lambda(x)(substitutie e LL x)) l))
    )
)

(substitutie '5 '(1 2 3 4 6) '(1 2 3 (4 5) (5 (5 5) 1) 1 2))
